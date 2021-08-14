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
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;

/**
 * Manager providing functionality to modify details for an
 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
 *
 * <p><b>Example</b>
 * <pre>{@code
 * manager.setName("Amazing server") // set the server name
 *     .remove(ServerDetailManager.DESCRIPTION) // remove the description
 *     .executeAsync();
 * }</pre>
 *
 * @see ApplicationServer#getDetailManager()
 */
public interface ServerDetailManager extends PteroAction<ApplicationServer> {

    /** Used to remove the server description */
    long DESCRIPTION = 0x1;
    /** Used to remove the external id */
    long EXTERNAL_ID = 0x2;

    /**
     * Removes the fields specified by the provided bit-flag pattern.
     * You can specify a combination by using a bitwise OR concat of the flag constants.
     *
     * <p><b>Example</b>
     * <pre>{@code
     *   manager.remove(ServerDetailAction.DESCRIPTION | ServerDetailAction.EXTERNAL_ID).executeAsync();
     * }</pre>
     *
     * <p><b>Flag Constants:</b>
     * <ul>
     *     <li>{@link #DESCRIPTION}</li>
     *     <li>{@link #EXTERNAL_ID}</li>
     * </ul>
     *
     * @param  fields
     *         Integer value containing the flags to remove.
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerDetailManager ServerDetailManager} instance, useful for chaining
     */
    ServerDetailManager remove(long fields);

    /**
     * Removes the fields specified by the provided bit-flag patterns.
     * You can specify a combination by using a bitwise OR concat of the flag constants.
     *
     * <p><b>Example</b>
     * <pre>{@code
     *   manager.remove(ServerDetailAction.DESCRIPTION, ServerDetailAction.EXTERNAL_ID).executeAsync();
     * }</pre>
     *
     * <p><b>Flag Constants:</b>
     * <ul>
     *     <li>{@link #DESCRIPTION}</li>
     *     <li>{@link #EXTERNAL_ID}</li>
     * </ul>
     *
     * @param  fields
     *         Integer values containing the flags to remove.
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerDetailManager ServerDetailManager}
     * instance, useful for chaining
     */
    ServerDetailManager remove(long... fields);


    /**
     * Removes all the removable elements
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerDetailManager ServerDetailManager}
     * instance, useful for chaining
     */
    ServerDetailManager remove();

    /**
     * Sets the name of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
     *
     * @param  name
     *         The new name for the server
     *
     * @throws IllegalArgumentException
     *         If the provided name is {@code null} or not between 1-191 characters long
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerDetailManager ServerDetailManager}
     * instance, useful for chaining
     */
    ServerDetailManager setName(String name);

    /**
     * Sets the owner of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
     *
     * @param  user
     *         The new owner for the server
     *
     * @throws IllegalArgumentException
     *         If the provided user is {@code null}
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerDetailManager ServerDetailManager}
     * instance, useful for chaining
     */
    ServerDetailManager setOwner(ApplicationUser user);

    /**
     * Sets the description of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
     *
     * @param  description
     *         The new description for the server or use {@link ServerDetailManager#remove(long)} to remove
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerDetailManager ServerDetailManager}
     * instance, useful for chaining
     *
     * @see ServerDetailManager#remove(long)
     * @see ServerDetailManager#remove(long...)
     */
    ServerDetailManager setDescription(String description);


    /**
     * Sets the external id of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
     *
     * @param  id
     *         The new external for the server or use {@link ServerDetailManager#remove(long)} to remove
     *
     * @throws IllegalArgumentException
     *         If the provided id is not between 1-191 characters long
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerDetailManager ServerDetailManager}
     * instance, useful for chaining
     *
     * @see ServerDetailManager#remove(long)
     * @see ServerDetailManager#remove(long...)
     */
    ServerDetailManager setExternalId(String id);

}
