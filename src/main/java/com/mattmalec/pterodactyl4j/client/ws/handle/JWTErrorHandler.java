package com.mattmalec.pterodactyl4j.client.ws.handle;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.WebSocketClient;
import com.mattmalec.pterodactyl4j.client.ws.events.error.JWTErrorEvent;

import java.util.Optional;

public class JWTErrorHandler extends ClientSocketHandler {

    private WebSocketClient webSocket;

    public JWTErrorHandler(PteroClientImpl client, ClientServer server, WebSocketManager manager, WebSocketClient webSocket) {
        super(client, server, manager);
        this.webSocket = webSocket;
    }

    @Override
    public void handleInternally(String content) {
        if (content.equals("jwt: exp claim is invalid"))
            webSocket.sendAuthenticate(Optional.empty());
        getManager().getEventManager().handle(new JWTErrorEvent(getClient(), getServer(), getManager(), content));
    }
}
