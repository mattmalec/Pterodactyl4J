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

package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.ApplicationDatabase;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.managers.ApplicationDatabaseCreationAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class ApplicationDatabaseCreationActionImpl extends PteroActionImpl<ApplicationDatabase> implements ApplicationDatabaseCreationAction {

    private String name;
    private String remote;
    private long host;

    public ApplicationDatabaseCreationActionImpl(ApplicationServer server, PteroApplicationImpl impl) {
        super(impl.getP4J(), Route.Databases.CREATE_DATABASE.compile(server.getId()),
                (response, request) -> new ApplicationDatabaseImpl(response.getObject(), server, impl));
    }

    @Override
    public ApplicationDatabaseCreationAction setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ApplicationDatabaseCreationAction setRemote(String remote) {
        this.remote = remote;
        return this;
    }

    @Override
    public ApplicationDatabaseCreationAction setHost(long id) {
        this.host = id;
        return this;
    }


    @Override
    protected RequestBody finalizeData() {
        Checks.notBlank(name, "Database Name");
        Checks.check(name.length() >= 1 && name.length() <= 48, "Database Name must be between 1-48 characters long");

        Checks.notBlank(remote, "Remote Connection String");
        Checks.check(remote.length() >= 1 && remote.length() <= 15, "Remote Connection String must be between 1-15 characters long");

        Checks.notNumeric(host, "Database Host");
        Checks.check(host > 0, "The Database Host id cannot be less than 0");

        JSONObject json = new JSONObject()
                .put("database", name)
                .put("remote", remote)
                .put("host", host);

        return getRequestBody(json);
    }
}
