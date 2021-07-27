package com.mattmalec.pterodactyl4j.requests.action;

import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.application.entities.impl.ApplicationUserImpl;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;
import com.mattmalec.pterodactyl4j.requests.Route;

public abstract class AbstractUserAction extends PteroActionImpl<ApplicationUser> implements UserAction {

    protected String userName;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String password;

    public AbstractUserAction(PteroApplicationImpl impl, Route.CompiledRoute route) {
        super(impl.getP4J(), route, (response, request) -> new ApplicationUserImpl(response.getObject(), impl));
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
}
