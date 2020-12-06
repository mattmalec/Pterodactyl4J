package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.Permission;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.UUID;

public class ClientSubuserImpl implements ClientSubuser {

    private JSONObject json;

    public ClientSubuserImpl(JSONObject json) {
        this.json = json.getJSONObject("attributes");
    }

    @Override
    public String getUserName() {
        return json.getString("username");
    }

    @Override
    public String getEmail() {
        return json.getString("email");
    }


    @Override
    public String getImage() {
        return json.getString("image");
    }

    @Override
    public boolean has2FA() {
        return json.getBoolean("2fa_enabled");
    }

    @Override
    public EnumSet<Permission> getPermissions() {
        EnumSet<Permission> perms = EnumSet.noneOf(Permission.class);
        for(Object o : json.getJSONArray("permissions")) {
            perms.add(Permission.ofRaw(o.toString()));
        }
        return perms;
    }

    @Override
    public boolean hasPermission(Permission... permission) {
        return getPermissions().containsAll(Arrays.asList(permission));
    }

    @Override
    public UUID getUUID() {
        return UUID.fromString(json.getString("uuid"));
    }

    @Override
    public OffsetDateTime getCreationDate() {
        return OffsetDateTime.parse(json.getString("created_at"));
    }

    @Override
    public String toString() {
        return json.toString(4);
    }

}
