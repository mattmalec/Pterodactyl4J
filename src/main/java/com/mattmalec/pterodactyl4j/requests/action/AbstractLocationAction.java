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

import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.impl.LocationImpl;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.requests.Route;

public abstract class AbstractLocationAction extends PteroActionImpl<Location> implements LocationAction {

    protected String shortCode;
    protected String description;

    public AbstractLocationAction(PteroApplicationImpl impl, Route.CompiledRoute route) {
        super(impl.getP4J(), route, (response, request) -> new LocationImpl(response.getObject(), impl));
    }

    @Override
    public LocationAction setShortCode(String shortCode) {
        this.shortCode = shortCode;
        return this;
    }

    @Override
    public LocationAction setDescription(String description) {
        this.description = description;
        return this;
    }
}
