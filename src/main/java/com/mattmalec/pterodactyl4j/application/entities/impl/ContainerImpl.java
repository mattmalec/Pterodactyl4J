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
import com.mattmalec.pterodactyl4j.application.entities.Container;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class ContainerImpl implements Container {

	private final JSONObject json;

	public ContainerImpl(JSONObject json) {
		this.json = json;
	}

	@Override
	public String getStartupCommand() {
		return json.getString("startup_command");
	}

	@Override
	public String getImage() {
		return json.getString("image");
	}

	@Override
	public boolean isInstalled() {
		return json.getInt("installed") != 0;
	}

	@Override
	public Map<String, EnvironmentValue<?>> getEnvironment() {
		JSONObject environment = json.getJSONObject("environment");
		return Collections.unmodifiableMap(environment.keySet().stream()
				.map(s -> new AbstractMap.SimpleImmutableEntry<String, EnvironmentValue<?>>(
						s, EnvironmentValue.of(environment.get(s))))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
	}
}
