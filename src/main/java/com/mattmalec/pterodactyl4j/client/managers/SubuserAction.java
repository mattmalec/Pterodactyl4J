package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.Permission;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

public interface SubuserAction {

    SubuserAction setPermissions(Permission... permissions);
    PteroAction<ClientSubuser> build();
    
}
