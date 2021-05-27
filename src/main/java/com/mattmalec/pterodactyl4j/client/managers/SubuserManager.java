package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;

public interface SubuserManager {

    SubuserCreationAction createUser();
    SubuserAction editUser(ClientSubuser subuser);
    PteroAction<Void> deleteUser(ClientSubuser subuser);

}
