package com.mattmalec.pterodactyl4j.client.ws.handle;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;

public abstract class ClientSocketHandler {

    private PteroClient client;
    private ClientServer server;

    public ClientSocketHandler(PteroClient client, ClientServer server) {
        this.client = client;
        this.server = server;
    }

    protected PteroClient getClient() {
        return client;
    }

    protected ClientServer getServer() {
        return server;
    }


    public abstract void handleInternally(String content);

}
