package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;
import com.mattmalec.pterodactyl4j.application.managers.UserManager;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;

public class UserManagerImpl implements UserManager {

    private PteroApplicationImpl impl;

    public UserManagerImpl(PteroApplicationImpl impl) {
        this.impl = impl;
    }

    @Override
    public UserAction createUser() {
        return new CreateUserImpl(impl);
    }

    @Override
    public UserAction editUser(ApplicationUser user) {
        return new EditUserImpl(user, impl);
    }

    @Override
    public PteroAction<Void> deleteUser(ApplicationUser user) {
        return PteroActionImpl.onExecute(() -> {
            Route.CompiledRoute route = Route.Users.DELETE_USER.compile(user.getId());
            impl.getRequester().request(route);
            return null;
        });
    }
}
