package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.Account;
import com.mattmalec.pterodactyl4j.client.managers.AccountManager;
import org.json.JSONObject;

public class AccountImpl implements Account {

    private JSONObject json;
    private PteroClientImpl impl;

    public AccountImpl(JSONObject json, PteroClientImpl impl) {
        this.json = json.getJSONObject("attributes");
        this.impl = impl;
    }

    @Override
    public AccountManager getManager() {
        return new AccountManager(impl);
    }

    @Override
    public String getUserName() {
        return json.getString("username");
    }

    @Override
    public String getEmail() {
        return json.getString("email");
    }

    @Override
    public String getFirstName() {
        return json.getString("first_name");
    }

    @Override
    public String getLastName() {
        return json.getString("last_name");
    }

    @Override
    public String getLanguage() {
        return json.getString("language");
    }

    @Override
    public boolean isRootAdmin() {
        return json.getBoolean("admin");
    }

    @Override
    public String toString() {
        return json.toString(4);
    }
}
