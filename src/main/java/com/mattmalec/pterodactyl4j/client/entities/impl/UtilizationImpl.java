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
