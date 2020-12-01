package com.mattmalec.pterodactyl4j.client.ws.events;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;

public abstract class Event {

    private final PteroClientImpl api;
    private final ClientServer server;
    private final WebSocketManager manager;

    public Event(PteroClientImpl api, ClientServer server, WebSocketManager manager) {
        this.api = api;
        this.server = server;
        this.manager = manager;
    }

    public PteroClient getClient() {
        return api;
    }

    public ClientServer getServer() {
        return server;
    }

    public WebSocketManager getWebSocketManager() {
        return manager;
    }

}
