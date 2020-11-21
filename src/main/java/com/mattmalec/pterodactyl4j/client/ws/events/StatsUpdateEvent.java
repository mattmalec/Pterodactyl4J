package com.mattmalec.pterodactyl4j.client.ws.events;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.UtilizationState;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import org.json.JSONObject;

public class StatsUpdateEvent extends Event {

    private JSONObject stats;

    public StatsUpdateEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, JSONObject stats) {
        super(api, server, manager);
        this.stats = stats;
    }

    public UtilizationState getState() {
        return UtilizationState.of(stats.getString("state"));
    }

    public long getMemory() {
        return stats.getLong("memory_bytes");
    }

    public String getMemoryFormatted(DataType dataType) {
        return String.format("%.2f %s", getMemory() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
    }

    public long getMaxMemory() {
        return stats.getLong("memory_limit_bytes");
    }

    public String getMaxMemoryFormatted(DataType dataType) {
        return String.format("%.2f %s", getMaxMemory() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
    }

    public long getDisk() {
        return stats.getLong("disk_bytes");
    }

    public String getDiskFormatted(DataType dataType) {
        return String.format("%.2f %s", getDisk() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
    }

    public double getCPU() {
        return stats.getDouble("cpu_absolute");
    }

    public long getNetworkIngress() {
        return stats.getJSONObject("network").getLong("rx_bytes");
    }

    public String getNetworkIngressFormatted(DataType dataType) {
        return String.format("%.2f %s", getNetworkIngress() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
    }

    public long getNetworkEgress() {
        return stats.getJSONObject("network").getLong("tx_bytes");
    }

    public String getNetworkEgressFormatted(DataType dataType) {
        return String.format("%.2f %s", getNetworkEgress() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
    }


}
