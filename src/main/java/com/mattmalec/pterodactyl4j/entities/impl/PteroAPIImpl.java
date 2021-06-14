package com.mattmalec.pterodactyl4j.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.entities.PteroAPI;
import com.mattmalec.pterodactyl4j.requests.Requester;
import okhttp3.OkHttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class PteroAPIImpl implements PteroAPI {

    private final String token;
    private final String applicationUrl;
    private final Requester requester;
    private final OkHttpClient httpClient;
    private final ExecutorService callbackPool;
    private final ExecutorService actionPool;
    private final ScheduledExecutorService rateLimitPool;
    private final ExecutorService supplierPool;
    private final OkHttpClient webSocketClient;

    public PteroAPIImpl(String applicationUrl, String token, OkHttpClient httpClient, ExecutorService callbackPool, ExecutorService actionPool,
                        ScheduledExecutorService rateLimitPool, ExecutorService supplierPool, OkHttpClient webSocketClient) {
        this.token = token;
        this.applicationUrl = applicationUrl;
        this.httpClient = httpClient;
        this.callbackPool = callbackPool;
        this.actionPool = actionPool;
        this.rateLimitPool = rateLimitPool;
        this.supplierPool = supplierPool;
        this.webSocketClient = webSocketClient;
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
    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    public ExecutorService getCallbackPool() {
        return callbackPool;
    }

    @Override
    public ExecutorService getActionPool() {
        return actionPool;
    }

    @Override
    public ScheduledExecutorService getRateLimitPool() {
        return rateLimitPool;
    }

    @Override
    public ExecutorService getSupplierPool() {
        return supplierPool;
    }

    @Override
    public OkHttpClient getWebSocketClient() {
        return webSocketClient;
    }

    @Override
    public PteroApplication asApplication() {
        return new PteroApplicationImpl(this);
    }

    @Override
    public PteroClient asClient() {
        return new PteroClientImpl(this);
    }
}
