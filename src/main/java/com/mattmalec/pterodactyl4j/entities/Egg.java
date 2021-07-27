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

import com.mattmalec.pterodactyl4j.EnvironmentValue;

import java.util.UUID;

/**
 * Represents a Pterodactyl {@link com.mattmalec.pterodactyl4j.entities.Egg Egg}.
 * This should contain all information provided from the Pterodactyl instance about an Egg.
 */
public interface Egg {

    /**
     * The human readable name of the Egg.
     *
     * @return Never-null String containing the Egg's name.
     */
    String getName();

    /**
     * The UUID of the Egg.
     *
     * @return Never-null {@link java.util.UUID} containing the Egg's UUID.
     */
    UUID getUUID();

    /**
     * Represents a Pterodactyl {@link com.mattmalec.pterodactyl4j.entities.Egg.EggVariable EggVariable}.
     * This should contain all information provided from the Egg about its variables.
     */
    interface EggVariable  {

        /**
         * The human readable name of the variable.
         *
         * @return Never-null String containing the variable's name.
         */
        String getName();

        /**
         * The description of the Server
         *
         * @return Never-null String containing the variable's description.
         */
        String getDescription();

        /**
         * The key of the variable
         *
         * @return Never-null String containing the variable's key.
         */
        String getEnvironmentVariable();

        /**
         * The default value of the variable
         *
         * @return Never-null {@link com.mattmalec.pterodactyl4j.EnvironmentValue EnvironmentValue} containing the variable's default value.
         */
        EnvironmentValue<?> getDefaultValue();

        /**
         * The Laravel validation rules of the variable's value
         *
         * @return Never-null String containing the variable's validation rules.
         */
        String getRules();
    }

}
