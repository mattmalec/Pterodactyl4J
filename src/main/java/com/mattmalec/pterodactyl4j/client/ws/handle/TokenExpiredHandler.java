package com.mattmalec.pterodactyl4j.client.ws.handle;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.client.ws.WebSocketClient;

import java.util.Optional;

public class TokenExpiredHandler extends ClientSocketHandler {

    private WebSocketClient webSocketClient;

    public TokenExpiredHandler(PteroClient client, ClientServer server, WebSocketClient webSocketClient) {
        super(client, server);
        this.webSocketClient = webSocketClient;
    }

    @Override
    public void handleInternally(String content) {
        webSocketClient.sendAuthenticate(Optional.empty());
    }
}
