package com.mattmalec.pterodactyl4j.client.ws.events.connection;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.Event;

public abstract class ConnectionEvent extends Event {

    private final boolean connected;

    public ConnectionEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, boolean connected) {
        super(api, server, manager);
        this.connected = connected;
    }

    public boolean isConnected() {
        return connected;
    }
}
