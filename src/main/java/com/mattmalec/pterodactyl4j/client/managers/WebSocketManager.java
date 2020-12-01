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

    public WebSocketManager(PteroClientImpl api, ClientServer server, IClientListenerManager eventManager) {
        this.eventManager = eventManager;
        this.client = new WebSocketClient(api, server, this);
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
        client.send(WebSocketAction.create(WebSocketAction.SET_STATE, power.name()));
    }

    public void sendCommand(String command) {
        client.send(WebSocketAction.create(WebSocketAction.SEND_COMMAND, command));
    }

    public enum RequestAction {
        LOGS(WebSocketAction.SEND_LOGS),
        STATS(WebSocketAction.SEND_STATS);

        public String data;

        RequestAction(String data) {
            this.data = data;
        }
    }

}
