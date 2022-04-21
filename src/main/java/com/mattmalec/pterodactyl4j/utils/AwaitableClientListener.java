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

package com.mattmalec.pterodactyl4j.utils;

import com.mattmalec.pterodactyl4j.client.ws.events.Event;
import com.mattmalec.pterodactyl4j.client.ws.hooks.ClientSocketListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class AwaitableClientListener implements ClientSocketListener {

    private final Class<? extends Event> awaitedEvent;
    private final ExecutorService executor;
    private final CountDownLatch latch = new CountDownLatch(1);

    private AwaitableClientListener(Class<? extends Event> event, ExecutorService executor) {
        this.awaitedEvent = event;
        this.executor = executor;
    }

    public static AwaitableClientListener create(Class<? extends Event> event, ExecutorService executor) {
        return new AwaitableClientListener(event, executor);
    }

    @Override
    public void onEvent(Event event) {
        if (event.getClass().isAssignableFrom(awaitedEvent))
            latch.countDown();
    }

    public void await(Consumer<?> success) {
        executor.submit(() -> {
            try {
                latch.await();
                success.accept(null);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
