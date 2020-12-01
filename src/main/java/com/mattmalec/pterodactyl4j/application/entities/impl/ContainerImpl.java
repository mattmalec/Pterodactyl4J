package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.Container;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ContainerImpl implements Container {

    private final JSONObject json;

    public ContainerImpl(JSONObject json) {
        this.json = json;
    }

    @Override
    public String getStartupCommand() {
        return json.getString("startup_command");
    }

    @Override
    public String getImage() {
        return json.getString("image");
    }

    @Override
    public boolean isInstalled() {
        return json.getBoolean("installed");
    }

    @Override
    public Map<String, String> getEnvironment() {
        JSONObject environment = json.getJSONObject("environment");
        HashMap<String, String> environmentMap = new HashMap<>();
        environment.keys().forEachRemaining(s -> environmentMap.putIfAbsent(s, environment.getString(s)));
        return Collections.unmodifiableMap(environmentMap);
    }
}
