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

package com.mattmalec.pterodactyl4j.entities;

import com.mattmalec.pterodactyl4j.PteroAction;

/**
 * Represents a pterodactyl database.
 */
public interface Database {

    /**
     * Retrieve the name of the {@link Database}.
     *
     * @return Name
     */
    String getName();

    /**
     * Retrieve the username of the {@link Database}.
     *
     * @return Username
     */
    String getUserName();

    /**
     * Retrieve the remote of the {@link Database}.
     *
     * @return Remote
     */
    String getRemote();

    /**
     * Retrieve the maximum amount of connections for this {@link Database}.
     *
     * @return Maximum amount of allowed connections.
     */
    int getMaxConnections();

    /**
     * Retrieve the password for this {@link Database}.
     *
     * @return Password
     */
    PteroAction<String> retrievePassword();

    /**
     * Deletes this database.
     *
     * @return Void
     */
    PteroAction<Void> delete();

    /**
     * Represents a host for a {@link Database}.
     */
    interface DatabaseHost {

        /**
         * Retrieve the address associated with this {@link DatabaseHost}.
         *
         * @return Address
         */
        String getAddress();

        /**
         * Retrieve the port associated with this {@link DatabaseHost}.
         *
         * @return Port
         */
        int getPort();
    }

}
