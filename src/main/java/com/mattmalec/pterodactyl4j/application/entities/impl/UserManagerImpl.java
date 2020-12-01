package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;
import com.mattmalec.pterodactyl4j.application.managers.UserManager;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;

public class UserManagerImpl implements UserManager {

    private final Requester requester;

    public UserManagerImpl(Requester requester) {
        this.requester = requester;
    }

    @Override
    public UserAction createUser() {
        return new CreateUserImpl(this.requester);
    }

    @Override
    public UserAction editUser(ApplicationUser user) {
        return new EditUserImpl(user, this.requester);
    }

    @Override
    public PteroAction<Void> deleteUser(ApplicationUser user) {
        return new PteroAction<Void>() {
            Route.CompiledRoute route = Route.Users.DELETE_USER.compile(user.getId());
            @Override
            public Void execute() {
                requester.request(route);
                return null;
            }
        };
    }
}
