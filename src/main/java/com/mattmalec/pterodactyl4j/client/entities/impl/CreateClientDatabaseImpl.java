package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.ClientDatabase;
import com.mattmalec.pterodactyl4j.client.managers.ClientDatabaseAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class CreateClientDatabaseImpl extends PteroActionImpl<ClientDatabase> implements ClientDatabaseAction {

    private String database;
    private String remote;

    public CreateClientDatabaseImpl(ClientServer server, PteroClientImpl impl) {
        super(impl.getP4J(), Route.Databases.CREATE_DATABASE.compile(server.getIdentifier()),
                (response, request) -> new ClientDatabaseImpl(response.getObject()));
    }

    @Override
    protected RequestBody finalizeData() {
        Checks.notBlank(database, "database name");
        Checks.notBlank(remote, "allowed network");
        JSONObject json = new JSONObject()
                .put("database", database)
                .put("remote", remote);
        return getRequestBody(json);
    }

    @Override
    public ClientDatabaseAction setName(String name) {
        database = name;
        return this;
    }

    @Override
    public ClientDatabaseAction setAllowedNetwork(String networks) {
        remote = networks;
        return this;
    }
}
