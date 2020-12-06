package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.Permission;
import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;
import com.mattmalec.pterodactyl4j.client.managers.SubuserAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

public class EditSubuserImpl implements SubuserAction {

    private ClientServer server;
    private ClientSubuser subuser;
    private PteroClientImpl impl;

    private EnumSet<Permission> permissions;

    public EditSubuserImpl(ClientServer server, ClientSubuser subuser, PteroClientImpl impl) {
        this.server = server;
        this.subuser = subuser;
        this.impl = impl;
    }

    @Override
    public SubuserAction setPermissions(Permission... permissions) {
        this.permissions.addAll(Arrays.asList(permissions));
        return this;
    }

    @Override
    public PteroAction<ClientSubuser> build() {
        Checks.notEmpty(this.permissions, "Permissions");
        JSONObject json = new JSONObject()
                .put("permissions", permissions.stream().map(permission -> permission.getRaw()).collect(Collectors.toList()));
        return PteroActionImpl.onExecute(() -> {
            Route.CompiledRoute route = Route.Subusers.UPDATE_SUBUSER.compile(server.getUUID().toString(), subuser.getUUID().toString()).withJSONdata(json);
            JSONObject obj = impl.getRequester().request(route).toJSONObject();
            return new ClientSubuserImpl(obj);
        });
    }
}
