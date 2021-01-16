package com.mattmalec.pterodactyl4j.client.ws.events.install;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;

public class InstallCompletedEvent extends InstallEvent {

    public InstallCompletedEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, boolean installed) {
        super(api, server, manager, installed);
    }
}
