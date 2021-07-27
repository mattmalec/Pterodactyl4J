package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.entities.P4J;
import com.mattmalec.pterodactyl4j.entities.impl.P4JImpl;
import com.mattmalec.pterodactyl4j.utils.Checks;
import com.mattmalec.pterodactyl4j.utils.NamedThreadFactory;
import okhttp3.OkHttpClient;

import java.util.concurrent.*;

/**
 * Used to create new {@link com.mattmalec.pterodactyl4j.application.entities.PteroApplication} or {@link com.mattmalec.pterodactyl4j.client.entities.PteroClient} instances.
 */
public class PteroBuilder {

    private String applicationUrl;
    private String token;

    private OkHttpClient httpClient = null;
    private ExecutorService actionPool = null;
    private ExecutorService callbackPool = null;
    private ScheduledExecutorService rateLimitPool = null;
    private ExecutorService supplierPool = null;
    private OkHttpClient webSocketClient = null;

    private PteroBuilder(String applicationUrl, String token) {
        this.applicationUrl = applicationUrl;
        this.token = token;
    }

    @Deprecated
    public PteroBuilder() {
        throw new UnsupportedOperationException("You cannot use the deprecated constructor anymore. Please use create(...), createApplication(...), or createClient(...) instead.");
    }

    /**
     * Creates a {@link com.mattmalec.pterodactyl4j.application.entities.PteroApplication} instance with the recommended default settings.
     *
     * <br>Note that these defaults can potentially change in the future.
     *
     * <p>You should use this method if you'd like to continue using P4J as is.
     *
     * @param url
     *        The URL for your panel
     *
     * @param  token
     *         The Application API key
     *
     * @return A new {@link com.mattmalec.pterodactyl4j.application.entities.PteroApplication} instance
     *
     */
    public static PteroApplication createApplication(String url, String token) {
        return new PteroBuilder(url, token).buildApplication();
    }

    /**
     * Creates a {@link com.mattmalec.pterodactyl4j.client.entities.PteroClient} instance with the recommended default settings.
     * <br>Note that these defaults can potentially change in the future.
     *
     * <p>You should use this method if you'd like to continue using P4J as is.
     *
     * @param url
     *        The URL for your panel
     *
     * @param  token
     *         The Client API key
     *
     * @return A new {@link com.mattmalec.pterodactyl4j.client.entities.PteroClient} instance
     *
     */
    public static PteroClient createClient(String url, String token) {
        return new PteroBuilder(url, token).buildClient();
    }

    /**
    * Creates a PteroBuilder with the predefined panel URL and API key.
     *
     * @param url
     *        The URL for your panel
     *
     * @param  token
     *         The API key
     *
     * @return The new PteroBuilder
     **/
    public static PteroBuilder create(String url, String token) {
        return new PteroBuilder(url, token);
    }

