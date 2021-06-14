package com.mattmalec.pterodactyl4j.client.ws.events;

import com.mattmalec.pterodactyl4j.UtilizationState;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;

public class StatusUpdateEvent extends Event {

    private final UtilizationState state;

    public StatusUpdateEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, UtilizationState state) {
        super(api, server, manager);
        this.state = state;
    }

    public UtilizationState getState() {
        return state;
    }
}
