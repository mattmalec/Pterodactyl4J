package com.mattmalec.pterodactyl4j.client.ws.events.token;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.Event;

public abstract class TokenEvent extends Event {

    private final boolean expired;

    public TokenEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, boolean expired) {
        super(api, server, manager);
        this.expired = expired;
    }

    public boolean isExpired() {
        return expired;
    }
}
