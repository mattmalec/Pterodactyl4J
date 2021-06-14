package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractSubuserAction;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.util.stream.Collectors;

public class EditSubuserImpl extends AbstractSubuserAction {

    public EditSubuserImpl(ClientServer server, ClientSubuser subuser, PteroClientImpl impl) {
        super(impl, Route.Subusers.UPDATE_SUBUSER.compile(server.getUUID().toString(), subuser.getUUID().toString()));
    }

    @Override
    protected RequestBody finalizeData() {
        Checks.notEmpty(this.permissions, "Permissions");
        JSONObject json = new JSONObject()
                .put("permissions", permissions.stream().map(permission -> permission.getRaw()).collect(Collectors.toList()));
        return getRequestBody(json);
    }
}
