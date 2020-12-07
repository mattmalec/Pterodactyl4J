package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

public class ClientServerManager {

    private ClientServer server;
    private PteroClientImpl impl;

    public ClientServerManager(ClientServer server, PteroClientImpl impl) {
        this.server = server;
        this.impl = impl;
    }

    public PteroAction<Void> setName(String name) {
        return PteroActionImpl.onExecute(() ->
        {
            JSONObject obj = new JSONObject()
                    .put("name", name);
            Route.CompiledRoute route = Route.Client.RENAME_SERVER.compile(server.getIdentifier()).withJSONdata(obj);
            impl.getRequester().request(route).toJSONObject();
            return null;
        });
    }

    public PteroAction<Void> reinstall() {
        return PteroActionImpl.onExecute(() ->
        {
            Route.CompiledRoute route = Route.Client.REINSTALL_SERVER.compile(server.getIdentifier());
            impl.getRequester().request(route).toJSONObject();
            return null;
        });
    }

}
