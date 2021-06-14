package com.mattmalec.pterodactyl4j.client.ws.handle;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.WebSocketClient;

public class TokenExpiringHandler extends ClientSocketHandler {

    private final WebSocketClient webSocketClient;

    public TokenExpiringHandler(PteroClientImpl client, ClientServer server, WebSocketManager manager, WebSocketClient webSocketClient) {
        super(client, server, manager);
        this.webSocketClient = webSocketClient;
    }

    @Override
    public void handleInternally(String content) {
        webSocketClient.sendAuthenticate();
    }
}
