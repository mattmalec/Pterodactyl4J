package com.mattmalec.pterodactyl4j.entities.impl;

import com.mattmalec.pterodactyl4j.entities.Limit;
import org.json.JSONObject;

public class LimitImpl implements Limit {

    private JSONObject json;

    public LimitImpl(JSONObject json) {
        this.json = json;
    }

    @Override
    public String getMemory() {
        return Long.toUnsignedString(json.getLong("memory"));
    }

    @Override
    public String getSwap() {
        return Long.toUnsignedString(json.getLong("swap"));
    }

    @Override
    public String getDisk() {
        return Long.toUnsignedString(json.getLong("disk"));
    }

    @Override
    public String getIO() {
        return Long.toUnsignedString(json.getLong("io"));
    }

    @Override
    public String getCPU() {
        return Long.toUnsignedString(json.getLong("cpu"));
    }
}
