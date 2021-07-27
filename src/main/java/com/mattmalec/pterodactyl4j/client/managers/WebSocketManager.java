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
import com.mattmalec.pterodactyl4j.client.ws.hooks.IClientListenerManager;

public class WebSocketManager {

    private final WebSocketClient client;
    private final IClientListenerManager eventManager;
    private final Thread thread;

    public WebSocketManager(PteroClientImpl api, ClientServer server, IClientListenerManager eventManager, boolean useFreshServer) {
        this.eventManager = eventManager;
        this.client = new WebSocketClient(api, server, useFreshServer, this);
        this.thread = new Thread(client, "P4J-ClientWS");
        connect();
    }

    public IClientListenerManager getEventManager() {
        return eventManager;
    }

    private void connect() {
        thread.start();
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
