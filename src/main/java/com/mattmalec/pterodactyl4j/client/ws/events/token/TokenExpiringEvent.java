package com.mattmalec.pterodactyl4j.client.ws.events.token;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;

public class TokenExpiringEvent extends TokenEvent {

    public TokenExpiringEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, boolean expired) {
        super(api, server, manager, expired);
    }

    @Override
    public boolean isExpired() {
        return super.isExpired();
    }
}
