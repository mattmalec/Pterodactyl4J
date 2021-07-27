/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;
import com.mattmalec.pterodactyl4j.application.managers.UserManager;
import com.mattmalec.pterodactyl4j.requests.Route;

public class UserManagerImpl implements UserManager {

    private final PteroApplicationImpl impl;

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
        return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Users.DELETE_USER.compile(user.getId()));
    }
}
