package com.mattmalec.pterodactyl4j.requests;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.entities.PteroAPI;
import com.mattmalec.pterodactyl4j.exceptions.HttpException;
import com.mattmalec.pterodactyl4j.exceptions.MissingActionException;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PteroActionImpl<T> implements PteroAction<T> {

    private PteroAPI api;
    private Route.CompiledRoute route;
    private RequestBody data;

    private Supplier<? extends T> supplier;

    private BiFunction<Response, Request<T>, T> handler;

    public static <T> PteroActionImpl<T> onExecute(PteroAPI api, Supplier<? extends T> supplier) {
        return new PteroActionImpl<>(api, supplier);
    }

    public static <T> PteroActionImpl<T> onRequestExecute(PteroAPI api, Route.CompiledRoute route) {
        return new PteroActionImpl<>(api, route);
    }

    public static <T> PteroActionImpl<T> onRequestExecute(PteroAPI api, Route.CompiledRoute route, RequestBody data) {
        return new PteroActionImpl<>(api, route, data);
    }

    public static <T> PteroActionImpl<T> onRequestExecute(PteroAPI api, Route.CompiledRoute route, BiFunction<Response, Request<T>, T> handler) {
        return new PteroActionImpl<>(api, route, handler);
    }

    public PteroActionImpl(PteroAPI api, Supplier<? extends T> supplier) {
        this.api = api;
        this.supplier = supplier;
    }

    public PteroActionImpl(PteroAPI api, Route.CompiledRoute route) {
        this(api, route, null, null);
    }

    public PteroActionImpl(PteroAPI api, Route.CompiledRoute route, RequestBody data) {
        this(api, route, data, null);
    }

    public PteroActionImpl(PteroAPI api, Route.CompiledRoute route, BiFunction<Response, Request<T>, T> handler) {
        this(api, route, null, handler);
    }

    public PteroActionImpl(PteroAPI api, Route.CompiledRoute route, RequestBody data, BiFunction<Response, Request<T>, T> handler) {
        this.api = api;
        this.route = route;
        this.data = data;
        this.handler = handler;
    }

    public static final Consumer<Object> DEFAULT_SUCCESS = o -> {};
    public static final Consumer<? super Throwable> DEFAULT_FAILURE = t -> System.err.printf("Action execute returned failure: %s%n", t.getMessage());

    @Override
    public T execute(boolean shouldQueue) {
        if (supplier == null) {
            Route.CompiledRoute route = finalizeRoute();
            RequestBody data = finalizeData();
            try {
                return new RequestFuture<>(this, route, data, shouldQueue).join();
            } catch (CompletionException ex) {
                if (ex.getCause() != null) {
                    Throwable cause = ex.getCause();
                    if (cause instanceof HttpException) {
                        throw (HttpException) cause.fillInStackTrace();
                    } else if (cause instanceof MissingActionException) {
                        throw (MissingActionException) cause.fillInStackTrace();
                    }
                }
                throw ex;
            }
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

//            finalizedFailure.accept(new HttpException("fuck", "you"));

            api.getActionPool().submit(() -> {
                RequestBody data = finalizeData();
                api.getRequester().request(new Request<>(this, finalizedSuccess, finalizedFailure, route, data, true));
            });
        } else {
            CompletableFuture.supplyAsync(supplier, api.getSupplierPool())
                    .thenAcceptAsync(success);
        }

    }

    public PteroAPI getApi() {
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
        return object == null ? null : RequestBody.create(Requester.MEDIA_TYPE_JSON, object.toString());
    }

    public static RequestBody getRequestBody(JSONArray array) {
        return array == null ? null : RequestBody.create(Requester.MEDIA_TYPE_JSON, array.toString());
    }
}
