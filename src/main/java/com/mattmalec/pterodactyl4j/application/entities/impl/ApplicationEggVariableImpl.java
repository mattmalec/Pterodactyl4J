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

import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg;
import org.json.JSONObject;

import java.time.OffsetDateTime;

public class ApplicationEggVariableImpl implements ApplicationEgg.EggVariable {

    private final JSONObject json;

    public ApplicationEggVariableImpl(JSONObject json) {
        this.json = json.getJSONObject("attributes");
    }

    @Override
    public String getName() {
        return json.getString("name");
    }

    @Override
    public String getDescription() {
        return json.getString("description");
    }

    @Override
    public String getEnvironmentVariable() {
        return json.getString("env_variable");
    }

    @Override
    public EnvironmentValue<?> getDefaultValue() {
        return EnvironmentValue.of(json.get("default_value"));
    }

    @Override
    public boolean isUserViewable() {
        return json.getBoolean("user_viewable");
    }

    @Override
    public boolean isUserEditable() {
        return json.getBoolean("user_editable");
    }

    @Override
    public String getRules() {
        return json.getString("rules");
    }

    @Override
    public long getIdLong() {
        return json.getLong("id");
    }

    @Override
    public OffsetDateTime getCreationDate() {
        return OffsetDateTime.parse(json.optString("created_at"));
    }

    @Override
    public OffsetDateTime getUpdatedDate() {
        return OffsetDateTime.parse(json.optString("updated_at"));
    }
}
