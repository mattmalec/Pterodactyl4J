package com.mattmalec.pterodactyl4j.requests.operator;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.entities.P4J;

import java.util.function.Consumer;

// big thanks to JDA for this tremendous code

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
    public P4J getP4J() {
        return action.getP4J();
    }
}