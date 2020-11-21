package com.mattmalec.pterodactyl4j.client.ws.handle;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.output.InstallOutputEvent;

public class InstallOuputHandler extends ClientSocketHandler {

    public InstallOuputHandler(PteroClientImpl client, ClientServer server, WebSocketManager manager) {
        super(client, server, manager);
    }

    @Override
    public void handleInternally(String content) {
        getManager().getEventManager().handle(new InstallOutputEvent(getClient(), getServer(), getManager(), content));
    }
}
