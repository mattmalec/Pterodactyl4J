/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.requests;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.utils.Checks;
import com.mattmalec.pterodactyl4j.utils.Procedure;
import com.mattmalec.pterodactyl4j.utils.StreamUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} specification used to retrieve entities for paginated endpoints.
 * <br>Note that this implementation is not considered thread-safe as modifications to the cache are not done
 * with a lock. Calling methods on this class from multiple threads is not recommended.
 *
 * The PaginationAction provides some unique capabilites for iterating over the list of entities
 * <ul>
 *     <li>{@link #forEach(Consumer)} {@link #forEachAsync(Procedure, Consumer)}
 *     <br>These functions act like standard {@link java.util.List Lists}. Unlike the latter, calling these functions
 *     several times will always start the iteration at the beginning of the real list.</li>
 *     <li>{@link #forEachRemaining(Procedure)}, {@link #forEachRemainingAsync(Procedure, Consumer)}, {@link #takeRemainingAsync(int)}
 *     <br>The remaining functions are similar to {@link java.util.Queue#poll polling} from a {@link java.util.Queue Queue} while iterating.
 *     These can be helpful when you need to run several looping functions from the same PaginationAction. Calling these functions will start the iteration
 *     where the last iteration left off. It is recommended to set {@link #cache(boolean)} to true to allow the implementation to finish iterating over
 *     entities that have already been retrieved.</li>
 * </ul>
 *
 * <p><b>Examples</b>
 * <pre><code>
 *  /**
 *   * Retrieves servers until the specified limit is reached. The servers will be limited after being filtered by the owner.
 *   * If the owner doesn't have enough servers, this will iterate through all the servers. It is recommended to add an additional end condition.
 *   *&#47;
 *   public static {@literal List<ApplicationServer>} getServersByOwner(PteroApplication application, ApplicationUser user, int limit) {
 *     <u>PaginationAction<ApplicationServer></u> action = application.<u>retrieveServers</u>();
 *     Stream{@literal <ApplicationServer>} serverStream = action.stream()
 *             .limit(limit * 2) // this keeps things civilized
 *             .filter(server -> server.getOwnerIdLong() == user.getIdLong())
 *             .limit(limit); // limit on filtered stream
 *     return serverStream.collect(Collectors.toList());
 *   }
 * </code></pre>
 *
 * <pre><code>
 * /**
 *  * Iterates ClientServers in an async stream and stops once the limit has been reached.
 *  *&#47;
 *   public static void onEachServerAsync(PteroClient client, {@literal Consumer<ClientServer>} consumer, int limit) {
 *     if (limit < 1)
 *         return;
 *     <u>PaginationAction<ClientServer></u> action = client.<u>retrieveServers</u>();
 *     AtomicInteger counter = new AtomicInteger(limit);
 *     action.forEachAsync(server -> {
 *         consumer.accept(server);
 *         // if false the iteration is terminated; else it continues
 *         return counter.decrementAndGet() == 0;
 *     });
 *   }
 * </code></pre>
 *
 * @param  <T>
 *         The type of entity to paginate
 */
public interface PaginationAction<T> extends PteroAction<List<T>>, Iterable<T> {

    /**
     * Skips to the specified page for successive requests.
     * This will reset the {@link #getLast()} entity and cause a {@link NoSuchElementException} to be thrown
     * when attempting to get the last entity until a new retrieve action has been done.
     *
     * <p>Set this to {@code 1} to start from the first page
     *
     * @param  page
     *         The page to skip to
     *
     * @return The current PaginationAction implementation instance, useful for chaining
     */
    PaginationAction<T> skipTo(int page);

    /**
     * The current page used for pagination.
     * <br>This is updated by each retrieve action.
     *
     * @return The current page
     *
     * @see    #skipTo(int) Use skipTo(page) to change this
     */
    int getCurrentPage();

    /**
     * The total pages used for pagination.
     * <br>This is updated by each retrieve action.
     *
     * @return The total pages for iteration
     */
    int getTotalPages();
    
    @Override
    PaginationAction<T> timeout(long timeout, TimeUnit unit);

    @Override
    PaginationAction<T> deadline(long timestamp);

    /**
     * The current amount of cached entities for this PaginationAction
     *
     * @return int size of currently cached entities
     */
    int cacheSize();

    /**
     * Whether the cache of this PaginationAction is empty.
     * <br>Logically equivalent to {@code cacheSize() == 0}
     *
     * @return True, if no entities have been retrieved yet
     */
    boolean isEmpty();

    /**
     * The currently cached entities of recent execution tasks.
     * <br>Every {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} success
     * adds to this List. (Thread-Safe due to {@link java.util.concurrent.CopyOnWriteArrayList CopyOnWriteArrayList})
     *
     * <p><b>This <u>does not</u> contain all entities for the paginated endpoint unless the pagination has reached an end</b>
     * <br>It only contains those entities which already have been retrieved.
     *
     * @return Immutable {@link java.util.List List} containing all currently cached entities for this PaginationAction
     */
    List<T> getCached();

    /**
     * The most recent entity retrieved by this PaginationAction instance
     *
     * @throws java.util.NoSuchElementException
     *         If no entities have been retrieved yet (see {@link #isEmpty()})
     *
     * @return The most recent cached entity
     */
    T getLast();

    /**
     * The first cached entity retrieved by this PaginationAction instance
     *
     * @throws java.util.NoSuchElementException
     *         If no entities have been retrieved yet (see {@link #isEmpty()})
     *
     * @return The very first cached entity
     */
    T getFirst();

    /**
     * Sets the limit that should be used in the next PteroAction completion
     * call.
     *
     * <p>The specified limit may not be below the {@link #getMinLimit() Minimum Limit}
     *
     * <p><b>This limit represents how many entities will be retrieved per request and
     * <u>NOT</u> the maximum amount of entities that should be retrieved for iteration/sequencing.</b>
     * <br>{@code action.limit(50).execute()}
     * <br>is not the same as
     * <br>{@code action.stream().limit(50).collect(collector)}
     *
     *
     * @param  limit
     *         The limit to use
     *
     * @throws java.lang.IllegalArgumentException
     *         If the provided limit is out of range
     *
     * @return The current PaginationAction implementation instance, useful for chaining
     */
    PaginationAction<T> limit(int limit);

    /**
     * Whether already retrieved entities should be stored
     * within the internal cache. All cached entities will be
     * available from {@link #getCached()}.
     * <b>Default: true</b>
     * <br>This being disabled allows unused entities to be removed from
     * the memory heap by the garbage collector. If this is enabled this will not
     * take place until all references to this PaginationAction have been cleared.
     *
     * @param  enableCache
     *         Whether to enable entity cache
     *
     * @return The current PaginationAction implementation instance, useful for chaining
     */
    PaginationAction<T> cache(boolean enableCache);

    /**
     * Whether retrieved entities are stored within an
     * internal cache. If this is {@code false} entities
     * retrieved by the iterator or a call to a {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction}
     * terminal operation will not be retrievable from {@link #getCached()}.
     * <br>This being disabled allows unused entities to be removed from
     * the memory heap by the garbage collector. If this is enabled, this will not
     * take place until all references to this PaginationAction have been cleared.
     *
     * @return True, If entities will be cached
     */
    boolean isCacheEnabled();

    /**
     * The minimum limit that can be used for this PaginationAction
     * <br>Limits provided to {@link #limit(int)} must not be less
     * than the returned value.
     *
     * @return The minimum limit
     */
    int getMinLimit();

    /**
     * The currently used limit.
     *
     * @return limit
     */
    int getLimit();

    /**
     * Retrieves elements while the specified condition is met.
     *
     * @param  rule
     *         The rule which must be fulfilled for an element to be added,
     *         returns false to discard the element and finish the task
     *
     * @throws IllegalArgumentException
     *         If the provided rule is {@code null}
     *
     * @return {@link CompletableFuture} - Type {@link List List}
     *         <br>Future representing the fetch task, the list will be sorted most recent to oldest
     *
     * @see    #takeWhileAsync(int, Predicate)
     * @see    #takeUntilAsync(Predicate)
     */
    default CompletableFuture<List<T>> takeWhileAsync(Predicate<? super T> rule) {
        Checks.notNull(rule, "Rule");
        return takeUntilAsync(rule.negate());
    }

    /**
     * Retrieves elements while the specified condition is met.
     *
     * @param  limit
     *         The maximum amount of elements to collect or {@code 0} for no limit
     * @param  rule
     *         The rule which must be fulfilled for an element to be added,
     *         returns false to discard the element and finish the task
     *
     * @throws IllegalArgumentException
     *         If the provided rule is {@code null} or the limit is negative
     *
     * @return {@link CompletableFuture} - Type {@link List List}
     *         <br>Future representing the fetch task, the list will be sorted most recent to oldest
     *
     * @see    #takeWhileAsync(Predicate)
     * @see    #takeUntilAsync(int, Predicate)
     */
    default CompletableFuture<List<T>> takeWhileAsync(int limit, Predicate<? super T> rule) {
        Checks.notNull(rule, "Rule");
        return takeUntilAsync(limit, rule.negate());
    }

    /**
     * Retrieves elements until the specified condition is met.
     *
     * @param  rule
     *         The rule which must be fulfilled for an element to be discarded,
     *         returns true to discard the element and finish the task
     *
     * @throws IllegalArgumentException
     *         If the provided rule is {@code null}
     *
     * @return {@link CompletableFuture} - Type {@link List List}
     *         <br>Future representing the fetch task, the list will be sorted most recent to oldest
     *
     * @see    #takeWhileAsync(Predicate)
     * @see    #takeUntilAsync(int, Predicate)
     */
    default CompletableFuture<List<T>> takeUntilAsync(Predicate<? super T> rule) {
        return takeUntilAsync(0, rule);
    }

    /**
     * Retrieves elements until the specified condition is met.
     *
     * @param  limit
     *         The maximum amount of elements to collect or {@code 0} for no limit
     * @param  rule
     *         The rule which must be fulfilled for an element to be discarded,
     *         returns true to discard the element and finish the task
     *
     * @throws IllegalArgumentException
     *         If the provided rule is {@code null} or the limit is negative
     *
     * @return {@link CompletableFuture} - Type {@link List List}
     *         <br>Future representing the fetch task, the list will be sorted most recent to oldest
     *
     * @see    #takeWhileAsync(Predicate)
     * @see    #takeUntilAsync(int, Predicate)
     */
    default CompletableFuture<List<T>> takeUntilAsync(int limit, Predicate<? super T> rule) {
        Checks.notNull(rule, "Rule");
        Checks.notNegative(limit, "Limit");

        List<T> result = new ArrayList<>();
        CompletableFuture<List<T>> future = new CompletableFuture<>();
        CompletableFuture<?> handle = forEachAsync(element -> {
            if (rule.test(element))
                return false;
            result.add(element);
            return limit == 0 || limit > result.size();
        });
        handle.whenComplete((r, t) -> {
            if (t != null)
                future.completeExceptionally(t);
            else
                future.complete(result);
        });
        return future;
    }

    /**
     * Convenience method to retrieve an amount of entities from this pagination action.
     * <br>This also includes already cached entities similar to {@link #forEachAsync(Procedure)}.
     *
     * @param  amount
     *         The maximum amount to retrieve
     *
     * @return {@link java.util.concurrent.CompletableFuture CompletableFuture} - Type {@link java.util.List List}
     *
     * @see    #forEachAsync(Procedure)
     */
    CompletableFuture<List<T>> takeAsync(int amount);

    /**
     * Convenience method to retrieve an amount of entities from this pagination action.
     * <br>Unlike {@link #takeAsync(int)} this does not include already cached entities.
     *
     * @param  amount
     *         The maximum amount to retrieve
     *
     * @return {@link java.util.concurrent.CompletableFuture CompletableFuture} - Type {@link java.util.List List}
     *
     * @see    #forEachRemainingAsync(Procedure)
     */
    CompletableFuture<List<T>> takeRemainingAsync(int amount);

    /**
     * Iterates over all entities until the provided action returns {@code false}
     * <br>This operation is different from {@link #forEach(Consumer)} as it
     * uses successive {@link #executeAsync()} tasks to iterate each entity in callback threads instead of
     * the calling active thread.
     * This means that this method fully works on different threads to retrieve new entities.
     * <p><b>This iteration will include already cached entities, in order to exclude cached
     * entities use {@link #forEachRemainingAsync(Procedure)}</b>
     *
     * <h4>Example</h4>
     * <pre>{@code
     * // stops servers until it finds another that is offline
     * public void stopServers(PaginationAction<ClientServer> action) {
     *     action.forEachAsync(server -> {
     *         UtilizationState state = server.retrieveUtilization()
     *             .map(Utilization::getState()).execute();
     *         if (state != UtilizationState.OFFLINE)
     *             server.stop().executeAsync();
     *         else
     *             return false;
     *         return true;
     *     });
     * }
     * }</pre>
     *
     * @param  action
     *         {@link com.mattmalec.pterodactyl4j.utils.Procedure Procedure} returning {@code true} if iteration should continue
     *
     * @throws java.lang.IllegalArgumentException
     *         If the provided Procedure is {@code null}
     *
     * @return {@link java.util.concurrent.CompletableFuture CompletableFuture} that can be cancelled to stop iteration from outside
     */
    default CompletableFuture<?> forEachAsync(Procedure<? super T> action) {
        return forEachAsync(action, PteroAction.getDefaultFailure());
    }

    /**
     * Iterates over all entities until the provided action returns {@code false}
     * <br>This operation is different from {@link #forEach(Consumer)} as it
     * uses successive {@link #executeAsync()} tasks to iterate each entity in callback threads instead of
     * the calling active thread.
     * This means that this method fully works on different threads to retrieve new entities.
     *
     * <p><b>This iteration will include already cached entities, in order to exclude cached
     * entities use {@link #forEachRemainingAsync(Procedure, Consumer)}</b>
     *
     * <h4>Example</h4>
     * <pre>{@code
     * // stops servers until it finds another that is offline
     * public void stopServers(PaginationAction<ClientServer> action) {
     *     action.forEachAsync(server -> {
     *         UtilizationState state = server.retrieveUtilization()
     *             .map(Utilization::getState()).execute();
     *         if (state != UtilizationState.OFFLINE)
     *             server.stop().executeAsync();
     *         else
     *             return false;
     *         return true;
     *     }, Throwable::printStackTrace);
     * }
     * }</pre>
     *
     * @param  action
     *         {@link com.mattmalec.pterodactyl4j.utils.Procedure Procedure} returning {@code true} if iteration should continue
     * @param  failure
     *         {@link java.util.function.Consumer Consumer} that should handle any throwables from the action
     *
     * @throws java.lang.IllegalArgumentException
     *         If the provided Procedure or the failure Consumer is {@code null}
     *
     * @return {@link java.util.concurrent.CompletableFuture CompletableFuture} that can be cancelled to stop iteration from outside
     */
    CompletableFuture<?> forEachAsync(Procedure<? super T> action, Consumer<? super Throwable> failure);

    /**
     * Iterates over all remaining entities until the provided action returns {@code false}
     * <br>This operation is different from {@link #forEachRemaining(Procedure)} as it
     * uses successive {@link #executeAsync()} tasks to iterate each entity in callback threads instead of
     * the calling active thread.
     * This means that this method fully works on different threads to retrieve new entities.
     *
     * <p><b>This iteration will exclude already cached entities, in order to include cached
     * entities use {@link #forEachAsync(Procedure)}</b>
     *
     * <h4>Example</h4>
     * <pre>{@code
     * // stops servers until it finds another that is offline
     * public void stopServers(PaginationAction<ClientServer> action) {
     *     action.forEachRemainingAsync(server -> {
     *         UtilizationState state = server.retrieveUtilization()
     *             .map(Utilization::getState()).execute();
     *         if (state != UtilizationState.OFFLINE)
     *             server.stop().executeAsync();
     *         else
     *             return false;
     *         return true;
     *     });
     * }
     * }</pre>
     *
     * @param  action
     *         {@link com.mattmalec.pterodactyl4j.utils.Procedure Procedure} returning {@code true} if iteration should continue
     *
     * @throws java.lang.IllegalArgumentException
     *         If the provided Procedure is {@code null}
     *
     * @return {@link java.util.concurrent.Future Future} that can be cancelled to stop iteration from outside
     */
    default CompletableFuture<?> forEachRemainingAsync(Procedure<? super T> action) {
        return forEachRemainingAsync(action, PteroAction.getDefaultFailure());
    }

    /**
     * Iterates over all remaining entities until the provided action returns {@code false}
     * <br>This operation is different from {@link #forEachRemaining(Procedure)} as it
     * uses successive {@link #executeAsync()} tasks to iterate each entity in callback threads instead of
     * the calling active thread.
     * This means that this method fully works on different threads to retrieve new entities.
     *
     * <p><b>This iteration will exclude already cached entities, in order to include cached
     * entities use {@link #forEachAsync(Procedure, Consumer)}</b>
     *
     * <h4>Example</h4>
     * <pre>{@code
     * // stops servers until it finds another that is offline
     * public void stopServers(PaginationAction<ClientServer> action) {
     *     action.forEachAsync(server -> {
     *         UtilizationState state = server.retrieveUtilization()
     *             .map(Utilization::getState()).execute();
     *         if (state != UtilizationState.OFFLINE)
     *             server.stop().executeAsync();
     *         else
     *             return false;
     *         return true;
     *     }, Throwable::printStackTrace);
     * }
     * }</pre>
     *
     * @param  action
     *         {@link com.mattmalec.pterodactyl4j.utils.Procedure Procedure} returning {@code true} if iteration should continue
     * @param  failure
     *         {@link java.util.function.Consumer Consumer} that should handle any throwables from the action
     *
     * @throws java.lang.IllegalArgumentException
     *         If the provided Procedure or the failure Consumer is {@code null}
     *
     * @return {@link java.util.concurrent.CompletableFuture CompletableFuture} that can be cancelled to stop iteration from outside
     */
    CompletableFuture<?> forEachRemainingAsync(Procedure<? super T> action, Consumer<? super Throwable> failure);

    /**
     * Iterates over all remaining entities until the provided action returns {@code false}
     * <br>Skipping past already cached entities to iterate all remaining entities of this PaginationAction.
     *
     * <p><b>This is a blocking operation that might take a while to complete</b>
     *
     * @param  action
     *         The {@link com.mattmalec.pterodactyl4j.utils.Procedure Procedure}
     *         which should return {@code true} to continue iterating
     */
    void forEachRemaining(Procedure<? super T> action);

    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), Spliterator.IMMUTABLE);
    }

    /**
     * A sequential {@link java.util.stream.Stream Stream} with this PaginationAction as its source.
     *
     * @return A sequential {@code Stream} over the elements in this PaginationAction
     */
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * {@link PaginationIterator PaginationIterator}
     * that will iterate over all entities for this PaginationAction.
     *
     * @return The new PaginationIterator
     */
    @Override
    PaginationIterator<T> iterator();

    /**
     * Returns a completed List of entitites.
     *
     * <p>To retrieve new entities after reaching the end of the current cache, this method will
     * request a List of new entities through internal calls of {@link com.mattmalec.pterodactyl4j.PteroAction#executeAsync() PteroAction.executeAsync()}.
     * <p><b>It is recommended to use {@link #forEachAsync(Procedure)} instead</b>, but for the sake of, use the highest possible limit for this task. (see {@link #limit(int)})
     *
     * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link T &lt;T&gt;}
     */
    default PteroAction<List<T>> all() {
        return PteroActionImpl.onExecute(getP4J(), () -> stream().collect(StreamUtils.toUnmodifiableList()));
    }

    /**
     * Iterator implementation for a {@link PaginationAction PaginationAction}.
     * <br>This iterator will first iterate over all currently cached entities and continue to retrieve new entities
     * as needed.
     *
     * <p>To retrieve new entities after reaching the end of the current cache, this iterator will
     * request a List of new entities through a call of {@link com.mattmalec.pterodactyl4j.PteroAction#execute() PteroAction.execute()}.
     * <br><b>It is recommended to use the highest possible limit for this task. (see {@link #limit(int)})</b>
     */
    class PaginationIterator<E> implements Iterator<E> {
        private Queue<E> items;
        private final Supplier<List<E>> supply;

        public PaginationIterator(Collection<E> queue, Supplier<List<E>> supply) {
            this.items = new LinkedList<>(queue);
            this.supply = supply;
        }

        @Override
        public boolean hasNext() {
            if (items == null)
                return false;
            if (!hitEnd())
                return true;

            if (items.addAll(supply.get()))
                return true;

            items = null;
            return false;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException("Reached end of pagination task");
            return items.poll();
        }

        protected boolean hitEnd() {
            return items.isEmpty();
        }
    }
}