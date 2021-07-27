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

package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.ws.events.Event;
import com.mattmalec.pterodactyl4j.client.ws.hooks.IClientListenerManager;
import com.mattmalec.pterodactyl4j.client.ws.hooks.InterfacedClientListenerManager;

public class WebSocketBuilder {

    private final IClientListenerManager eventManager;
    private final PteroClientImpl api;
    private final ClientServer server;

    private boolean useFreshServer;

    public WebSocketBuilder(PteroClientImpl api, ClientServer server) {
        this.api = api;
        this.server = server;
        this.eventManager = new InterfacedClientListenerManager();
    }


    /**
     * Registers the specified listeners
     *
     * @param listeners
     *        The listener objects
     *
     * @throws java.lang.IllegalArgumentException
     *         If one of the listeners does not implement {@link com.mattmalec.pterodactyl4j.client.ws.hooks.ClientSocketListener ClientSocketListener}
     *         or if the listeners are null
     */
    public WebSocketBuilder addEventListeners(Object...  listeners) {
        if (listeners == null)
            throw new IllegalArgumentException("Listeners cannot be null");

        for (Object listener : listeners) {
            eventManager.register(listener);
        }
        return this;
    }

    /**
     * Removes the specified listeners
     *
     * @param listeners
     *        The listener objects to remove
     *
     * @throws java.lang.IllegalArgumentException
     *         If the listeners are null
     */
    public WebSocketBuilder removeEventListeners(Object...  listeners) {
        if (listeners == null)
            throw new IllegalArgumentException("Listeners cannot be null");

        for (Object listener : listeners) {
            eventManager.unregister(listener);
        }
        return this;
    }

    /**
     * Enables/Disables P4J to return the latest server instance when firing events.
     *
     * <p>By default, the {@link com.mattmalec.pterodactyl4j.client.entities.ClientServer ClientServer}
     * provided in the event context will be the <b>same instance</b> when the WebSocket was built. Generally, this behavior is preferred
     * since you can use {@link Event#retrieveServer()} when you need an updated instance.
     *
     * <p>However, if your program requires the latest data from the {@link com.mattmalec.pterodactyl4j.client.entities.ClientServer ClientServer},
     * you can use this method to have P4J retrieve the server, then fire the event using the new data.
     *
     * <p><b>Note:</b> If this is enabled, and the panel rate limit is hit, events will stop firing until the limit is lifted
     *
     * <p>Default: <b>false (disabled)</b>
     *
     * @param  enable
     *         True - provide a fresh instance of the {@link com.mattmalec.pterodactyl4j.client.entities.ClientServer ClientServer}
     *         in event context
     *
     * @return The {@link com.mattmalec.pterodactyl4j.client.managers.WebSocketBuilder WebSocketBuilder} instance. Useful for chaining.
     */
    public WebSocketBuilder setEnableFreshServer(boolean enable) {
        this.useFreshServer = enable;
        return this;
    }

    /**
     * Builds a new {@link com.mattmalec.pterodactyl4j.client.managers.WebSocketManager WebSocketManager}
     * instance and retrieves a token from the panel to start the authorization process.
     * <p>The authorization process runs in a different thread, so while this will return immediately, the manager has not
     * finished connecting.
     *
     * @return A {@link com.mattmalec.pterodactyl4j.client.managers.WebSocketManager WebSocketManager} that is ready for use
     */
    public WebSocketManager build() {
        return new WebSocketManager(api, server, eventManager, useFreshServer);
    }



}
