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