    /**
     * Sets the panel URL that will be used when P4J makes a Request
     *
     * @param  applicationUrl
     *         The URL of the Pterodactyl panel
     *
     * @return The PteroBuilder instance. Useful for chaining.
     */
    public PteroBuilder setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
        return this;
    }

    /**
     * Sets the API key that will be used when P4J makes a Request
     *
     * @param  token
     *         The API key for the user or application
     *
     * @return The PteroBuilder instance. Useful for chaining.
     */
    public PteroBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * Sets the {@link okhttp3.OkHttpClient OkHttpClient} that will be used by P4Js requester.
     *
     * <br>This can be used to set things such as connection timeout and proxy.
     *
     * @param  client
     *         The new {@link okhttp3.OkHttpClient OkHttpClient} to use
     *
     * @return The PteroBuilder instance. Useful for chaining.
     */
    public PteroBuilder setHttpClient(OkHttpClient client) {
        this.httpClient = client;
        return this;
    }

    /**
     * Sets the {@link ExecutorService ExecutorService} that should be used in the P4J request handler.
     *
     * <br><b>Only change this pool if you know what you're doing.</b>
     *
     * <p>This is used to queue the request and finalize its request body for {@link PteroAction#executeAsync()} tasks.
     *
     * <p>Default: {@link ThreadPoolExecutor} with 1 thread.
     *
     * @param  pool
     *         The thread pool to use for action handling
     *
     * @return The PteroBuilder instance. Useful for chaining.
     */
    public PteroBuilder setActionPool(ExecutorService pool) {
        this.actionPool = pool;
        return this;
    }

    /**
     * Sets the {@link ExecutorService ExecutorService} that should be used in
     * the P4J callback handler which consists of {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} callbacks.
     * <br><b>Only change this pool if you know what you're doing.</b>
     *
     * <p>This is used to handle callbacks of {@link PteroAction#executeAsync()}, similarly it is used to
     * finish {@link PteroAction#execute()} tasks which build on queue.
     *
     * <p>Default: {@link ForkJoinPool#commonPool()}
     *
     * @param  pool
     *         The thread pool to use for callback handling
     *
     * @return The PteroBuilder instance. Useful for chaining.
     */
    public PteroBuilder setCallbackPool(ExecutorService pool) {
        this.callbackPool = pool;
        return this;
    }

    /**
     * Sets the {@link ScheduledExecutorService ScheduledExecutorService} that should be used in
     * the P4J rate limiter. Changing this can affect the P4J behavior for PteroAction execution
     * and should be handled carefully.
     *
     * <br><b>Only change this pool if you know what you're doing.</b>
     *
     * <p>This is used by the rate limiter to handle backoff delays by using scheduled executions.
     *
     * <p>Default: {@link ScheduledThreadPoolExecutor} with 5 threads.
     *
     * @param  pool
     *         The thread pool to use for rate limiting
     *
     * @return The PteroBuilder instance. Useful for chaining.
     */
    public PteroBuilder setRateLimitPool(ScheduledExecutorService pool) {
        this.rateLimitPool = pool;
        return this;
    }

    /**
     * Sets the {@link ExecutorService ExecutorService} that should be used in
     * the P4J Action CompletableFutures.
     *
     * <br><b>Only change this pool if you know what you're doing.</b>
     *
     * <p>This is used to execute Suppliers mainly used by PteroActions that aren't requests.
     *
     * <p>Default: {@link ThreadPoolExecutor} with 3 threads.
     *
     * @param  pool
     *         The thread pool to use for CompletableFutures
     *
     * @return The PteroBuilder instance. Useful for chaining.
     */
    public PteroBuilder setSupplierPool(ExecutorService pool) {
        this.supplierPool = pool;
        return this;
    }

    /**
     * Sets the {@link okhttp3.OkHttpClient OkHttpClient} that will be used by P4Js websocket client.
     * <br>This can be used to set things such as connection timeout and proxy.
     *
     * @param  client
     *         The new {@link okhttp3.OkHttpClient OkHttpClient} to use
     *
     * @return The PteroBuilder instance. Useful for chaining.
     */
    public PteroBuilder setWebSocketClient(OkHttpClient client) {
        this.webSocketClient = client;
        return this;
    }

    /**
     * The URL of the Pterodactyl panel that is currently being used with P4J.
     *
     * @return The panel URL
     */
    public String getApplicationUrl() {
        return this.applicationUrl;
    }

    /**
     * The API key that is currently being used for P4J authentication.
     *
     * @return The API key
     */
    public String getToken() {
        return this.token;
    }

    private P4J build() {
        Checks.notBlank(token, "API Key");
        Checks.notBlank(applicationUrl, "Application URL");
        if (httpClient == null)
            this.httpClient = new OkHttpClient();
        if (callbackPool == null)
            this.callbackPool = ForkJoinPool.commonPool();
        if (actionPool == null)
            this.actionPool = Executors.newSingleThreadExecutor(new NamedThreadFactory("Action"));
        if (rateLimitPool == null)
            this.rateLimitPool = Executors.newScheduledThreadPool(5, new NamedThreadFactory("RateLimit"));
        if (supplierPool == null)
            this.supplierPool = Executors.newFixedThreadPool(3, new NamedThreadFactory("Supplier"));
        if (webSocketClient == null)
            this.webSocketClient = new OkHttpClient();
        return new P4JImpl(this.applicationUrl, this.token, this.httpClient, this.callbackPool, this.actionPool,
                this.rateLimitPool, this.supplierPool, this.webSocketClient);
    }

    /**
     * Builds a new {@link com.mattmalec.pterodactyl4j.application.entities.PteroApplication PteroApplication} instance
     * and uses the provided panel URL and application API key to make requests.
     *
     * <p>This provides access to the <b>Application API</b>. Use a {@link PteroClient PteroClient} if you need access
     * to the <b>Client API</b>.
     *
     * @throws IllegalArgumentException
     *         If the provided URL or token is empty or null.
     *
     * @return A PteroApplication instance that is ready to execute requests.
     *
     * @see PteroBuilder#buildClient()
     */
    public PteroApplication buildApplication() {
        return build().asApplication();
    }

    /**
     * Builds a new {@link com.mattmalec.pterodactyl4j.client.entities.PteroClient PteroClient} instance
     * and uses the provided panel URL and client API key to make requests and offer websocket access.
     *
     * <p>This provides access to the <b>Client API</b>. Use a
     * {@link com.mattmalec.pterodactyl4j.application.entities.PteroApplication PteroApplication} if you need access
     * to the <b>Application API</b>.
     *
     * @throws IllegalArgumentException
     *         If the provided URL or token is empty or null.
     *
     * @return A PteroClient instance that is ready to execute requests.
     *
     * @see PteroBuilder#buildApplication()
     */
    public PteroClient buildClient() { return build().asClient(); }
}
