package com.mattmalec.pterodactyl4j.requests;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.exceptions.HttpException;
import com.mattmalec.pterodactyl4j.exceptions.RateLimitedException;
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
        try {
            onSuccess.accept(success);
        } catch (Throwable t) {
            System.err.printf("Encountered error while processing success consumer: %s%n", t);
            throw t;
        }
    }

    public void setOnFailure(Response response) {
        if(response.isRateLimit()) {
            onFailure(new RateLimitedException(route, response.getRetryAfter()));
        } else
            onFailure(new HttpException(String.format("There was an HTTP exception: %s", response.getRawResponse().message())));
    }

    public void onFailure(Throwable failException) {
        if (done) return;
        done = true;
        try {
            onFailure.accept(failException);
        } catch (Throwable t) {
            System.err.printf("Encountered error while processing failure consumer: %s%n", t);
            throw t;
        }
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