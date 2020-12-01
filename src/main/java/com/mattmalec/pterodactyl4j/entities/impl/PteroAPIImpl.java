package com.mattmalec.pterodactyl4j.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.entities.PteroAPI;
import com.mattmalec.pterodactyl4j.requests.Requester;

public class PteroAPIImpl implements PteroAPI {

    private final String token;
    private final String applicationUrl;
    private final Requester requester;

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

    @Override
    public PteroApplication asApplication() {
        return new PteroApplicationImpl(requester);
    }

    @Override
    public PteroClient asClient() {
        return new PteroClientImpl(requester);
    }
}
