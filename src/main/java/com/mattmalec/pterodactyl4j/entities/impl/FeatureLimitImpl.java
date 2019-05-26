package com.mattmalec.pterodactyl4j.entities.impl;

import com.mattmalec.pterodactyl4j.entities.FeatureLimit;
import org.json.JSONObject;

public class FeatureLimitImpl implements FeatureLimit {

    private JSONObject json;

    public FeatureLimitImpl(JSONObject json) {
        this.json = json;
    }

    @Override
    public String getDatabases() {
        return Long.toUnsignedString(json.getLong("databases"));
    }

    @Override
    public String getAllocations() {
        return Long.toUnsignedString(json.getLong("allocations"));
    }
}
