package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

public class ClientServerManager {

    private final ClientServer server;
    private final PteroClientImpl impl;

    public ClientServerManager(ClientServer server, PteroClientImpl impl) {
        this.server = server;
        this.impl = impl;
    }

    public PteroAction<Void> setName(String name) {
        JSONObject obj = new JSONObject().put("name", name);
        return PteroActionImpl.onRequestExecute(impl.getP4J(),
                Route.Client.RENAME_SERVER.compile(server.getIdentifier()), PteroActionImpl.getRequestBody(obj));
    }

    public PteroAction<Void> reinstall() {
        return PteroActionImpl.onRequestExecute(impl.getP4J(),
                Route.Client.REINSTALL_SERVER.compile(server.getIdentifier()));
    }
}
