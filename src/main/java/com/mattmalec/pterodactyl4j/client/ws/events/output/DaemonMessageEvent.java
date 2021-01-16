package com.mattmalec.pterodactyl4j.client.ws.events.output;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;

public class DaemonMessageEvent extends OutputEvent {

    public DaemonMessageEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, String line) {
        super(api, server, manager, line);
    }
}
