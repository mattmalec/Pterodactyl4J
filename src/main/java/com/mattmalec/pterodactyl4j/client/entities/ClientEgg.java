package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.entities.Egg;

import java.util.List;

public interface ClientEgg extends Egg {

    List<EggVariable> getVariables();

    interface EggVariable extends Egg.EggVariable {
        String getServerValue();
        boolean isEditable();
    }
}
