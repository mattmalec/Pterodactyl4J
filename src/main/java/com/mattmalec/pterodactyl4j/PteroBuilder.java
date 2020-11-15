package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.entities.impl.PteroAPIImpl;

public class PteroBuilder {

    private String token;
    private String applicationUrl;

    public PteroBuilder(String applicationUrl, String token) {
        this.token = token;
        this.applicationUrl = applicationUrl;
    }
    public PteroBuilder() {}

    public PteroBuilder setToken(String token) {
        this.token = token;
        return this;
    }


    public PteroBuilder setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
        return this;
    }

    public String getToken() {
        return this.token;
    }


    public String getApplicationUrl() {
        return this.applicationUrl;
    }
    public PteroApplication buildApplication() {
        return new PteroAPIImpl(this.applicationUrl, this.token).asApplication();
    }
    public PteroClient buildClient() {
        return new PteroAPIImpl(this.applicationUrl, this.token).asClient();
    }
}
