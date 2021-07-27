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

import okhttp3.RequestBody;

import java.util.concurrent.CompletableFuture;

public class RequestFuture<T> extends CompletableFuture<T> {

    private final Request<T> request;

    public RequestFuture(PteroActionImpl<T> action, Route.CompiledRoute route, RequestBody requestBody, boolean shouldQueue) {
        this.request = new Request<>(action, this::complete, this::completeExceptionally, route, requestBody, shouldQueue);
        action.getP4J().getRequester().request(this.request);
    }

    @Override
    public boolean cancel(final boolean mayInterrupt) {
        if (this.request != null)
            this.request.cancel();

        return (!isDone() && !isCancelled()) && super.cancel(mayInterrupt);
    }

}
