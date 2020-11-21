package com.mattmalec.pterodactyl4j.client.ws.events.output;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.Event;

public abstract class OutputEvent extends Event {

    private String line;

    public OutputEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, String line) {
        super(api, server, manager);
        this.line = line;
    }

    public String getLine() {
        return line;
    }
}
