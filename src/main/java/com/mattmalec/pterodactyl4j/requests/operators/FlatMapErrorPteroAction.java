package com.mattmalec.pterodactyl4j.requests.operators;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.exceptions.PteroException;
import com.mattmalec.pterodactyl4j.utils.ExceptionUtils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FlatMapErrorPteroAction<T> extends PteroActionOperator<T, T> {

    private final Predicate<? super Throwable> check;
    private final Function<? super Throwable, ? extends PteroAction<? extends T>> map;

    public FlatMapErrorPteroAction(PteroAction<T> action, Predicate<? super Throwable> check,
                                   Function<? super Throwable, ? extends PteroAction<? extends T>> map) {
        super(action);
        this.check = check;
        this.map = map;
    }

    @Override
    public void executeAsync(Consumer<? super T> success, Consumer<? super Throwable> failure) {
        action.executeAsync(success, (error) -> {
            try {
                if (check.test(error)) {
                    PteroAction<? extends T> then = map.apply(error);
                    if (then == null)
                        doFailure(failure, new IllegalStateException("FlatMapError operand is null", error));
                    else
                        then.executeAsync(success, failure);
                } else doFailure(failure, error);
            } catch (Throwable e) {
                doFailure(failure, ExceptionUtils.appendCause(e, error));
            }
        });
    }

    @Override
    public T execute(boolean shouldQueue) {
        try {
            return action.execute(shouldQueue);
        } catch (Throwable error) {
            try {
                if (check.test(error)) {
                    PteroAction<? extends T> then = map.apply(error);
                    if (then == null)
                        throw new IllegalStateException("FlatMapError operand is null", error);
                    return then.execute(shouldQueue);
                }
            } catch (Throwable e) {
                if (e instanceof IllegalStateException && e.getCause() == error)
                    throw (IllegalStateException) e;
                else
                    fail(ExceptionUtils.appendCause(e, error));
            }
            fail(error);
        }
        throw new AssertionError("Unreachable");
    }

    private void fail(Throwable error) {
        if (error instanceof PteroException)
            throw (PteroException) error;
        else
            throw new RuntimeException(error);
    }
}