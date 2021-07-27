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

import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractLocationAction;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class EditLocationImpl extends AbstractLocationAction {

    private final Location location;

    EditLocationImpl(Location location, PteroApplicationImpl impl) {
        super(impl, Route.Locations.EDIT_LOCATION.compile(location.getId()));
        this.location = location;
    }

    @Override
    protected RequestBody finalizeData() {
        JSONObject json = new JSONObject();
        json.put("short", shortCode == null ? location.getShortCode() : shortCode);
        json.put("long", description == null ? location.getDescription() : description);
        return getRequestBody(json);
    }
}
