package com.mattmalec.pterodactyl4j.client.ws.events.error;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.Event;

public class DaemonErrorEvent extends Event {

    private final String message;

    public DaemonErrorEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, String message) {
        super(api, server, manager);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
