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

package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationDatabase;

/**
 * {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} extension designed for the creation of
 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationDatabase ApplicationDatabases}
 *
 * @see ApplicationDatabaseManager#createDatabase
 */
public interface ApplicationDatabaseCreationAction extends PteroAction<ApplicationDatabase> {

    /**
     * Sets the name for this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationDatabase ApplicationDatabase}.
     *
     * <br>The panel validates this value using the following regex: {@code /^[A-Za-z0-9_]+$/}
     *
     * @param  name
     *         The name for the database
     *
     * @throws IllegalArgumentException
     *         If the provided name is {@code null} or not between 1-48 characters long
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ApplicationDatabaseCreationAction ApplicationDatabaseCreationAction}
     * instance, useful for chaining
     */
    ApplicationDatabaseCreationAction setName(String name);

    /**
     * Sets the remote connection string for this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationDatabase ApplicationDatabase}.
     *
     * <br>The panel validates this value using the following regex: {@code /^[0-9%.]{1,15}$/}
     *
     * @param  remote
     *         The remote connection string for the database
     *
     * @throws IllegalArgumentException
     *         If the provided remote connection string is {@code null} or not between 1-15 characters long
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ApplicationDatabaseCreationAction ApplicationDatabaseCreationAction}
     * instance, useful for chaining
     */
    ApplicationDatabaseCreationAction setRemote(String remote);

    /**
     * Sets the database host id for this
     * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationDatabase ApplicationDatabase}.
     *
     * @param  id
     *         The host id
     *
     * @throws IllegalArgumentException
     *         If the provided id is less than 0
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ApplicationDatabaseCreationAction ApplicationDatabaseCreationAction}
     * instance, useful for chaining
     */
    ApplicationDatabaseCreationAction setHost(long id);

    /**
     * Sets the database host id for this
     * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationDatabase ApplicationDatabase}.
     *
     * @param  id
     *         The host id
     *
     * @throws IllegalArgumentException
     *         If the provided id is less than 0
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ApplicationDatabaseCreationAction ApplicationDatabaseCreationAction}
     * instance, useful for chaining
     */
    default ApplicationDatabaseCreationAction setHost(String id) {
        return setHost(Long.parseUnsignedLong(id));
    }

}
