/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.client.ws.events;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.UtilizationState;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import org.json.JSONObject;

public class StatsUpdateEvent extends Event {

    private final JSONObject stats;

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

    public long getUptime() {
        return stats.getLong("uptime");
    }

    public String getUptimeFormatted() {
        long uptime = getUptime();
        long second = (uptime / 1000) % 60;
        long minute = (uptime / (1000 * 60)) % 60;
        long hour = (uptime / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
