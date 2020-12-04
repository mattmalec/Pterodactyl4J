package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.entities.impl.PteroAPIImpl;

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

    public static PteroApplication createApplication(String url, String token) {
        return new PteroAPIImpl(url, token).asApplication();
    }

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
