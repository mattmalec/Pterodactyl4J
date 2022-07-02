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

import com.mattmalec.pterodactyl4j.entities.IPermissionHolder;
import com.mattmalec.pterodactyl4j.entities.User;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Represents a sub-user pm a {@link ClientServer}
 */
public interface ClientSubuser extends User, IPermissionHolder {
    /**
     * Gets the avatar link of the {@link ClientSubuser}
     *
     * @return link to image of the avatar
     */
    String getImage();

    /**
     * Checks if the {@link ClientSubuser} has 2fa enabled
     *
     * @return whether the user has 2fa enabled
     */
    boolean has2FA();

    /**
     * Gets the uuid of the {@link ClientSubuser}
     *
     * @return uuid of {@link ClientSubuser}
     */
    UUID getUUID();

    OffsetDateTime getCreationDate();

}
