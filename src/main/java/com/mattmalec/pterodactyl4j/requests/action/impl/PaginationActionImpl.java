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

package com.mattmalec.pterodactyl4j.requests.action.impl;

import com.mattmalec.pterodactyl4j.entities.P4J;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.PaginationAction;
import com.mattmalec.pterodactyl4j.utils.Checks;
import com.mattmalec.pterodactyl4j.utils.Procedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public abstract class PaginationActionImpl<T> extends PteroActionImpl<List<T>> implements PaginationAction<T> {

    protected static final Logger PAGINATION_LOG = LoggerFactory.getLogger(PaginationAction.class);

    protected final List<T> cached = new CopyOnWriteArrayList<>();
    protected final int minLimit;
    protected final AtomicInteger limit;

    protected volatile int iteratorIndex = 0;
    protected volatile int currentPage = 1;
    protected volatile int totalPages = 1;
    protected volatile T last = null;
    protected volatile boolean useCache = true;

    /**
     * Creates a new PaginationAction instance
     * <br>This is used for PaginationActions that should not deal with
     * {@link #limit(int)}
     *
     * @param api
     *        The current P4J instance
     */
    public PaginationActionImpl(P4J api, Route.CompiledRoute route) {
        super(api, route);
        this.minLimit = 0;
        this.limit = new AtomicInteger(0);
    }

    public PaginationActionImpl(P4J api) {
        this(api, null);
    }

    @Override
    public PaginationAction<T> skipTo(int page) {
        Checks.check(page > 0, "Page must be greater than 0");

        if (currentPage != page)
            last = null;
        currentPage = page;
        return this;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public PaginationAction<T> timeout(long timeout, TimeUnit unit) {
        return (PaginationAction<T>) super.timeout(timeout, unit);
    }

    @Override
    public PaginationAction<T> deadline(long timestamp) {
        return (PaginationAction<T>) super.deadline(timestamp);
    }

    @Override
    public int cacheSize() {
        return cached.size();
    }

    @Override
    public boolean isEmpty() {
        return cached.isEmpty();
    }

    @Override
    public List<T> getCached() {
        return Collections.unmodifiableList(cached);
    }

    @Override
    public T getLast() {
        T last = this.last;
        if (last == null)
            throw new NoSuchElementException("No entities have been retrieved yet");
        return last;
    }

    @Override
    public T getFirst() {
        if (cached.isEmpty())
            throw new NoSuchElementException("No entities have been retrieved yet");
        return cached.get(0);
    }

    @Override
    public PaginationAction<T> limit(int limit) {
        Checks.check(minLimit == 0 || limit >= minLimit, String.format("Limit must be greater or equal to %d", minLimit));
        this.limit.set(limit);
        return this;
    }

    @Override
    public PaginationAction<T> cache(boolean enableCache) {
        this.useCache = enableCache;
        return this;
    }

    @Override
    public boolean isCacheEnabled() {
        return useCache;
    }

    @Override
    public final int getMinLimit() {
        return minLimit;
    }

    @Override
    public final int getLimit() {
        return limit.get();
    }

    @Override
    public CompletableFuture<List<T>> takeAsync(int amount) {
        return takeAsyncInternally(amount, (task, list) -> forEachAsync(val -> {
            list.add(val);
            return list.size() < amount;
        }, task::completeExceptionally));
    }

    @Override
    public CompletableFuture<List<T>> takeRemainingAsync(int amount) {
        return takeAsyncInternally(amount, (task, list) -> forEachRemainingAsync(val -> {
            list.add(val);
            return list.size() < amount;
        }, task::completeExceptionally));
    }

    private CompletableFuture<List<T>> takeAsyncInternally(int amount, BiFunction<CompletableFuture<?>, List<T>, CompletableFuture<?>> converter) {
        CompletableFuture<List<T>> task = new CompletableFuture<>();
        List<T> list = new ArrayList<>(amount);
        CompletableFuture<?> promise = converter.apply(task, list);
        promise.thenRun(() -> task.complete(list));
        return task;
    }

    @Override
    public PaginationIterator<T> iterator() {
        return new PaginationIterator<>(cached, this::getNextChunk);
    }

    @Override
    public CompletableFuture<?> forEachAsync(Procedure<? super T> action, Consumer<? super Throwable> failure) {
        return forEachAsyncInternally(action, failure, cached);
    }

    @Override
    public CompletableFuture<?> forEachRemainingAsync(Procedure<? super T> action, Consumer<? super Throwable> failure) {
        return forEachAsyncInternally(action, failure, getRemainingCache());
    }

    private CompletableFuture<?> forEachAsyncInternally(Procedure<? super T> action, Consumer<? super Throwable> failure, List<T> acceptorValue) {
        Checks.notNull(action, "Procedure");
        Checks.notNull(failure, "Failure Consumer");

        CompletableFuture<?> task = new CompletableFuture<>();
        Consumer<List<T>> acceptor = new ChainedConsumer(task, action, (throwable) -> {
            task.completeExceptionally(throwable);
            failure.accept(throwable);
        });
        try {
            acceptor.accept(acceptorValue);
        } catch (Exception ex) {
            failure.accept(ex);
            task.completeExceptionally(ex);
        }
        return task;
    }

    @Override
    public void forEachRemaining(Procedure<? super T> action) {
        Checks.notNull(action, "Procedure");
        Queue<T> queue = new LinkedList<>();
        while (queue.addAll(getNextChunk())) {
            while (!queue.isEmpty()) {
                T it = queue.poll();

                if (queue.size() == 0)
                    updateIndex(it, cacheSize() + 1);

                if (!action.execute(it)) {
                    updateIndex(it, queue.size());
                    return;
                }
            }
        }
    }

    @Override
    protected Route.CompiledRoute finalizeRoute() {
        Route.CompiledRoute route = super.finalizeRoute();

        String limit = Integer.toUnsignedString(getLimit());
        String page = Integer.toUnsignedString(getCurrentPage());

        return route.withQueryParams("per_page", limit, "page", page);
    }

    protected int getIteratorIndex() {
        return iteratorIndex < cacheSize() ? cacheSize() - iteratorIndex : -1;
    }

    protected List<T> getRemainingCache() {
        int index = getIteratorIndex();
        if (useCache && index > -1 && index < cached.size())
            return cached.subList(index, cached.size());
        return Collections.emptyList();
    }

    public List<T> getNextChunk() {
        List<T> list = getRemainingCache();
        PAGINATION_LOG.trace("Returning {} elements from remaining cache", list.size());
        if (!list.isEmpty())
            return list;

        PAGINATION_LOG.trace("No more elements in cache, retrieving page {}", getCurrentPage());
        return execute();
    }

    protected void updateIndex(T it, int index) {
        iteratorIndex = index;
        if (!useCache)
            last = it;
    }

    protected class ChainedConsumer implements Consumer<List<T>> {
        private final CompletableFuture<?> task;
        private final Procedure<? super T> action;
        private final Consumer<Throwable> throwableConsumer;
        private boolean initial = true;

        protected ChainedConsumer(CompletableFuture<?> task,
                                  Procedure<? super T> action, Consumer<Throwable> throwableConsumer) {
            this.task = task;
            this.action = action;
            this.throwableConsumer = throwableConsumer;
        }

        @Override
        public void accept(List<T> list) {
            if (list.isEmpty() && !initial) {
                task.complete(null);
                return;
            }

            int size = list.size();
            if (!list.isEmpty() && !initial)
                PAGINATION_LOG.trace("Returning {} elements from remaining cache", size);

            initial = false;

            for (T it : list) {
                if (size-- == 0)
                    updateIndex(it, cacheSize() + 1);

                if (task.isCancelled())
                    return;

                if (action.execute(it))
                    continue;

                updateIndex(it, size);
                task.complete(null);
                return;
            }

            PAGINATION_LOG.trace("No more elements in cache, asynchronously retrieving next page {} -> {}", getCurrentPage() - 1, getCurrentPage());
            executeAsync(this, throwableConsumer);
        }
    }
}