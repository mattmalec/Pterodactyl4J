package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.client.entities.ClientEgg;
import org.json.JSONObject;

public class ClientEggVariableImpl implements ClientEgg.EggVariable {

    private JSONObject json;

    public ClientEggVariableImpl(JSONObject json) {
        this.json = json.getJSONObject("attributes");
    }

    @Override
    public String getName() {
        return json.getString("name");
    }

    @Override
    public String getDescription() {
        return json.getString("description");
    }

    @Override
    public String getEnvironmentVariable() {
        return json.getString("env_variable");
    }

    @Override
    public EnvironmentValue<?> getDefaultValue() {
        return EnvironmentValue.of(json.get("default_value"));
    }

    @Override
    public String getServerValue() {
        return json.getString("server_value");
    }

    @Override
    public boolean isEditable() {
        return json.getBoolean("is_editable");
    }

    @Override
    public String getRules() {
        return json.getString("rules");
    }
}
