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
import com.mattmalec.pterodactyl4j.exceptions.PteroException;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PteroActionImpl<T> implements PteroAction<T> {

    public static final Logger LOGGER = LoggerFactory.getLogger(PteroAction.class);

    private final P4J api;
    private final Route.CompiledRoute route;
    private final RequestBody data;
    private long deadline = 0;
    private final BiFunction<Response, Request<T>, T> handler;

    public static <T> DeferredPteroAction<T> onExecute(P4J api, Supplier<? extends T> supplier) {
        return new DeferredPteroAction<>(api, supplier);
    }

    public static <T> PteroActionImpl<T> onRequestExecute(P4J api, Route.CompiledRoute route) {
        return new PteroActionImpl<>(api, route);
    }

    public static <T> PteroActionImpl<T> onRequestExecute(P4J api, Route.CompiledRoute route, RequestBody data) {
        return new PteroActionImpl<>(api, route, data);
    }

    public static <T> PteroActionImpl<T> onRequestExecute(P4J api, Route.CompiledRoute route, BiFunction<Response, Request<T>, T> handler) {
        return new PteroActionImpl<>(api, route, handler);
    }

    public static <T> PteroActionImpl<T> onRequestExecute(P4J api, Route.CompiledRoute route, RequestBody data, BiFunction<Response, Request<T>, T> handler) {
        return new PteroActionImpl<>(api, route, data, handler);
    }

    public PteroActionImpl(P4J api) {
        this(api, null);
    }

    public PteroActionImpl(P4J api, Route.CompiledRoute route) {
        this(api, route, null, null);
    }

    public PteroActionImpl(P4J api, Route.CompiledRoute route, RequestBody data) {
        this(api, route, data, null);
    }

    public PteroActionImpl(P4J api, Route.CompiledRoute route, BiFunction<Response, Request<T>, T> handler) {
        this(api, route, null, handler);
    }

    public PteroActionImpl(P4J api, Route.CompiledRoute route, RequestBody data, BiFunction<Response, Request<T>, T> handler) {
        this.api = api;
        this.route = route;
        this.data = data;
        this.handler = handler;
    }

    public static final Consumer<Object> DEFAULT_SUCCESS = o -> {};
    public static final Consumer<? super Throwable> DEFAULT_FAILURE = t -> System.err.printf("Action execute returned failure: %s%n", t.getMessage());

    @Override
    public T execute(boolean shouldQueue) {
        Route.CompiledRoute route = finalizeRoute();
        RequestBody data = finalizeData();
        try {
            return new RequestFuture<>(this, route, data, shouldQueue, deadline).join();
        } catch (CompletionException ex) {
            if (ex.getCause() != null) {
                Throwable cause = ex.getCause();
                if (cause instanceof PteroException)
                    throw (PteroException) cause.fillInStackTrace();
            }
            throw ex;
        }
    }

    @Override
    public void executeAsync(Consumer<? super T> success, Consumer<? super Throwable> failure) {
        Route.CompiledRoute route = finalizeRoute();
        if (success == null)
            success = DEFAULT_SUCCESS;
        if (failure == null)
            failure = DEFAULT_FAILURE;

        Consumer<? super T> finalizedSuccess = success;
        Consumer<? super Throwable> finalizedFailure = failure;

        api.getActionPool().submit(() -> {
            RequestBody data = finalizeData();
            api.getRequester().request(new Request<>(this, finalizedSuccess, finalizedFailure, route, data, true, deadline));
        });
    }

    @Override
    public PteroAction<T> deadline(long timestamp) {
        this.deadline = timestamp;
        return this;
    }

    @Override
    public P4J getP4J() {
        return api;
    }

    public void handleResponse(Response response, Request<T> request) {
        if(response.isOk())
            handleSuccess(response, request);
        else request.setOnFailure(response);
    }

    public void handleSuccess(Response response, Request<T> request) {
        if(response.isEmpty())
            request.onSuccess(null);
        else request.onSuccess(handler.apply(response, request));
    }

    protected RequestBody finalizeData() {
        return data;
    }

    protected Route.CompiledRoute finalizeRoute() {
        return route;
    }

    public static RequestBody getRequestBody(JSONObject object) {
        return object == null ? null : RequestBody.create(object.toString(), Requester.MEDIA_TYPE_JSON);
    }

    public static RequestBody getRequestBody(JSONArray array) {
        return array == null ? null : RequestBody.create(array.toString(), Requester.MEDIA_TYPE_JSON);
    }
}
