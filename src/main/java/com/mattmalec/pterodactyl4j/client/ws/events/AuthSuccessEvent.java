package com.mattmalec.pterodactyl4j.client.ws.events;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;

public class AuthSuccessEvent extends Event {

    public AuthSuccessEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager) {
        super(api, server, manager);
    }
}
