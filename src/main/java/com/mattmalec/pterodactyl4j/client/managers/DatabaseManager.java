package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientDatabase;

public interface DatabaseManager {

    ClientDatabaseAction createDatabase();
    PteroAction<Void> deleteDatabase(ClientDatabase clientDatabase);
    PteroAction<ClientDatabase> rotatePassword(ClientDatabase clientDatabase);

}

