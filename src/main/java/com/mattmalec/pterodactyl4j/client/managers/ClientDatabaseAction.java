package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientDatabase;

public interface ClientDatabaseAction extends PteroAction<ClientDatabase>, DatabaseAction {

    ClientDatabaseAction setName(String name);
    ClientDatabaseAction setAllowedNetwork(String network);

}
