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

package com.mattmalec.pterodactyl4j.client.ws.events.connection;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Indicates that P4J encountered a Throwable that could not be forwarded to another end-user frontend.
 * <br>This is fired for events in internal WebSocket handling.
 * This normally includes subtypes of {@link java.io.IOException IOExceptions}
 */
public class FailureEvent extends ConnectionEvent {

    private final Response response;
    private final Throwable throwable;

    public FailureEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, boolean connected, Response response, Throwable throwable) {
        super(api, server, manager, connected);
        this.response = response;
        this.throwable = throwable;
    }

    /**
     * The cause Throwable for this event.
     * <br>There are several types of Throwable instances that can be expected here.
     *
     * <p>For instance, if the Throwable is a type of {@link java.io.EOFException EOFException}, that means P4J unexpectedly
     * lost connection to Wings.
     *
     * <p>If the Throwable is a type of {@link java.net.ConnectException ConnectException}, that means P4J was not
     * able to reach Wings
     *
     * @return The cause
     */
    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * Read the response body of the event, deserialized as a {@link org.json.JSONObject JSONObject}
     * <br>There normally isn't a case when calling this method is necessary.
     *
     * @throws IOException
     *         If the provided response body cannot be deserialized
     *
     * @return The response body
     */
    public JSONObject getResponse() throws IOException {
        return new JSONObject(response.body().string());
    }

}
