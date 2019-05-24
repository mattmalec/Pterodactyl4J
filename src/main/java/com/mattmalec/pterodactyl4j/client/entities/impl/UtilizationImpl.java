package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.UtilizationState;
import com.mattmalec.pterodactyl4j.client.entities.Utilization;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UtilizationImpl implements Utilization {

    private JSONObject json;

    public UtilizationImpl(JSONObject json) {
        this.json = json;
    }

    @Override
    public UtilizationState getState() {
        switch(json.getString("state")) {
            case "on": return UtilizationState.ON;
            case "off": return UtilizationState.OFF;
            default: return UtilizationState.OFF;
        }
    }

    @Override
    public String getCurrentMemory() {
        return String.valueOf(json.getJSONObject("memory").getLong("current"));
    }

    @Override
    public String getMaxMemory() {
        return String.valueOf(json.getJSONObject("memory").getLong("limit"));
    }

    @Override
    public String getCurrentCPU() {
        return String.valueOf(json.getJSONObject("cpu").getLong("current"));
    }

    @Override
    public List<Double> getCurrentCores() {
        JSONArray array = json.getJSONObject("cpu").getJSONArray("cores");
        List<Double> cores = new ArrayList<>();
        array.forEach(o -> cores.add(Double.parseDouble(o.toString())));
        return Collections.unmodifiableList(cores);
    }

    @Override
    public String getMaxCPU() {
        return String.valueOf(json.getJSONObject("cpu").getLong("limit"));
    }

    @Override
    public String getCurrentDisk() {
        return String.valueOf(json.getJSONObject("disk").getLong("current"));
    }

    @Override
    public String getMaxDisk() {
        return String.valueOf(json.getJSONObject("disk").getLong("limit"));

    }
}
