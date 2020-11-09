package com.mattmalec.pterodactyl4j.entities.impl;

import com.mattmalec.pterodactyl4j.entities.FeatureLimit;
import org.json.JSONObject;

public class FeatureLimitImpl implements FeatureLimit {

    private JSONObject json;

    public FeatureLimitImpl(JSONObject json) {
        this.json = json;
    }

    @Override
    public long getDatabasesLong() {
        return json.getLong("databases");
    }

    @Override
    public long getAllocationsLong() {
        return json.getLong("allocations");
    }

    @Override
    public long getBackupsLong() {
        return json.getLong("backups");
    }
}
