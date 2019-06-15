package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.entities.PteroAPI;
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
    public PteroAPI build() {
        return new PteroAPIImpl(this.applicationUrl, this.token);
    }
}
