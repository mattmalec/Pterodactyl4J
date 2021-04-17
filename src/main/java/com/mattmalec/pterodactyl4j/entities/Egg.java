package com.mattmalec.pterodactyl4j.entities;

import com.mattmalec.pterodactyl4j.EnvironmentValue;

import java.util.UUID;

public interface Egg {

    String getName();
    UUID getUUID();

    interface EggVariable  {
        String getName();
        String getDescription();
        String getEnvironmentVariable();
        EnvironmentValue<?> getDefaultValue();
        String getRules();
    }

}
