package com.mattmalec.pterodactyl4j.client.ws.events.connection;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;

public class FailureEvent extends ConnectionEvent {

    private Throwable throwable;

    public FailureEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, boolean connected, Throwable throwable) {
        super(api, server, manager, connected);
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

}
