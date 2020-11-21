package com.mattmalec.pterodactyl4j.client.ws.handle;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.StatsUpdateEvent;
import org.json.JSONObject;

public class StatsHandler extends ClientSocketHandler {

    public StatsHandler(PteroClientImpl client, ClientServer server, WebSocketManager manager) {
        super(client, server, manager);
    }

    @Override
    public void handleInternally(String content) {
        JSONObject json = new JSONObject(content);
        getManager().getEventManager().handle(new StatsUpdateEvent(getClient(), getServer(), getManager(), json));
    }
}
