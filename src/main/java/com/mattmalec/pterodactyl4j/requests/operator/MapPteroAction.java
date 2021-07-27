package com.mattmalec.pterodactyl4j.requests.operator;

import com.mattmalec.pterodactyl4j.PteroAction;

import java.util.function.Consumer;
import java.util.function.Function;

// big thanks to JDA for this tremendous code

public class MapPteroAction<I, O> extends PteroActionOperator<I, O> {

    private final Function<? super I, ? extends O> function;

    public MapPteroAction(PteroAction<I> action, Function<? super I, ? extends O> function) {
        super(action);
        this.function = function;
    }

    @Override
    public void executeAsync(Consumer<? super O> success, Consumer<? super Throwable> failure) {
        action.executeAsync((result) -> doSuccess(success, function.apply(result)), (error) -> doFailure(failure, error));
    }

    @Override
    public O execute(boolean shouldQueue) {
        return function.apply(action.execute(shouldQueue));
    }

}