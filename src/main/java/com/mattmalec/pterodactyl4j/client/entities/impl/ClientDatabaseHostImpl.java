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

import com.mattmalec.pterodactyl4j.client.entities.ClientDatabase;
import org.json.JSONObject;

public class ClientDatabaseHostImpl implements ClientDatabase.DatabaseHost {

    private final JSONObject json;

    public ClientDatabaseHostImpl(JSONObject json) {
        this.json = json;
    }

    @Override
    public String getAddress() {
        return json.getString("address");
    }

    @Override
    public int getPort() {
        return json.getInt("port");
    }

    @Override
    public String toString() {
        return json.toString(4);
    }
}
