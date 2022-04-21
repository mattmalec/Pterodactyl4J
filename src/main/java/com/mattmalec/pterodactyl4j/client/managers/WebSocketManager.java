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

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.ws.WebSocketAction;
import com.mattmalec.pterodactyl4j.client.ws.WebSocketClient;
import com.mattmalec.pterodactyl4j.client.ws.events.connection.DisconnectedEvent;
import com.mattmalec.pterodactyl4j.client.ws.hooks.IClientListenerManager;
import com.mattmalec.pterodactyl4j.utils.AwaitableClientListener;

public class WebSocketManager {

    private final PteroClientImpl api;
    private final ClientServer server;
    private final WebSocketClient client;
    private final IClientListenerManager eventManager;

    public WebSocketManager(PteroClientImpl api, ClientServer server, IClientListenerManager eventManager, boolean freshServer) {
        this.api = api;
        this.server = server;
        this.eventManager = eventManager;
        this.client = new WebSocketClient(api, server, freshServer, this);
        connect();
    }

    public IClientListenerManager getEventManager() {
        return eventManager;
    }

    private void connect() {
        Thread thread = new Thread(client, "P4J-ClientWS");
        thread.start();
    }

    public void reconnect() {
        if (client.isConnected()) {
            AwaitableClientListener listener =
                    AwaitableClientListener.create(DisconnectedEvent.class, api.getP4J().getSupplierPool());

            eventManager.register(listener);

            client.shutdown();

            listener.await(ignored -> {
                eventManager.unregister(listener);
                connect();
            });

        } else connect();
    }

    public void shutdown() {
        client.shutdown();
    }

    public void authenticate() {
        client.sendAuthenticate();
    }

    public void authenticate(String token) {
        client.sendAuthenticate(token);
    }

    public void request(RequestAction action) {
        client.send(WebSocketAction.create(action.data, null));
    }

    public void setPower(PowerAction power) {
        client.send(WebSocketAction.create(WebSocketAction.SET_STATE, power.name().toLowerCase()));
    }

    public void sendCommand(String command) {
        client.send(WebSocketAction.create(WebSocketAction.SEND_COMMAND, command));
    }

    public enum RequestAction {
        LOGS(WebSocketAction.SEND_LOGS),
        STATS(WebSocketAction.SEND_STATS);

        public final String data;

        RequestAction(String data) {
            this.data = data;
        }
    }

}
