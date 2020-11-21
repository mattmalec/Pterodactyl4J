package com.mattmalec.pterodactyl4j.client.ws.handle;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;

public abstract class ClientSocketHandler {

    private PteroClientImpl client;
    private ClientServer server;
    private WebSocketManager manager;

    public ClientSocketHandler(PteroClientImpl client, ClientServer server, WebSocketManager manager) {
        this.client = client;
        this.server = server;
        this.manager = manager;
    }

    protected PteroClientImpl getClient() {
        return client;
    }

    protected ClientServer getServer() {
        return server;
    }

    protected WebSocketManager getManager() {
        return manager;
    }

    public abstract void handleInternally(String content);

}
