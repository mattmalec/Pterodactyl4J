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

package com.mattmalec.pterodactyl4j.utils.config;

import com.mattmalec.pterodactyl4j.P4JInfo;
import okhttp3.OkHttpClient;

public final class SessionConfig {

    public static final String DEFAULT_USER_AGENT = "Pterodactyl4J (" + P4JInfo.VERSION + ")";

    private final OkHttpClient httpClient;
    private final OkHttpClient webSocketClient;
    private String userAgent;

    public SessionConfig(OkHttpClient httpClient, OkHttpClient webSocketClient) {
        if (httpClient == null)
            httpClient = new OkHttpClient();

        if (webSocketClient == null)
            webSocketClient = new OkHttpClient();

        this.httpClient = httpClient;
        this.webSocketClient = webSocketClient;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public OkHttpClient getWebSocketClient() {
        return webSocketClient;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        if (userAgent == null)
            userAgent = DEFAULT_USER_AGENT;
        this.userAgent = userAgent;
    }

}
