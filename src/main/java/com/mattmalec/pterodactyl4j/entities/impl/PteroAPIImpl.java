package com.mattmalec.pterodactyl4j.entities.impl;

import com.mattmalec.pterodactyl4j.entities.PteroAPI;
import com.mattmalec.pterodactyl4j.requests.Requester;

public class PteroAPIImpl implements PteroAPI {

    private String token;
    private String applicationUrl;
    private Requester requester;

    public PteroAPIImpl(String applicationUrl, String token) {
        this.token = token;
        this.applicationUrl = applicationUrl;
        this.requester = new Requester(this);
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public Requester getRequester() {
        return requester;
    }

    @Override
    public String getApplicationUrl() {
        return this.applicationUrl;
    }

}
