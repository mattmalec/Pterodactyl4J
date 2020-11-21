package com.mattmalec.pterodactyl4j.client.ws.handle;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.WebSocketClient;

import java.util.Optional;

public class TokenExpiredHandler extends ClientSocketHandler {

    private WebSocketClient webSocketClient;

    public TokenExpiredHandler(PteroClientImpl client, ClientServer server, WebSocketManager manager, WebSocketClient webSocketClient) {
        super(client, server, manager);
        this.webSocketClient = webSocketClient;
    }

    @Override
    public void handleInternally(String content) {
        webSocketClient.sendAuthenticate(Optional.empty());
    }
}
