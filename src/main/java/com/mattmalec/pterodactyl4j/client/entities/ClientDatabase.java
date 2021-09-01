package com.mattmalec.pterodactyl4j.client.entities;

public interface ClientDatabase extends Database {

    String getId();
    String getName();
    String getUsername();
    String getPassword();
    String getAllowedNetwork();

}
