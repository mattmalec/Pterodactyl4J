package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.application.entities.Container;
import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

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
        return json.getInt("installed") != 0;
    }

    @Override
    public Map<String, EnvironmentValue<?>> getEnvironment() {
        JSONObject environment = json.getJSONObject("environment");
        return Collections.unmodifiableMap(environment.keySet().stream()
                .map(s -> new AbstractMap.SimpleImmutableEntry<String, EnvironmentValue<?>>(s, EnvironmentValue.of(environment.get(s))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }
}
