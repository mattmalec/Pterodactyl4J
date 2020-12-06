package com.mattmalec.pterodactyl4j.entities;

import java.util.UUID;

public interface Egg {

    String getName();
    UUID getUUID();

    interface EggVariable  {
        String getName();
        String getDescription();
        String getEnvironmentVariable();
        String getDefaultValue();
        String getRules();
    }

}
