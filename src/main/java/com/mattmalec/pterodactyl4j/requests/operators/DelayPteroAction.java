package com.mattmalec.pterodactyl4j.requests.operators;

import com.mattmalec.pterodactyl4j.PteroAction;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DelayPteroAction<T> extends PteroActionOperator<T, T> {

    private final TimeUnit unit;
    private final long delay;
    private final ScheduledExecutorService scheduler;

    public DelayPteroAction(PteroAction<T> action, TimeUnit unit, long delay, ScheduledExecutorService scheduler) {
        super(action);
        this.unit = unit;
        this.delay = delay;
        this.scheduler = scheduler == null ? action.getApi().getRateLimitPool() : scheduler;
    }

    @Override
    public void executeAsync(Consumer<? super T> success, Consumer<? super Throwable> failure) {
        action.executeAsync((result) ->
                        scheduler.schedule(() ->
                                doSuccess(success, result), delay, unit), failure);
    }

    @Override
    public T execute(boolean shouldQueue) {
        T result = action.execute(shouldQueue);
        try {
            unit.sleep(delay);
            return result;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}