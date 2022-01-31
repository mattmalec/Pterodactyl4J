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
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.managers.ClientDatabaseCreationAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class ClientDatabaseCreationActionImpl extends PteroActionImpl<ClientDatabase> implements ClientDatabaseCreationAction {

    private String name;
    private String remote;

    public ClientDatabaseCreationActionImpl(ClientServer server, PteroClientImpl impl) {
        super(impl.getP4J(), Route.ClientDatabases.CREATE_DATABASE.compile(server.getIdentifier()),
                (response, request) -> new ClientDatabaseImpl(response.getObject(), server));
    }

    @Override
    public ClientDatabaseCreationAction setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ClientDatabaseCreationAction setRemote(String remote) {
        this.remote = remote;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        Checks.notBlank(name, "Database Name");
        Checks.check(name.length() >= 1 && name.length() <= 48, "Database Name must be between 1-48 characters long");

        Checks.notBlank(remote, "Remote Connection String");
        Checks.check(remote.length() >= 1 && remote.length() <= 15, "Remote Connection String must be between 1-15 characters long");

        JSONObject json = new JSONObject()
                .put("database", name)
                .put("remote", remote);

        return getRequestBody(json);
    }
}
