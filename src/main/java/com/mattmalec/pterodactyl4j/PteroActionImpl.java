package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.requests.*;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PteroActionImpl<T> implements PteroAction<T> {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private Requester requester;
    private Route.CompiledRoute route;
    private RequestBody data;

    private Supplier<? extends T> supplier;

    private BiFunction<Response, Request<T>, T> handler;

    public static <T> PteroActionImpl<T> onExecute(Supplier<? extends T> supplier) {
        return new PteroActionImpl<>(supplier);
    }

    public static <T> PteroActionImpl<T> onRequestExecute(Requester requester, Route.CompiledRoute route) {
        return new PteroActionImpl<>(requester, route);
    }

    public static <T> PteroActionImpl<T> onRequestExecute(Requester requester, Route.CompiledRoute route, RequestBody data) {
        return new PteroActionImpl<>(requester, route, data);
    }

    public static <T> PteroActionImpl<T> onRequestExecute(Requester requester, Route.CompiledRoute route, BiFunction<Response, Request<T>, T> handler) {
        return new PteroActionImpl<>(requester, route, handler);
    }

    public PteroActionImpl(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    public PteroActionImpl(Requester requester, Route.CompiledRoute route) {
        this(requester, route, null, null);
    }

    public PteroActionImpl(Requester requester, Route.CompiledRoute route, RequestBody data) {
        this(requester, route, data, null);
    }

    public PteroActionImpl(Requester requester, Route.CompiledRoute route, BiFunction<Response, Request<T>, T> handler) {
        this(requester, route, null, handler);
    }

    public PteroActionImpl(Requester requester, Route.CompiledRoute route, RequestBody data, BiFunction<Response, Request<T>, T> handler) {
        this.requester = requester;
        this.route = route;
        this.data = data;
        this.handler = handler;
    }

    private static final Consumer<Object> DEFAULT_SUCCESS = o -> {};
    private static final Consumer<? super Throwable> DEFAULT_FAILURE = t -> System.err.printf("Action execute returned failure: %s%n", t.getMessage());

    @Override
    public T execute() {
        if (supplier == null) {
            Route.CompiledRoute route = finalizeRoute();
            RequestBody data = finalizeData();
            return new RequestFuture<>(this, route, data, true).join();
        } else
            return supplier.get();
    }

    @Override
    public void executeAsync(Consumer<? super T> success, Consumer<? super Throwable> failure) {
        if (supplier == null) {
            Route.CompiledRoute route = finalizeRoute();
            if (success == null)
                success = DEFAULT_SUCCESS;
            if (failure == null)
                failure = DEFAULT_FAILURE;

            // the fact that i have to do this is bullshit
            Consumer<? super T> finalizedSuccess = success;
            Consumer<? super Throwable> finalizedFailure = failure;
            finalizeDataAsync(data -> requester.request(new Request<>(this, finalizedSuccess, finalizedFailure, route, data, true)));
        } else
            CompletableFuture.supplyAsync(supplier, executor)
                    .thenAcceptAsync(success);
    }

    public Requester getRequester() {
        return requester;
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

    protected void finalizeDataAsync(Consumer<RequestBody> body) {
        body.accept(finalizeData());
    }

    protected RequestBody finalizeData() {
        return data;
    }

    protected Route.CompiledRoute finalizeRoute() {
        return route;
    }

    public static RequestBody getRequestBody(JSONObject object) {
        return object == null ? null : RequestBody.create(Requester.MEDIA_TYPE_JSON, object.toString());
    }

    public static RequestBody getRequestBody(JSONArray array) {
        return array == null ? null : RequestBody.create(Requester.MEDIA_TYPE_JSON, array.toString());
    }
}
