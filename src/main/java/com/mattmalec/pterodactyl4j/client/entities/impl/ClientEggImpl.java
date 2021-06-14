package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.ClientEgg;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ClientEggImpl implements ClientEgg {

    private final JSONObject json;
    private final JSONObject variables;

    public ClientEggImpl(JSONObject json, JSONObject variables) {
        this.json = json.getJSONObject("attributes");
        this.variables = variables;
    }

    @Override
    public String getName() {
        return json.getString("name");
    }

    @Override
    public UUID getUUID() {
        return UUID.fromString(json.getString("uuid"));
    }

    @Override
    public List<EggVariable> getVariables() {
        List<EggVariable> variables = new ArrayList<>();
        for(Object o : this.variables.getJSONArray("data")) {
            JSONObject variable = new JSONObject(o.toString());
            variables.add(new ClientEggVariableImpl(variable));
        }
        return Collections.unmodifiableList(variables);
    }
}
