package com.mattmalec.pterodactyl4j.requests;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import okhttp3.RequestBody;

import java.util.concurrent.CompletableFuture;

public class RequestFuture<T> extends CompletableFuture<T> {

    private final Request<T> request;

    public RequestFuture(PteroActionImpl<T> action, Route.CompiledRoute route, RequestBody requestBody, boolean shouldQueue) {
        this.request = new Request<>(action, this::complete, this::completeExceptionally, route, requestBody, shouldQueue);
        action.getRequester().request(this.request);
    }

    @Override
    public boolean cancel(final boolean mayInterrupt) {
        if (this.request != null)
            this.request.cancel();

        return (!isDone() && !isCancelled()) && super.cancel(mayInterrupt);
    }

}
