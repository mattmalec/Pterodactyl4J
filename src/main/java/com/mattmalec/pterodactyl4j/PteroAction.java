package com.mattmalec.pterodactyl4j;

import java.util.function.Consumer;

/**
 * Represents a terminal between the user and the API of choice.
 */
public interface PteroAction<T> {

    T execute();

    /**
     * Submits a Request for execution.
     * <br>Using the default callback function.
     * <p>To access the response, you can use {@link #executeAsync(Consumer)}.
     * To handle failures, use {@link #executeAsync(Consumer, Consumer)}.
     *
     * <p><b>This method is asynchronous</b>
     *
     *
     * @see #executeAsync(Consumer)
     * @see #executeAsync(Consumer, Consumer)
     */
    default void executeAsync() {
        executeAsync(null);
    }

    /**
     * Submits a Request for execution.
     * <br>Using the default failure callback function.
     * <p>To handle failures, use {@link #executeAsync(Consumer, Consumer)}.
     *
     * <p><b>This method is asynchronous</b>
     *
     * @param success
     *        The success callback that will be called at a convenient time for P4J. (can be null)
     *
     * @see #executeAsync(Consumer, Consumer)
     */
    default void executeAsync(Consumer<? super T> success) {
        executeAsync(success, null);
    }


    /**
     * Submits a Request for execution.
     * <p><b>This method is asynchronous</b>
     *
     * @param success
     *        The success callback that will be called at a convenient time for P4J. (can be null to use default)
     *
     * @param failure
     *        The failure callback that will be called if the Request encounters an exception at its execution point. (can be null to use default)
     */
    void executeAsync(Consumer<? super T> success, Consumer<? super Throwable> failure);

}
