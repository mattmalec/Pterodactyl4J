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

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.APIKey;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class APIKeyImpl implements APIKey {

    private final JSONObject json;
    private final JSONObject attributes;
    private final PteroClientImpl impl;

    public APIKeyImpl(JSONObject json, PteroClientImpl impl) {
        this.json = json;
        this.attributes = json.getJSONObject("attributes");
        this.impl = impl;
    }

    @Override
    public String getIdentifier() {
        return attributes.getString("identifier");
    }

    @Override
    public String getDescription() {
        return attributes.getString("description");
    }

    @Override
    public Set<String> getAllowedIps() {
        JSONArray ips = attributes.getJSONArray("allowed_ips");
        if (ips.length() == 0)
            return Collections.emptySet();

        Set<String> allowedIps = new HashSet<>();
        ips.forEach(o -> allowedIps.add(o.toString()));
        return Collections.unmodifiableSet(allowedIps);
    }

    @Override
    public OffsetDateTime getLastUsedDate() {
        return OffsetDateTime.parse(attributes.optString("last_used_at"));
    }

    @Override
    public OffsetDateTime getCreationDate() {
        return OffsetDateTime.parse(attributes.getString("created_at"));
    }

    @Override
    public String getToken() {
       if (json.has("meta"))
            return json.getJSONObject("meta").getString("secret_token");
       return null;
    }

    @Override
    public PteroAction<Void> delete() {
        return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Accounts.DELETE_API_KEY.compile(getIdentifier()));
    }

    @Override
    public String toString() {
        return json.toString(4);
    }
}
