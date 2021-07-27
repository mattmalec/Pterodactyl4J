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

package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.Account;
import com.mattmalec.pterodactyl4j.client.managers.AccountManager;
import org.json.JSONObject;

public class AccountImpl implements Account {

    private final JSONObject json;
    private final PteroClientImpl impl;

    public AccountImpl(JSONObject json, PteroClientImpl impl) {
        this.json = json.getJSONObject("attributes");
        this.impl = impl;
    }

    @Override
    public AccountManager getManager() {
        return new AccountManager(impl);
    }

    @Override
    public String getUserName() {
        return json.getString("username");
    }

    @Override
    public String getEmail() {
        return json.getString("email");
    }

    @Override
    public String getFirstName() {
        return json.getString("first_name");
    }

    @Override
    public String getLastName() {
        return json.getString("last_name");
    }

    @Override
    public String getLanguage() {
        return json.getString("language");
    }

    @Override
    public long getId() {
        return json.getLong("id");
    }

    @Override
    public boolean isRootAdmin() {
        return json.getBoolean("admin");
    }

    @Override
    public String toString() {
        return json.toString(4);
    }
}
