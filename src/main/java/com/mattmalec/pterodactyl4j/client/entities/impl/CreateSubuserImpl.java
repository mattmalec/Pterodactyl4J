package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.managers.SubuserCreationAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.SubuserActionImpl;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.util.stream.Collectors;

public class CreateSubuserImpl extends SubuserActionImpl implements SubuserCreationAction {

    private String email;

    public CreateSubuserImpl(ClientServer server, PteroClientImpl impl) {
        super(impl, Route.Subusers.CREATE_SUBUSER.compile(server.getUUID().toString()));
    }

    @Override
    public SubuserCreationAction setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        Checks.notBlank(this.email, "Email");
        Checks.notEmpty(this.permissions, "Permissions");
        JSONObject json = new JSONObject()
                .put("email", email)
                .put("permissions", permissions.stream().map(permission -> permission.getRaw()).collect(Collectors.toList()));
        return getRequestBody(json);
    }
}
