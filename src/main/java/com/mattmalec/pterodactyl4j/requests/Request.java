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

import com.mattmalec.pterodactyl4j.exceptions.*;
import okhttp3.RequestBody;

import java.util.function.Consumer;

public class Request<T> {

    private final PteroActionImpl<T> action;
    private final Consumer<? super T> onSuccess;
    private final Consumer<? super Throwable> onFailure;
    private final Route.CompiledRoute route;
    private final RequestBody requestBody;
    private final boolean shouldQueue;

    private boolean done = false;
    private boolean isCancelled = false;

    public Request(PteroActionImpl<T> action, Consumer<? super T> onSuccess, Consumer<? super Throwable> onFailure, Route.CompiledRoute route, RequestBody requestBody, boolean shouldQueue) {
        this.action = action;
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
        this.route = route;
        this.requestBody = requestBody;
        this.shouldQueue = shouldQueue;
    }

    public void onSuccess(T success) {
        if (done) return;
        done = true;
        action.getP4J().getCallbackPool().execute(() -> {
            try {
                onSuccess.accept(success);
            } catch (Throwable t) {
                System.err.printf("Encountered error while processing success consumer: %s%n", t);
                throw t;
            }
        });
    }

    public void setOnFailure(Response response) {
        if (response.isRateLimit()) {
            onFailure(new RateLimitedException(route, response.getRetryAfter()));
        } else
            switch (response.getCode()) {
                case 403:
                    onFailure(new LoginException("The provided token is either incorrect or does not have access to process this request."));
                    break;
                case 404:
                    onFailure(new NotFoundException("The requested entity was not found."));
                    break;
                case 422:
                    onFailure(new MissingActionException("The request is missing required fields.", response.getObject()));
                    break;
                case 500:
                    onFailure(new ServerException("The server has encountered an Internal Server Error."));
                    break;
                default:
                    onFailure(new HttpException(String.format("Pterodactyl4J has encountered a %d error.", response.getCode()), response.getObject()));
                    break;
            }
    }

    public void onFailure(Throwable failException) {
        if (done) return;
        done = true;
        action.getP4J().getCallbackPool().execute(() -> {
            try {
                onFailure.accept(failException);
            } catch (Throwable t) {
                System.err.printf("Encountered error while processing failure consumer: %s%n", t);
                throw t;
            }
        });
    }

    public void cancel() {
        this.isCancelled = true;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public Route.CompiledRoute getRoute() {
        return route;
    }

    public boolean shouldQueue() {
        return shouldQueue;
    }

    public void handleResponse(Response response) {
        action.handleResponse(response, this);
    }
}