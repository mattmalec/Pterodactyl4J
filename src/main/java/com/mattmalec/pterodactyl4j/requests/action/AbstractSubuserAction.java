package com.mattmalec.pterodactyl4j.requests.action;

import com.mattmalec.pterodactyl4j.Permission;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;
import com.mattmalec.pterodactyl4j.client.entities.impl.ClientSubuserImpl;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.SubuserAction;
import com.mattmalec.pterodactyl4j.requests.Route;

import java.util.Arrays;
import java.util.EnumSet;

public abstract class AbstractSubuserAction extends PteroActionImpl<ClientSubuser> implements SubuserAction {

    protected EnumSet<Permission> permissions;

    public AbstractSubuserAction(PteroClientImpl impl, Route.CompiledRoute route) {
        super(impl.getPteroApi(), route, (response, request) -> new ClientSubuserImpl(response.getObject()));
        this.permissions = EnumSet.noneOf(Permission.class);
    }

    @Override
    public SubuserAction setPermissions(Permission... permissions) {
        this.permissions.addAll(Arrays.asList(permissions));
        return this;
    }
}
