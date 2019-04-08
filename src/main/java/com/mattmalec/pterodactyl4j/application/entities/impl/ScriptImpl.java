package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.Script;
import org.json.JSONObject;

public class ScriptImpl implements Script {

    private JSONObject json;

    public ScriptImpl(JSONObject json) {
        this.json = json;
    }

    @Override
    public boolean isPrivileged() {
        return json.getBoolean("privileged");
    }

    @Override
    public String getInstall() {
        return json.getString("install");
    }

    @Override
    public String getEntry() {
        return json.getString("entry");
    }

    @Override
    public String getContainer() {
        return json.getString("container");
    }

    @Override
    public String getExtends() {
        return json.getString("extends");
    }
}
