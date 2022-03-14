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

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.entities.P4J;
import com.mattmalec.pterodactyl4j.exceptions.RateLimitedException;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DeferredPteroAction<T> implements PteroAction<T> {

    private final P4J api;
    private final Supplier<? extends T> value;

    public DeferredPteroAction(P4J api, Supplier<? extends T> value) {
        this.api = api;
        this.value = value;
    }

    @Override
    public P4J getP4J() {
        return api;
    }

    @Override
    public T execute(boolean shouldQueue) throws RateLimitedException {
        return value.get();
    }

    @Override
    public void executeAsync(Consumer<? super T> success, Consumer<? super Throwable> failure) {
        if (success == null)
            PteroAction.getDefaultSuccess().accept(value);
        else
            CompletableFuture.supplyAsync(value, api.getSupplierPool())
                    .thenAcceptAsync(success);
    }

    @Override
    public PteroAction<T> deadline(long timestamp) {
        return this;
    }
}