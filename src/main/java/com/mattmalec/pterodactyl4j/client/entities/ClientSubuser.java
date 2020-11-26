package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.entities.IPermissionHolder;
import com.mattmalec.pterodactyl4j.entities.User;

import java.time.OffsetDateTime;

public interface ClientSubuser extends User, IPermissionHolder {

    String getImage();
    boolean has2FA();
    OffsetDateTime getCreationDate();

}
