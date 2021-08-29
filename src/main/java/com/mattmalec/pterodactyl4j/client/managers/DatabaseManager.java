package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.Database;

import java.util.List;

public interface DatabaseManager {

    DatabaseAction createDatabase();
    PteroAction<Database> getDatabase(String databaseId);
    PteroAction<List<Database>> getDatabases();
    PteroAction<Void> deleteDatabase(Database database);
    PteroAction<Database> rotateDatabasePassword(Database database);

}

