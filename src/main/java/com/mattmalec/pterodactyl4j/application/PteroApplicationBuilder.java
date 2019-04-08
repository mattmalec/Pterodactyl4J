package com.mattmalec.pterodactyl4j.application;

import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;

public class PteroApplicationBuilder implements PteroBuilder {

    private String token;
    private String applicationUrl;

    public PteroApplicationBuilder(String applicationUrl, String token) {
        this.token = token;
        this.applicationUrl = applicationUrl;
    }
    public PteroApplicationBuilder() {
    }
    @Override
    public PteroApplicationBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public PteroApplicationBuilder setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
        return this;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public String getApplicationUrl() {
        return this.applicationUrl;
    }
    public PteroApplication build() {
        if(this.applicationUrl == null || this.applicationUrl.isEmpty())
            throw new IllegalArgumentException("Application URL cannot be blank or null");
        if(this.token == null || this.token.isEmpty())
            throw new IllegalArgumentException("Authorization token cannot be blank or null");
        return new PteroApplicationImpl(this.applicationUrl, this.token);
    }
}
