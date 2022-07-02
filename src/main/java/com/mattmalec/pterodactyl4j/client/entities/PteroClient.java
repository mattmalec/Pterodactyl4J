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

package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.ClientType;
import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.requests.PaginationAction;

import java.util.List;

/**
 * Represents the access to a user's server panel
 * To build a {@link PteroClient} use {@link PteroBuilder#buildClient()}
 */
public interface PteroClient {

    /**
     * Retrieves the Pterodactyl user account belonging to the API key
     *
     * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.client.entities.Account Account}
     * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException If the API key is incorrect
     */
    PteroAction<Account> retrieveAccount();

    /**
     * Sets the power of a {@link com.mattmalec.pterodactyl4j.client.entities.ClientServer ClientServer}
     *
     * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link Void}
     * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException If the API key is incorrect
     * @deprecated Use {@link ClientServer#setPower(PowerAction)} instead
     */
    @Deprecated
    PteroAction<Void> setPower(ClientServer server, PowerAction powerAction);

    /**
     * Sends a command to a {@link com.mattmalec.pterodactyl4j.client.entities.ClientServer ClientServer}
     *
     * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link Void}
     * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException If the API key is incorrect
     * @deprecated Use {@link ClientServer#sendCommand(String)} instead
     */
    @Deprecated
    PteroAction<Void> sendCommand(ClientServer server, String command);

    /**
     * Retrieves the utilization of a {@link com.mattmalec.pterodactyl4j.client.entities.ClientServer ClientServer}
     *
     * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.client.entities.Utilization Utilization}
     * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException If the API key is incorrect
     * @deprecated Use {@link ClientServer#retrieveUtilization()} instead
     */
    @Deprecated
    PteroAction<Utilization> retrieveUtilization(ClientServer server);

    /**
     * Retrieves all the ClientServers from the Pterodactyl instance
     *
     * @param type
     *        Type for the appended type parameter (NONE, ADMIN, ADMIN-ALL, OWNER)
     *
     * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.client.entities.ClientServer ClientServers}
     * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException If the API key is incorrect
     */
    PaginationAction<ClientServer> retrieveServers(ClientType type);

    default PaginationAction<ClientServer> retrieveServers() {
        return retrieveServers(ClientType.NONE);
    }

    /**
     * Retrieves an individual ClientServer represented by the provided identifier from Pterodactyl instance
     *
     * @param identifier The server identifier (first 8 characters of the uuid)
     * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.client.entities.ClientServer ClientServer}
     * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException    If the API key is incorrect
     * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException If the server cannot be found
     */
    PteroAction<ClientServer> retrieveServerByIdentifier(String identifier);

    /**
     * Retrieves ClientServers matching the provided name from Pterodactyl instance
     *
     * @param name          The name
     * @param caseSensitive True - If P4J should search using case sensitivity
     * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.client.entities.ClientServer ClientServers}
     * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException If the API key is incorrect
     */
    PteroAction<List<ClientServer>> retrieveServersByName(String name, boolean caseSensitive);

}
