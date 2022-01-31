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

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationDatabase;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.entities.impl.DatabasePasswordImpl;
import com.mattmalec.pterodactyl4j.utils.Relationed;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.Optional;

public class ApplicationDatabaseImpl implements ApplicationDatabase {

    private final JSONObject json;
    private final JSONObject relationships;
    private final ApplicationServer server;
    private final PteroApplicationImpl impl;

    public ApplicationDatabaseImpl(JSONObject json, ApplicationServer server, PteroApplicationImpl impl) {
        this.json = json.getJSONObject("attributes");
        this.relationships = json.getJSONObject("attributes").optJSONObject("relationships");
        this.server = server;
        this.impl = impl;
    }

    @Override
    public Relationed<ApplicationServer> retrieveServer() {
        return new Relationed<ApplicationServer>() {
            @Override
            public PteroAction<ApplicationServer> retrieve() {
                return impl.retrieveServerById(getServerIdLong());
            }

            @Override
            public Optional<ApplicationServer> get() {
                return Optional.of(server);
            }
        };
    }

    @Override
    public long getServerIdLong() {
        return json.getLong("server");
    }

    @Override
    public Relationed<DatabaseHost> getHost() {
        return new Relationed<DatabaseHost>() {
            @Override
            public PteroAction<DatabaseHost> retrieve() {
                return server.retrieveDatabaseById(getIdLong())
                        .map(ApplicationDatabase::getHost).map(Relationed::get).map(Optional::get);
            }

            @Override
            public Optional<DatabaseHost> get() {
                if (!json.has("relationships")) return Optional.empty();
                return Optional.of(new ApplicationDatabaseHostImpl(relationships.getJSONObject("host"), impl));
            }
        };
    }

    @Override
    public long getHostIdLong() {
        return json.getLong("host");
    }

    @Override
    public long getIdLong() {
        return json.getLong("id");
    }

    @Override
    public OffsetDateTime getCreationDate() {
        return OffsetDateTime.parse(json.optString("created_at"));
    }

    @Override
    public OffsetDateTime getUpdatedDate() {
        return OffsetDateTime.parse(json.optString("updated_at"));
    }

    @Override
    public String getName() {
        return json.getString("database");
    }

    @Override
    public String getUserName() {
        return json.getString("username");
    }

    @Override
    public String getRemote() {
        return json.getString("remote");
    }

    @Override
    public int getMaxConnections() {
        return json.optInt("max_connections");
    }

    @Override
    public Relationed<String> getPassword() {
        return new Relationed<String>() {
            @Override
            public PteroAction<String> retrieve() {
                return retrieveServer()
                        .get().get().retrieveDatabaseById(getIdLong())
                                .map(ApplicationDatabase::getPassword).map(Relationed::get).map(Optional::get);
            }

            @Override
            public Optional<String> get() {
                if (!json.has("relationships")) return Optional.empty();
                return Optional.of(new DatabasePasswordImpl(relationships.getJSONObject("password")).getPassword());
            }
        };
    }

    @Override
    public PteroAction<Void> resetPassword() {
        return server.getDatabaseManager().resetPassword(this);
    }

    @Override
    public PteroAction<Void> delete() {
        return server.getDatabaseManager().deleteDatabase(this);
    }

    @Override
    public String toString() {
        return json.toString(4);
    }
}
