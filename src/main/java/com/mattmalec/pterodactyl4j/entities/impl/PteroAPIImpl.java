package com.mattmalec.pterodactyl4j.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.entities.PteroAPI;
import com.mattmalec.pterodactyl4j.requests.Requester;

public class PteroAPIImpl implements PteroAPI {

    private String token;
    private String applicationUrl;
    private Requester requester;
    private PteroApplicationImpl applicationImpl;
    private PteroClientImpl clientImpl;

    public PteroAPIImpl(String applicationUrl, String token) {
        this.token = token;
        this.applicationUrl = applicationUrl;
        this.requester = new Requester(this);
        this.applicationImpl = new PteroApplicationImpl(requester);
        this.clientImpl = new PteroClientImpl(requester);
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
        return applicationImpl;
    }

    @Override
    public PteroClient asClient() {
        return clientImpl;
    }
}
