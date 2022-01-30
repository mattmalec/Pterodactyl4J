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

package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.entities.Database;
import com.mattmalec.pterodactyl4j.utils.Relationed;

public interface ApplicationDatabase extends Database, ISnowflake {

    PteroAction<ApplicationServer> retrieveServer();
    long getServerIdLong();
    default String getServerId() {
        return Long.toUnsignedString(getServerIdLong());
    }

    Relationed<DatabaseHost> getHost();
    long getHostIdLong();
    default String getHostId() {
        return Long.toUnsignedString(getHostIdLong());
    }

    interface DatabaseHost extends Database.DatabaseHost, ISnowflake {
        String getName();
        String getUserName();

        long getNodeIdLong();
        default String getNodeId() {
            return Long.toUnsignedString(getNodeIdLong());
        }

        PteroAction<Node> retrieveNode();
    }

}
