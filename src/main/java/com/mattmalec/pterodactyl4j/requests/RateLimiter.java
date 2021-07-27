/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.requests;

import com.mattmalec.pterodactyl4j.entities.P4J;
import com.mattmalec.pterodactyl4j.utils.LockUtils;
import okhttp3.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class RateLimiter implements Runnable {

    public static final Logger RATELIMIT_LOG = LoggerFactory.getLogger(RateLimiter.class);

    private static final String RESET_HEADER = "X-RateLimit-Reset";
    private static final String LIMIT_HEADER = "X-RateLimit-Limit";
    private static final String RETRY_AFTER_HEADER = "Retry-After";
    private static final String REMAINING_HEADER = "X-RateLimit-Remaining";

    private final Requester requester;
    private final ScheduledExecutorService scheduler;

    private final Queue<Request<?>> requests = new ConcurrentLinkedQueue<>();
    private final Map<RateLimiter, Future<?>> rateLimitQueue = new ConcurrentHashMap<>();

    private long reset = 0;
    private int limit = 1;
    private long retryAfter = 0;
    private int remaining = 1;
    
    private final ReentrantLock lock = new ReentrantLock();

    public RateLimiter(Requester requester, P4J api) {
        this.requester = requester;
        this.scheduler = api.getRateLimitPool();
    }

    public void queueRequest(Request<?> request) {
        LockUtils.locked(lock, () -> {
            requests.offer(request);
            runQueue();
        });
    }

    public Long handleResponse(Request<?> request, okhttp3.Response response) {
        lock.lock();
        try {
            updateRequest(request, response);
            if (response.code() == 429)
                return getRateLimit();
            else return null;
        } finally {
            lock.unlock();
        }
    }

    private void updateRequest(Request<?> request, okhttp3.Response response) {
        LockUtils.locked(lock, () -> {
            try {
                Headers headers = response.headers();
                long now = getNow();
                if (response.code() == 429) {
                    String retryAfterHeader = headers.get(RETRY_AFTER_HEADER);
                    long retryAfter = parseLong(retryAfterHeader) * 1000;
                    RATELIMIT_LOG.warn("Encountered 429 on route {} Retry-After: {} ms", request.getRoute().getCompiledRoute(), retryAfter);
                }

                String limitHeader = headers.get(LIMIT_HEADER);
                String remainingHeader = headers.get(REMAINING_HEADER);
                String retryAfterHeader = headers.get(RETRY_AFTER_HEADER);
                String resetHeader = headers.get(RESET_HEADER);

                this.limit = (int) Math.max(1L, parseLong(limitHeader));
                this.remaining = (int) parseLong(remainingHeader);
                this.retryAfter = parseDouble(retryAfterHeader);
                this.reset = parseDouble(resetHeader);
                RATELIMIT_LOG.trace("Updated to ({}/{}, {})", this.remaining, this.limit, this.reset - now);
            } catch (Exception e) {
                RATELIMIT_LOG.error("Encountered Exception while updating the rate limiter. Route: {} Code: {} Headers:\n{}",
                        request.getRoute().getBaseRoute(), response.code(), response.headers());
            }
        });
    }
    private void runQueue() {
        LockUtils.locked(lock, () -> rateLimitQueue.computeIfAbsent(this, k ->
            scheduler.schedule(this, getRateLimit(), TimeUnit.MILLISECONDS)));
    }

    private void cancel(Iterator<Request<?>> it, Request<?> request, Throwable exception) {
        request.onFailure(exception);
        it.remove();
    }

    private boolean isSkipped(Iterator<Request<?>> it, Request<?> request) {
        try {
            if (request.isCancelled()) {
                cancel(it, request, new CancellationException("Action has been cancelled"));
                return true;
            }
        } catch (Throwable exception) {
            cancel(it, request, exception);
            return true;
        }
        return false;
    }

    private void backoff() {
        LockUtils.locked(lock, () -> {
            rateLimitQueue.remove(this);
            if (!requests.isEmpty()) runQueue();
        });
    }

    @Override
    public void run() {
        RATELIMIT_LOG.trace("Rate limiter is running {} requests", requests.size());

        Iterator<Request<?>> iterator = requests.iterator();
        while (iterator.hasNext()) {
            Long rateLimit = getRateLimit();
            if (rateLimit > 0L) {
                RATELIMIT_LOG.debug("Backing off {} ms", rateLimit);
                break;
            }

            Request<?> request = iterator.next();

            if (isSkipped(iterator, request))
                continue;

            try {
                rateLimit = requester.execute(request);
                if (rateLimit != null)
                    break;
                iterator.remove();
            } catch (Exception ex) {
                RATELIMIT_LOG.error("Encountered exception trying to execute request");
                ex.printStackTrace();
                break;
            }
        }
        backoff();
    }

    public long getRateLimit() {
        long now = getNow();
        if (reset <= now) {
            remaining = limit;
            return 0L;
        }
        return remaining < 1 ? retryAfter : 0L;
    }

    public long getNow() {
        return System.currentTimeMillis();
    }

    private long parseLong(String input) {
        return input == null ? 0L : Long.parseLong(input);
    }

    private long parseDouble(String input) {
        return input == null ? 0L : (long) (Double.parseDouble(input) * 1000);
    }
}