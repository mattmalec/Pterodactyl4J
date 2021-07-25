package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.entities.PteroAPI;
import com.mattmalec.pterodactyl4j.exceptions.RateLimitedException;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.operators.*;
import com.mattmalec.pterodactyl4j.utils.Checks;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a terminal between the user and P4J.
 * <br>This is used to offer users the ability to decide how P4J should limit a Request.
 *
 * <p>Methods that return an instance of PteroAction require an additional step
 * to complete the execution. Thus the user needs to append a follow-up method.
 *
 * <p>A default PteroAction is issued with the following operations:
 * <ul>
 *     <li>{@link #executeAsync()}, {@link #executeAsync(Consumer)}, {@link #executeAsync(Consumer, Consumer)}
 *     <br>The fastest and most simplistic way to execute a PteroAction is to execute it asynchronously.
 *     <br>This method has two optional callback functions, one with the generic type and another with a failure exception.</li>
 *
 *     <li>{@link #execute()}, {@link #execute(boolean)}
 *     <br>This will simply block the thread and return the Request result, or throw an exception.
 *     <br>An optional boolean parameter can be passed to disable automated rate limit handling (not recommended)</li>
 * </ul>
 *
 * The most efficient way to use a PteroAction is by using the asynchronous {@link #executeAsync()} operations.
 * <br>These allow users to provide success and failure callbacks which will be called at a convenient time for the wrapper.
 *
 * <p><b>Developer Note:</b> It is generally a good practice to use asynchronous logic. Blocking threads requires resources
 * which can be avoided by using callbacks:
 *  <br>{@link #executeAsync(Consumer)} {@literal >} {@link #execute()}
 *
 * @param <T>
 *        The generic response type for this PteroAction
 */
public interface PteroAction<T> {

    /**
     * The current Pterodactyl instance
     *
     * @return The corresponding API instance
     */
    PteroAPI getApi();

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

    /**
     * Intermediate operator that returns a modified PteroAction.
     *
     * <p>This does not modify the instance but returns a new PteroAction which will apply
     * the map function on successful execution.
     *
     * <h2>Example</h2>
     * <pre>{@code
     * public PteroAction<String> retrieveServerName(String id) {
     *     return application.retrieveServerById(id)
     *                 .map(ApplicationServer::getName);
     * }
     * }</pre>
     *
     * @param  map
     *         The mapping function to apply to the action result
     *
     * @param  <O>
     *         The target output type
     *
     * @return PteroAction for the mapped type
     *
     */
    default <O> PteroAction<O> map(Function<? super T, ? extends O> map) {
        Checks.notNull(map, "Function");
        return new MapPteroAction<>(this, map);
    }

    /**
     * Supply a fallback value when the PteroAction fails for any reason.
     *
     * <p>This does not modify the instance but returns a new PteroAction which will apply
     * the map function on failed execution.
     *
     * <h2>Example</h2>
     * <pre>{@code
     * public PteroAction<String> retrieveServerName(String id) {
     *     return application.retrieveServerById(id)
     *                 .map(ApplicationServer::getName)
     *                 .onErrorMap(__ -> "Unknown server");
     * }
     * }</pre>
     *
     * @param  map
     *         The mapping function which provides the fallback value to use
     *
     * @throws IllegalArgumentException
     *         If the mapping function is null
     *
     * @return PteroAction with fallback handling
     */
    default PteroAction<T> onErrorMap(Function<? super Throwable, ? extends T> map) {
        return onErrorMap(null, map);
    }

    /**
     * Supply a fallback value when the PteroAction fails for a specific reason.
     *
     * <p>This does not modify the instance but returns a new PteroAction which will apply
     * the map function on failed execution.
     *
     * <h2>Example</h2>
     * <pre>{@code
     * public PteroAction<String> retrieveServerName(String id, boolean handleNotFound) {
     *     return application.retrieveServerById(id)
     *                 .map(ApplicationServer::getName)
     *                 .onErrorMap((x) -> handleNotFound, __ -> "Unknown server");
     * }
     * }</pre>
     *
     * @param  condition
     *         A condition that must return true to apply this fallback
     * @param  map
     *         The mapping function which provides the fallback value to use
     *
     * @throws IllegalArgumentException
     *         If the mapping function is null
     *
     * @return PteroAction with fallback handling
     */
    default PteroAction<T> onErrorMap(Predicate<? super Throwable> condition, Function<? super Throwable, ? extends T> map) {
        Checks.notNull(map, "Function");
        return new MapErrorPteroAction<>(this, condition == null ? (x) -> true : condition, map);
    }

    default <O> PteroAction<O> flatMap(Function<? super T, ? extends PteroAction<O>> flatMap) {
        return flatMap(null, flatMap);
    }
    
    /**
     * Intermediate operator that returns a modified PteroAction.
     *
     * <p>This does not modify the instance but returns a new PteroAction which will apply
     * the map function on successful execution. This will execute the result of both PteroActions.
     * <br>The provided PteroAction must not be null!
     *
     * <h2>Example</h2>
     * <pre>{@code
     * public void startServer(String identifier) {
     *     client.retrieveServerByIdentifier(identifier) // retrieve the client server
     *         .flatMap(ClientServer::start) // start the server
     *         .executeAsync(__ -> System.out.println("Starting server " + identifier));
     * }
     * }</pre>
     *
     * @param  condition
     *         A condition predicate that decides whether to apply the flat map operator or not
     * @param  flatMap
     *         The mapping function to apply to the action result, must return a PteroAction
     *
     * @param  <O>
     *         The target output type
     *
     * @return PteroAction for the mapped type
     *
     * @see    #flatMap(Function)
     * @see    #map(Function)
     */
    default <O> PteroAction<O> flatMap(Predicate<? super T> condition, Function<? super T, ? extends PteroAction<O>> flatMap) {
        Checks.notNull(flatMap, "Function");
        return new FlatMapPteroAction<>(this, condition, flatMap);
    }

    /**
     * Supply a fallback value when the PteroAction fails for a any reason.
     *
     * <p>This does not modify the instance but returns a new PteroAction which will apply
     * the map function on failed execution.
     *
     * <h2>Example</h2>
     * <pre>{@code
     * public void deleteServer(ApplicationServer as, PteroClient client) {
     *        client.retrieveServerByIdentifier(as.getIdentifier())
     *       .flatMap(s ->
     *           s.sendCommand("say Deleting server in 5 seconds...")
     *           .delay(5, TimeUnit.SECONDS)
     *           .flatMap(__ -> s.kill())
     *       ).flatMap(__ -> as.getController().delete(false))
     *       .onErrorFlatMap(__ -> as.getController().delete(true))
     *       .executeAsync();
     * }
     * }</pre>
     *
     * @param  map
     *         The mapping function which provides the fallback action to use
     *
     * @throws IllegalArgumentException
     *         If the mapping function is null
     *
     * @return PteroAction with fallback handling
     */
    default PteroAction<T> onErrorFlatMap(Function<? super Throwable, ? extends PteroAction<? extends T>> map) {
        return onErrorFlatMap(null, map);
    }

    /**
     * Supply a fallback value when the PteroAction fails for a specific reason.
     *
     * <p>This does not modify the instance but returns a new PteroAction which will apply
     * the map function on failed execution.
     *
     * <h2>Example</h2>
     * <pre>{@code
     * public void deleteServer(ApplicationServer as, PteroClient client, boolean handleDeletionFailure) {
     *        client.retrieveServerByIdentifier(as.getIdentifier())
     *       .flatMap(s ->
     *           s.sendCommand("say Deleting server in 5 seconds...")
     *           .delay(5, TimeUnit.SECONDS)
     *           .flatMap(__ -> s.kill())
     *       ).flatMap(__ -> as.getController().delete(false))
     *       .onErrorFlatMap((x) -> handleDeletionFailure, __ -> as.getController().delete(true))
     *       .executeAsync();
     * }
     * }</pre>
     *
     * @param  condition
     *         A condition that must return true to apply this fallback
     * @param  map
     *         The mapping function which provides the fallback action to use
     *
     * @throws IllegalArgumentException
     *         If the mapping function is null
     *
     * @return PteroAction with fallback handling
     */
    default PteroAction<T> onErrorFlatMap(Predicate<? super Throwable> condition, 
                                          Function<? super Throwable, ? extends PteroAction<? extends T>> map) {
        Checks.notNull(map, "Function");
        return new FlatMapErrorPteroAction<>(this, condition == null ? (x) -> true : condition, map);
    }
    
    /**
     * Intermediate operator that returns a modified PteroAction.
     *
     * <p>This does not modify the instance but returns a new PteroAction which will delay its result by the provided delay.
     *
     * <h2>Example</h2>
     * <pre>{@code
     * public PteroAction<Void> selfDestruct(ClientServer server) {
     *        server.sendCommand("say Stopping server in 30 seconds...")
     *        .delay(Duration.ofSeconds(15))
     *        .flatMap(__ -> server.sendCommand("Stopping server in 15 seconds...")
     *        .delay(Duration.ofSeconds(5))
     *        .flatMap(__ -> server.sendCommand("Stopping server in 10 seconds...")
     *        .delay(Duration.ofSeconds(5))
     *        .flatMap(__ -> server.sendCommand("Stopping server in 5 seconds...")
     *        .delay(Duration.ofSeconds(5))
     *        .flatMap(__ server.stop());
     * }
     * }</pre>
     *
     * @param  duration
     *         The delay
     *
     * @return PteroAction with delay
     */
    default PteroAction<T> delay(Duration duration) {
        return delay(duration, null);
    }

    /**
     * Intermediate operator that returns a modified PteroAction.
     *
     * <p>This does not modify the instance but returns a new PteroAction which will delay its result by the provided delay.
     *
     * <h2>Example</h2>
     * <pre>{@code
     * public PteroAction<Void> selfDestruct(ClientServer server) {
     *        server.sendCommand("say Stopping server in 5 seconds...")
     *        .delay(Duration.ofSeconds(5), scheduler)
     *        .flatMap(__ server.stop());
     * }
     * }</pre>
     *
     * @param  duration
     *         The delay
     * @param  scheduler
     *         The scheduler to use, null to use {@link PteroAPI#getRateLimitPool()}
     *
     * @return PteroAction with delay
     */
    default PteroAction<T> delay(Duration duration, ScheduledExecutorService scheduler) {
        Checks.notNull(duration, "Duration");
        return new DelayPteroAction<>(this, TimeUnit.MILLISECONDS, duration.toMillis(), scheduler);
    }

    /**
     * Intermediate operator that returns a modified PteroAction.
     *
     * <p>This does not modify the instance but returns a new PteroAction which will delay its result by the provided delay.
     *
     * <h2>Example</h2>
     * <pre>{@code
     * public PteroAction<Void> selfDestruct(ClientServer server) {
     *        server.sendCommand("say Stopping server in 5 seconds...")
     *        .delay(5, TimeUnit.SECONDS)
     *        .flatMap(__ server.stop());
     * }
     * }</pre>
     *
     * @param  delay
     *         The delay value
     * @param  unit
     *         The time unit for the delay value
     *
     * @return PteroAction with delay
     */
    default PteroAction<T> delay(long delay, TimeUnit unit) {
        return delay(delay, unit, null);
    }

    /**
     * Intermediate operator that returns a modified PteroAction.
     *
     * <p>This does not modify the instance but returns a new PteroAction which will delay its result by the provided delay.
     *
     * <h2>Example</h2>
     * <pre>{@code
     * public PteroAction<Void> selfDestruct(ClientServer server) {
     *        server.sendCommand("say Stopping server in 5 seconds...")
     *        .delay(5, TimeUnit.SECONDS, scheduler)
     *        .flatMap(__ server.stop());
     * }
     * }</pre>
     *
     * @param  delay
     *         The delay value
     * @param  unit
     *         The time unit for the delay value
     * @param  scheduler
     *         The scheduler to use, null to use {@link PteroAPI#getRateLimitPool()}
     *
     * @return PteroAction with delay
     */
    default PteroAction<T> delay(long delay, TimeUnit unit, ScheduledExecutorService scheduler) {
        Checks.notNull(unit, "TimeUnit");
        return new DelayPteroAction<>(this, unit, delay, scheduler);
    }


}
