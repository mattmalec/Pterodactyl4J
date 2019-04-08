package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.User;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import org.json.JSONObject;

public class CreateUserImpl implements UserAction {

    private Requester requester;

    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public CreateUserImpl(Requester requester) {
        this.requester = requester;
    }

    @Override
    public UserAction setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public UserAction setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public UserAction setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public UserAction setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public UserAction setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public PteroAction<User> build() {
        Checks.notBlank(this.userName, "Username");
        Checks.notBlank(this.email, "Email");
        Checks.notBlank(this.firstName, "First name");
        Checks.notBlank(this.lastName, "Last name");
        JSONObject json = new JSONObject();
        json.put("username", this.userName);
        json.put("email", this.email);
        json.put("first_name", this.firstName);
        json.put("last_name", this.lastName);
        json.put("password", this.password);
        return new PteroAction<User>() {
            Route.CompiledRoute route = Route.Users.CREATE_USER.compile().withJSONdata(json);
            JSONObject jsonObject = requester.request(route).toJSONObject();
            @Override
            public User execute() {
                return new UserImpl(jsonObject);
            }
        };
    }
}
