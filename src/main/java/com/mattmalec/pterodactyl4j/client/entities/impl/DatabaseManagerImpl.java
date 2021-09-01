package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.ClientDatabase;
import com.mattmalec.pterodactyl4j.client.managers.ClientDatabaseAction;
import com.mattmalec.pterodactyl4j.client.managers.DatabaseManager;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;

public class DatabaseManagerImpl implements DatabaseManager {

    private final ClientServer server;
    private final PteroClientImpl impl;

    public DatabaseManagerImpl(ClientServer server, PteroClientImpl impl) {
        this.server = server;
        this.impl = impl;
    }

    @Override
    public ClientDatabaseAction createDatabase() {
        return new CreateClientDatabaseImpl(server, impl);
    }

    @Override
    public PteroAction<Void> deleteDatabase(ClientDatabase clientDatabase) {
        return PteroActionImpl.onRequestExecute(impl.getP4J(),
                Route.Databases.DELETE_DATABASE.compile(server.getIdentifier(), clientDatabase.getId()));
    }

    @Override
    public PteroAction<ClientDatabase> rotatePassword(ClientDatabase clientDatabase) {
         return PteroActionImpl.onRequestExecute(impl.getP4J(),
                 Route.Databases.ROTATE_PASSWORD.compile(server.getIdentifier(), clientDatabase.getId()), (response, request) -> new ClientDatabaseImpl(response.getObject()));
    }
}
