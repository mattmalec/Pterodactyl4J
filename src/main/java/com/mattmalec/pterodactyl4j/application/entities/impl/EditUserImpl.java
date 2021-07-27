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

import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractUserAction;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class EditUserImpl extends AbstractUserAction {

    private final ApplicationUser user;

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
