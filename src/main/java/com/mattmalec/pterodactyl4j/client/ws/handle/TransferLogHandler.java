package com.mattmalec.pterodactyl4j.client.ws.handle;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.transfer.TransferLogEvent;

public class TransferLogHandler extends ClientSocketHandler {

    public TransferLogHandler(PteroClientImpl client, ClientServer server, WebSocketManager manager) {
        super(client, server, manager);
    }

    @Override
    public void handleInternally(String content) {
        getManager().getEventManager().handle(new TransferLogEvent(getClient(), getServer(), getManager(), content));
    }
}
