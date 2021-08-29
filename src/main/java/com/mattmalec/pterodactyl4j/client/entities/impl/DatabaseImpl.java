package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Database;
import org.json.JSONObject;

public class DatabaseImpl implements Database {

    private final JSONObject attributes;


    private final JSONObject json;

    private final ClientServer server;


    public DatabaseImpl(JSONObject json, ClientServer server) {
        this.json = json;
        this.attributes = json.getJSONObject("attributes");
        this.server = server;
    }


    @Override
    public String getID() {
        return this.attributes.getString("id");
    }

    @Override
    public String getName() {
        return this.attributes.getString("name");

    }

    @Override
    public String getUsername() {
        return this.attributes.getString("username");

    }

    @Override
    public String getPassword() {
        return this.attributes.getJSONObject("relationships").getJSONObject("password").getJSONObject("attributes").getString("password");

    }



    @Override
    public String allowedNetworks() {
        return this.attributes.getString("connections_from");

    }


}
