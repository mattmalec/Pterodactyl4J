package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.exceptions.RateLimitedException;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;

import java.util.function.Consumer;

/**
 * Represents a terminal between the user and P4J.
 */
public interface PteroAction<T> {

    /**
     * The default failure callback used when none is provided in {@link #executeAsync(Consumer, Consumer)}.
     *
     * @return The fallback failure consumer
     */
    static Consumer<? super Throwable> getDefaultFailure() {
        return PteroActionImpl.DEFAULT_FAILURE;
    }


    /**
     * The default success callback used when none is provided in {@link #executeAsync(Consumer, Consumer)} or {@link #executeAsync(Consumer)}.
     *
     * @return The fallback success consumer
     */
    static Consumer<Object> getDefaultSuccess() {
        return PteroActionImpl.DEFAULT_SUCCESS;
    }

    /**
     * Blocks the current Thread and awaits the completion of a Request.
     * <br>Used for synchronous logic.
     *
     * <p><b>This method is synchronous</b>
     *
     * @return The response value
     **/
    default T execute() {
        return execute(true);
    }

    /**
     * Blocks the current Thread and awaits the completion of a Request.
     * <br>Used for synchronous logic.
     *
     * <p><b>This method is synchronous</b>
     *
     * @param  shouldQueue
     *         Whether this should automatically handle rate limitations (default true)
     *
     * @throws RateLimitedException
     *         If the Request was rate limited and {@code shouldQueue} is false.
     *         Use {@link #execute()} to avoid this Exception.
     * @return The response value
     **/
    T execute(boolean shouldQueue) throws RateLimitedException;

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
