package com.mattmalec.pterodactyl4j.requests.operators;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.entities.PteroAPI;

import java.util.function.Consumer;

public abstract class PteroActionOperator<I, O> implements PteroAction<O> {

    protected final PteroAction<I> action;

    public PteroActionOperator(PteroAction<I> action) {
        this.action = action;
    }

    protected static <E> void doSuccess(Consumer<? super E> callback, E value) {
        if (callback == null)
            PteroAction.getDefaultSuccess().accept(value);
        else
            callback.accept(value);
    }

    protected static void doFailure(Consumer<? super Throwable> callback, Throwable throwable) {
        if (callback == null)
            PteroAction.getDefaultFailure().accept(throwable);
        else
            callback.accept(throwable);
    }

    @Override
    public PteroAPI getApi() {
        return action.getApi();
    }
}