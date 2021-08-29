package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.Backup;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Database;
import com.mattmalec.pterodactyl4j.client.managers.BackupAction;
import com.mattmalec.pterodactyl4j.client.managers.DatabaseAction;
import com.mattmalec.pterodactyl4j.entities.P4J;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.util.function.Supplier;

public class CreateDatabaseImpl extends PteroActionImpl<Database> implements DatabaseAction {

    private String database;
    private String remote;


    public CreateDatabaseImpl(ClientServer server, PteroClientImpl impl) {
        super(impl.getP4J(), Route.Databases.CREATE_DATABASE.compile(server.getIdentifier()),
                (response, request) -> new DatabaseImpl(response.getObject(), server));
    }

    @Override
    protected RequestBody finalizeData() {
        Checks.notBlank(this.database, "database");
        JSONObject json = new JSONObject()
                .put("database", database)
                .put("remote", remote);
        return getRequestBody(json);
    }

    @Override
    public DatabaseAction setName(String name) {
        this.database = name;
        return this;
    }

    @Override
    public DatabaseAction setAllowedNetworks(String networks) {
        this.remote = networks;
        return this;
    }
}
