/*
 *    Copyright 2021-2022 Matt Malec, and the Pterodactyl4J contributors
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

import com.mattmalec.pterodactyl4j.client.entities.ClientEgg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.json.JSONObject;

public class ClientEggImpl implements ClientEgg {

	private final JSONObject json;
	private final JSONObject variables;

	public ClientEggImpl(JSONObject json, JSONObject variables) {
		this.json = json.getJSONObject("attributes");
		this.variables = variables;
	}

	@Override
	public String getName() {
		return json.getString("name");
	}

	@Override
	public UUID getUUID() {
		return UUID.fromString(json.getString("uuid"));
	}

	@Override
	public List<EggVariable> getVariables() {
		List<EggVariable> variables = new ArrayList<>();
		for (Object o : this.variables.getJSONArray("data")) {
			JSONObject variable = new JSONObject(o.toString());
			variables.add(new ClientEggVariableImpl(variable));
		}
		return Collections.unmodifiableList(variables);
	}
}
