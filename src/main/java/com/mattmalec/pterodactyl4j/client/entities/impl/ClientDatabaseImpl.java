package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.ClientDatabase;
import org.json.JSONObject;

public class ClientDatabaseImpl implements ClientDatabase {

    private final JSONObject attributes;

    public ClientDatabaseImpl(JSONObject json) {
        attributes = json.getJSONObject("attributes");
    }

    @Override
    public String getId() {
        return attributes.getString("id");
    }

    @Override
    public String getName() {
        return attributes.getString("name");
    }

    @Override
    public String getUsername() {
        return attributes.getString("username");
    }

    @Override
    public String getPassword() {
        return attributes.getJSONObject("relationships").getJSONObject("password").getJSONObject("attributes").getString("password");
    }

    @Override
    public String getAllowedNetwork() {
        return attributes.getString("connections_from");
    }

}
