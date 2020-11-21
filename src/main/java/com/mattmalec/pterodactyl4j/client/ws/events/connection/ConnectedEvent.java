package com.mattmalec.pterodactyl4j.client.ws.events.connection;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;

public class ConnectedEvent extends ConnectionEvent {

    public ConnectedEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, boolean connected) {
        super(api, server, manager, connected);
    }

}
