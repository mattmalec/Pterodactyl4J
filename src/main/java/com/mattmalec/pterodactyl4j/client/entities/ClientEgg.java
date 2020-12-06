package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.entities.Egg;

public interface ClientEgg extends Egg {

    interface EggVariable extends Egg.EggVariable {
        String getServerValue();
        boolean isEditable();
    }
}
