package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.UserActionImpl;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class CreateUserImpl extends UserActionImpl {

    public CreateUserImpl(PteroApplicationImpl impl) {
        super(impl, Route.Users.CREATE_USER.compile());
    }
    
    @Override
    protected RequestBody finalizeData() {
        Checks.notBlank(userName, "Username");
        Checks.notBlank(email, "Email");
        Checks.notBlank(firstName, "First name");
        Checks.notBlank(lastName, "Last name");
        JSONObject json = new JSONObject();
        json.put("username", userName);
        json.put("email", email);
        json.put("first_name", firstName);
        json.put("last_name", lastName);
        json.put("password", password);
        return getRequestBody(json);
    }
}
