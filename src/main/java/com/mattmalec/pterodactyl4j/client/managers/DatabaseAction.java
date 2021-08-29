package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.Database;

import java.util.List;

public interface DatabaseAction extends PteroAction<Database> {

    DatabaseAction setName(String name);
    DatabaseAction setAllowedNetworks(String networks);

}
