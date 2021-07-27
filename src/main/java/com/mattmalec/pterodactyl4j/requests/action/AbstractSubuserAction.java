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

package com.mattmalec.pterodactyl4j.requests.action;

import com.mattmalec.pterodactyl4j.Permission;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;
import com.mattmalec.pterodactyl4j.client.entities.impl.ClientSubuserImpl;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.SubuserAction;
import com.mattmalec.pterodactyl4j.requests.Route;

import java.util.Arrays;
import java.util.EnumSet;

public abstract class AbstractSubuserAction extends PteroActionImpl<ClientSubuser> implements SubuserAction {

    protected EnumSet<Permission> permissions;

    public AbstractSubuserAction(PteroClientImpl impl, Route.CompiledRoute route) {
        super(impl.getP4J(), route, (response, request) -> new ClientSubuserImpl(response.getObject()));
        this.permissions = EnumSet.noneOf(Permission.class);
    }

    @Override
    public SubuserAction setPermissions(Permission... permissions) {
        this.permissions.addAll(Arrays.asList(permissions));
        return this;
    }
}
