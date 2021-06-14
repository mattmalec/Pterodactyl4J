package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractUserAction;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class EditUserImpl extends AbstractUserAction {

    private ApplicationUser user;

    public EditUserImpl(ApplicationUser user, PteroApplicationImpl impl) {
        super(impl, Route.Users.EDIT_USER.compile(user.getId()));
        this.user = user;
    }

    @Override
    protected RequestBody finalizeData() {
        JSONObject json = new JSONObject();
        json.put("username", userName == null ? user.getUserName() : userName);
        json.put("email", email == null ? user.getEmail() : email);
        json.put("first_name", firstName == null ? user.getFirstName() : firstName);
        json.put("last_name", lastName == null ? user.getLastName() : lastName);
        json.put("password", password);
        return getRequestBody(json);
    }
}
