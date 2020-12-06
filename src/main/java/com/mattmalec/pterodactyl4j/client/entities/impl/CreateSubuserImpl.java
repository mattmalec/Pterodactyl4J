package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.Permission;
import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;
import com.mattmalec.pterodactyl4j.client.managers.SubuserAction;
import com.mattmalec.pterodactyl4j.client.managers.SubuserCreationAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

public class CreateSubuserImpl implements SubuserCreationAction {

    private ClientServer server;
    private PteroClientImpl impl;

    private String email;
    private EnumSet<Permission> permissions;

    public CreateSubuserImpl(ClientServer server, PteroClientImpl impl) {
        this.server = server;
        this.impl = impl;
    }

    @Override
    public SubuserCreationAction setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public SubuserAction setPermissions(Permission... permissions) {
        EnumSet<Permission> perms = EnumSet.noneOf(Permission.class);
        perms.addAll(Arrays.asList(permissions));
        this.permissions = perms;
        return this;
    }

    @Override
    public PteroAction<ClientSubuser> build() {
        Checks.notBlank(this.email, "Email");
        Checks.notEmpty(this.permissions, "Permissions");
        JSONObject json = new JSONObject()
                .put("email", email)
                .put("permissions", permissions.stream().map(permission -> permission.getRaw()).collect(Collectors.toList()));
        return PteroActionImpl.onExecute(() -> {
            Route.CompiledRoute route = Route.Subusers.CREATE_SUBUSER.compile(server.getUUID().toString()).withJSONdata(json);
            JSONObject obj = impl.getRequester().request(route).toJSONObject();
            return new ClientSubuserImpl(obj);
        });
    }
}
