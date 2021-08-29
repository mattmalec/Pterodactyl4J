package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Database;
import com.mattmalec.pterodactyl4j.client.managers.DatabaseAction;
import com.mattmalec.pterodactyl4j.client.managers.DatabaseManager;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DatabaseManagerImpl implements DatabaseManager {

    private final ClientServer server;
    private final PteroClientImpl impl;

    public DatabaseManagerImpl(ClientServer server, PteroClientImpl impl) {
        this.server = server;
        this.impl = impl;
    }

    @Override
    public DatabaseAction createDatabase() {
        return new CreateDatabaseImpl(server, impl);
    }

    @Override
    public PteroAction<Database> getDatabase(String databaseId) {
        return PteroActionImpl.onRequestExecute(impl.getP4J(),
                Route.Databases.LIST_DATABASES.compile(server.getIdentifier()), (response, request) -> {
                    JSONObject json = response.getObject();
                    JSONObject parsedJson = new JSONObject();
                    JSONArray dataArray = json.getJSONArray("data");

                    for (int i=0; i < dataArray.length();) {
                        String jsonDbId = json.getJSONArray("data").getJSONObject(i).getJSONObject("attributes").getString("id");
                        if (Objects.equals(jsonDbId, databaseId)){
                            parsedJson = json.getJSONArray("data").getJSONObject(i);
                            break;
                        }
                        i++;
                    }
                    return new DatabaseImpl(parsedJson, this.server);
                });
    }

    @Override
    public PteroAction<List<Database>> getDatabases() {
        return PteroActionImpl.onRequestExecute(impl.getP4J(),
                Route.Databases.LIST_DATABASES.compile(server.getIdentifier()), (response, request) -> {
                    JSONObject json = response.getObject();
                    List<Database> databases = new ArrayList<>();
                    for (Object o : json.getJSONArray("data")) {
                        JSONObject database = new JSONObject(o.toString());
                        databases.add(new DatabaseImpl(database, this.server));
                    }
                    return Collections.unmodifiableList(databases);
                });
    }

    @Override
    public PteroAction<Void> deleteDatabase(Database database) {
        return PteroActionImpl.onRequestExecute(impl.getP4J(),
                Route.Databases.DELETE_DATABASE.compile(server.getIdentifier(), database.getID()));
    }

    @Override
    public PteroAction<Database> rotateDatabasePassword(Database database) {

         return PteroActionImpl.onRequestExecute(impl.getP4J(),
                 Route.Databases.ROTATE_PASSWORD.compile(server.getIdentifier(), database.getID()), (response, request) -> new DatabaseImpl(response.getObject(), this.server));

    }
}
