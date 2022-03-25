/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.entities.P4J;
import com.mattmalec.pterodactyl4j.requests.Requester;
import okhttp3.OkHttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class P4JImpl implements P4J {

    private final String token;
    private final String applicationUrl;
    private final Requester requester;
    private final OkHttpClient httpClient;
    private final ExecutorService callbackPool;
    private final ExecutorService actionPool;
    private final ScheduledExecutorService rateLimitPool;
    private final ExecutorService supplierPool;
    private final OkHttpClient webSocketClient;
    private final String userAgent;

    public P4JImpl(String applicationUrl, String token, OkHttpClient httpClient, ExecutorService callbackPool, ExecutorService actionPool,
                   ScheduledExecutorService rateLimitPool, ExecutorService supplierPool, OkHttpClient webSocketClient, String userAgent) {
        this.token = token;
        this.applicationUrl = applicationUrl;
        this.httpClient = httpClient;
        this.callbackPool = callbackPool;
        this.actionPool = actionPool;
        this.rateLimitPool = rateLimitPool;
        this.supplierPool = supplierPool;
        this.webSocketClient = webSocketClient;
        this.userAgent = userAgent;
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
    public String getUserAgent() {
        return userAgent;
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
