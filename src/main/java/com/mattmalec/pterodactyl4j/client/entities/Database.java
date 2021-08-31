package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.managers.DatabaseAction;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface ClientDatabase extends Database {

    String getID();
    String getName();
    String getUsername();
    String getPassword();
    String allowedNetworks();

}
