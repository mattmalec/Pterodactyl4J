package com.mattmalec.pterodactyl4j.client.ws.events.install;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;

// this event is only called if skip egg scripts is enabled
public class InstallStartedEvent extends InstallEvent {

    public InstallStartedEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, boolean installed) {
        super(api, server, manager, installed);
    }
}
