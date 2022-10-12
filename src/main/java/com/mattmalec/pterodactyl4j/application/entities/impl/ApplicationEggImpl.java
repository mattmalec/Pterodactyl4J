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

package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg;
import com.mattmalec.pterodactyl4j.application.entities.Nest;
import com.mattmalec.pterodactyl4j.application.entities.Script;
import com.mattmalec.pterodactyl4j.requests.CompletedPteroAction;
import java.time.OffsetDateTime;
import java.util.*;
import org.json.JSONObject;

public class ApplicationEggImpl implements ApplicationEgg {

	private final JSONObject json;
	private final JSONObject relationships;
	private final PteroApplicationImpl impl;

	public ApplicationEggImpl(JSONObject json, PteroApplicationImpl impl) {
		this.json = json.getJSONObject("attributes");
		this.relationships = json.getJSONObject("attributes").optJSONObject("relationships");
		this.impl = impl;
	}

	@Override
	public PteroAction<Nest> retrieveNest() {
		if (!json.has("relationships")) return impl.retrieveNestById(json.getLong("nest"));

		return new CompletedPteroAction<>(impl.getP4J(), new NestImpl(relationships.getJSONObject("nest"), impl));
	}

	@Override
	public Optional<List<EggVariable>> getVariables() {
		if (!json.has("relationships")) return Optional.empty();
		List<EggVariable> variables = new ArrayList<>();
		JSONObject json = relationships.getJSONObject("variables");
		for (Object o : json.getJSONArray("data")) {
			JSONObject variable = new JSONObject(o.toString());
			variables.add(new ApplicationEggVariableImpl(variable));
		}
		return Optional.of(Collections.unmodifiableList(variables));
	}

	@Override
	public String getAuthor() {
		return json.getString("author");
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
	public String getDescription() {
		return json.optString("description", null);
	}

	@Override
	public String getDockerImage() {
		return json.getString("docker_image");
	}

	@Override
	public String getStopCommand() {
		return json.getString("stop");
	}

	@Override
	public String getStartupCommand() {
		return json.getString("startup");
	}

	@Override
	public Script getInstallScript() {
		return new ScriptImpl(json.getJSONObject("script"));
	}

	@Override
	public Optional<Map<String, EnvironmentValue<?>>> getDefaultVariableMap() {
		if (!getVariables().isPresent()) return Optional.empty();
		Map<String, EnvironmentValue<?>> variableMap = new HashMap<>();
		getVariables().get().forEach(var -> variableMap.put(var.getEnvironmentVariable(), var.getDefaultValue()));
		return Optional.of(Collections.unmodifiableMap(variableMap));
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

	@Override
	public String toString() {
		return json.toString(4);
	}
}
