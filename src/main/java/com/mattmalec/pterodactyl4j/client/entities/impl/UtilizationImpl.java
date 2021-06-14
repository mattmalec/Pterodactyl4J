package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.UtilizationState;
import com.mattmalec.pterodactyl4j.client.entities.Utilization;
import org.json.JSONObject;

public class UtilizationImpl implements Utilization {

    private final JSONObject json;
    private final JSONObject resources;

    public UtilizationImpl(JSONObject json) {
        this.json = json.getJSONObject("attributes");
        this.resources = this.json.getJSONObject("resources");
    }

    @Override
    public UtilizationState getState() {
        return UtilizationState.of(json.getString("current_state"));
    }

    @Override
    public long getMemory() {
        return resources.getLong("memory_bytes");
    }

    @Override
    public long getDisk() {
        return resources.getLong("disk_bytes");
    }

    @Override
    public double getCPU() {
        return resources.getDouble("cpu_absolute");
    }

    @Override
    public long getNetworkIngress() {
        return resources.getLong("network_rx_bytes");
    }

    @Override
    public long getNetworkEgress() {
        return resources.getLong("network_tx_bytes");
    }

    @Override
    public boolean isSuspended() {
        return json.getBoolean("is_suspended");
    }

    @Override
    public String toString() {
        return json.toString(4);
    }
}
