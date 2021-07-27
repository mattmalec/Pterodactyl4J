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

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractSubuserAction;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.util.stream.Collectors;

public class EditSubuserImpl extends AbstractSubuserAction {

    public EditSubuserImpl(ClientServer server, ClientSubuser subuser, PteroClientImpl impl) {
        super(impl, Route.Subusers.UPDATE_SUBUSER.compile(server.getUUID().toString(), subuser.getUUID().toString()));
    }

    @Override
    protected RequestBody finalizeData() {
        Checks.notEmpty(this.permissions, "Permissions");
        JSONObject json = new JSONObject()
                .put("permissions", permissions.stream().map(permission -> permission.getRaw()).collect(Collectors.toList()));
        return getRequestBody(json);
    }
}
