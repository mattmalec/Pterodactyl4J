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

import com.mattmalec.pterodactyl4j.application.entities.Script;
import org.json.JSONObject;

public class ScriptImpl implements Script {

    private final JSONObject json;

    public ScriptImpl(JSONObject json) {
        this.json = json;
    }

    @Override
    public boolean isPrivileged() {
        return json.getBoolean("privileged");
    }

    @Override
    public String getInstall() {
        return json.getString("install");
    }

    @Override
    public String getEntry() {
        return json.getString("entry");
    }

    @Override
    public String getContainer() {
        return json.getString("container");
    }

    @Override
    public String getExtends() {
        return json.getString("extends");
    }

    @Override
    public String toString() {
        return json.toString(4);
    }
}
