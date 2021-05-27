package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.Permission;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;

public interface SubuserAction extends PteroAction<ClientSubuser> {

    SubuserAction setPermissions(Permission... permissions);
    
}
