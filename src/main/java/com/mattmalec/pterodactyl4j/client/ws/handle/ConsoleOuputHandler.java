package com.mattmalec.pterodactyl4j.client.ws.handle;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;

public class ConsoleOuputHandler extends ClientSocketHandler {

    public ConsoleOuputHandler(PteroClient client, ClientServer server) {
        super(client, server);
    }

    @Override
    public void handleInternally(String content) {

    }
}
