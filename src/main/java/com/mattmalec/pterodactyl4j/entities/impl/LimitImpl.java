package com.mattmalec.pterodactyl4j.entities.impl;

import com.mattmalec.pterodactyl4j.entities.Limit;
import org.json.JSONObject;

public class LimitImpl implements Limit {

    private final JSONObject json;

    public LimitImpl(JSONObject json) {
        this.json = json;
    }

    @Override
    public long getMemoryLong() {
        return json.getLong("memory");
    }

    @Override
    public long getSwapLong() {
        return json.getLong("swap");
    }

    @Override
    public long getDiskLong() {
        return json.getLong("disk");
    }

    @Override
    public long getIOLong() {
        return json.getLong("io");
    }

    @Override
    public long getCPULong() {
        return json.getLong("cpu");
    }

    @Override
    public String getThreads() {
        return json.optString("threads");
    }

}
