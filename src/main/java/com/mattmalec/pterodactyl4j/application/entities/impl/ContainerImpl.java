package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.application.entities.Container;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ContainerImpl implements Container {

    private JSONObject json;

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
        return json.getInt("installed") != 0;
    }

    @Override
    public Map<String, EnvironmentValue<?>> getEnvironment() {
        JSONObject environment = json.getJSONObject("environment");
        Map<String, EnvironmentValue<?>> environmentMap = new HashMap<>();
        environment.keys().forEachRemaining(s -> environmentMap.putIfAbsent(s, EnvironmentValue.of(environment.get(s))));
        return Collections.unmodifiableMap(environmentMap);
    }
}
