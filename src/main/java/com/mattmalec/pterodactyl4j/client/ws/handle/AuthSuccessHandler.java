package com.mattmalec.pterodactyl4j.client.ws.handle;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.WebSocketClient;
import com.mattmalec.pterodactyl4j.client.ws.events.AuthSuccessEvent;

public class AuthSuccessHandler extends ClientSocketHandler {

    public AuthSuccessHandler(PteroClientImpl client, ClientServer server, WebSocketManager manager) {
        super(client, server, manager);
    }

    @Override
    public void handleInternally(String content) {
        getManager().getEventManager().handle(new AuthSuccessEvent(getClient(), getServer(), getManager()));
        WebSocketClient.WEBSOCKET_LOG.info(String.format("Authorized websocket for server %s", getServer().getIdentifier()));
    }
}
