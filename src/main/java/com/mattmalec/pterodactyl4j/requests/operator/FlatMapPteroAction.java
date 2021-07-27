package com.mattmalec.pterodactyl4j.requests.operator;

import com.mattmalec.pterodactyl4j.PteroAction;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

// big thanks to JDA for this tremendous code

public class FlatMapPteroAction<I, O> extends PteroActionOperator<I, O> {

    private final Function<? super I, ? extends PteroAction<O>> function;
    private final Predicate<? super I> condition;

    public FlatMapPteroAction(PteroAction<I> action,
                              Predicate<? super I> condition, Function<? super I, ? extends PteroAction<O>> function) {
        super(action);
        this.function = function;
        this.condition = condition;
    }


    @Override
    public void executeAsync(Consumer<? super O> success, Consumer<? super Throwable> failure) {
        action.executeAsync((result) -> {
            if (condition != null && !condition.test(result))
                return;
            PteroAction<O> then = function.apply(result);
            if (then == null)
                doFailure(failure, new IllegalStateException("FlatMap operand is null"));
            else
                then.executeAsync(success, failure);
        }, failure);
    }

    @Override
    public O execute(boolean shouldQueue) {
        return function.apply(action.execute(shouldQueue)).execute(shouldQueue);
    }

}