package com.mattmalec.pterodactyl4j.client.ws.events.install;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.Event;

public abstract class InstallEvent extends Event {

    private final boolean installed;

    public InstallEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, boolean installed) {
        super(api, server, manager);
        this.installed = installed;
    }

    public boolean isInstalled() {
        return installed;
    }

}
