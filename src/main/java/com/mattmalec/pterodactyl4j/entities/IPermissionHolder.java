package com.mattmalec.pterodactyl4j.entities;

import com.mattmalec.pterodactyl4j.Permission;

import java.util.EnumSet;

public interface IPermissionHolder {

    EnumSet<Permission> getPermissions();
    boolean hasPermission(Permission... permission);

}
