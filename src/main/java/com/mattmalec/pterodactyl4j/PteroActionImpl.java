package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.entities.PteroAction;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class PteroActionImpl<T> implements PteroAction<T> {
    private static final ThreadGroup threadGroup = new ThreadGroup("PtaeroAction Callback Workers");
    static final ExecutorService callbackPool = newCachedThreadPool(runnable -> new Thread(threadGroup, runnable));

    private final Supplier<? extends T> execute;

    public static <T> PteroActionImpl<T> onExecute(Supplier<? extends T> execute) {
        return new PteroActionImpl<>(execute);
    }

    private PteroActionImpl(Supplier<? extends T> execute) {
        this.execute = execute;
    }

    @Override
    public T execute() {
        return execute.get();
    }

    @Override
    public void executeAsync() {
        this.executeAsync(x -> {
        });
    }

    @Override
    public void executeAsync(Consumer<? super T> success) {
        this.executeAsync(success, err -> {
            System.err.println("An exception occurred while making a request");
            err.printStackTrace();
        });
    }

    @Override
    public void executeAsync(Consumer<? super T> success, Consumer<? super Throwable> failure) {
        callbackPool.submit(() -> {
            try {
                success.accept(execute());
            } catch (Exception e) {
                failure.accept(e);
            }
        });
    }
}
