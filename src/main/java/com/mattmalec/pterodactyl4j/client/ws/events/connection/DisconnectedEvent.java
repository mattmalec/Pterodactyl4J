package com.mattmalec.pterodactyl4j.client.ws.events.connection;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;

public class DisconnectedEvent extends ConnectionEvent {

    private int closeCode;

    public DisconnectedEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, boolean connected, int closeCode) {
        super(api, server, manager, connected);
        this.closeCode = closeCode;
    }

    public int getCloseCode() {
        return closeCode;
    }
}
