package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.entities.impl.PteroAPIImpl;

/**
 * Used to create new {@link com.mattmalec.pterodactyl4j.application.entities.PteroApplication} or {@link com.mattmalec.pterodactyl4j.client.entities.PteroClient} instances.
 */
public class PteroBuilder {

    private String applicationUrl;
    private String token;

    @Deprecated
    public PteroBuilder(String applicationUrl, String token) {
        this.applicationUrl = applicationUrl;
        this.token = token;
    }

    @Deprecated
    public PteroBuilder() {}

    /**
     * Creates a {@link com.mattmalec.pterodactyl4j.application.entities.PteroApplication} instance
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
        return new PteroAPIImpl(url, token).asApplication();
    }

    /**
     * Creates a {@link com.mattmalec.pterodactyl4j.client.entities.PteroClient} instance
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
        return new PteroAPIImpl(url, token).asClient();
    }

    public PteroBuilder setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
        return this;
    }

    public PteroBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public String getApplicationUrl() {
        return this.applicationUrl;
    }

    public String getToken() {
        return this.token;
    }

    public PteroApplication buildApplication() {
        return new PteroAPIImpl(this.applicationUrl, this.token).asApplication();
    }
    public PteroClient buildClient() {
        return new PteroAPIImpl(this.applicationUrl, this.token).asClient();
    }
}
