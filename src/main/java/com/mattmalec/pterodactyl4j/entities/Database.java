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
 * Represents a Pterodactyl {@link com.mattmalec.pterodactyl4j.entities.Database Database}.
 * This should contain all information provided from the Pterodactyl instance about an Database.
 */
public interface Database {

    /**
     * The human readable name of the Database.
     *
     * @return Never-null String containing the Database's name.
     */
    String getName();

    /**
     * The username used when connecting to the Database.
     *
     * @return Never-null String containing the username
     */
    String getUserName();

    /**
     * The hosts allowed to connect to the Database.
     *
     * @return Never-null String containing the remote endpoint
     */
    String getRemote();

    /**
     * The maximum amount of connections allowed to connect to the Database.
     *
     * @return The maximum amount of connections.
     */
    int getMaxConnections();

    /**
     * Retrieves the password used to connect to the Database.
     *
     * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.lang.String String}
     */
    PteroAction<String> retrievePassword();

    /**
     * Delete this Database.
     *
     * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction}
     */
    PteroAction<Void> delete();

    /**
     * Represents the {@link com.mattmalec.pterodactyl4j.entities.Database.DatabaseHost DatabaseHost} associated
     * with a {@link com.mattmalec.pterodactyl4j.entities.Database Database}.
     */
    interface DatabaseHost {

        /**
         * The endpoint used when connecting to this {@link DatabaseHost}.
         *
         * @return Never-null String containing the host endpoint
         */
        String getAddress();

        /**
         * The port used when connecting to this {@link DatabaseHost}.
         *
         * @return The port used for connecting to the host
         */
        int getPort();
    }

}
