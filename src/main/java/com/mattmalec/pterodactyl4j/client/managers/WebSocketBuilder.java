package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.ws.hooks.IClientListenerManager;
import com.mattmalec.pterodactyl4j.client.ws.hooks.InterfacedClientListenerManager;

public class WebSocketBuilder {

    private IClientListenerManager eventManager = new InterfacedClientListenerManager();

    private PteroClientImpl api;
    private ClientServer server;

    public WebSocketBuilder(PteroClientImpl api, ClientServer server) {
        this.api = api;
        this.server = server;
    }


    public WebSocketBuilder addEventListeners(Object...  listeners) {
        if (listeners == null)
            throw new IllegalArgumentException("Listeners cannot be null");

        for (Object listener : listeners) {
            eventManager.register(listener);
        }
        return this;
    }

    public WebSocketBuilder removeEventListeners(Object...  listeners) {
        if (listeners == null)
            throw new IllegalArgumentException("Listeners cannot be null");

        for (Object listener : listeners) {
            eventManager.unregister(listener);
        }
        return this;
    }

    public WebSocketManager build() {
        return new WebSocketManager(api, server, eventManager);
    }



}
