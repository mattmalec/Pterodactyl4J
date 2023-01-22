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

import com.mattmalec.pterodactyl4j.application.entities.ApplicationAllocation;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import java.util.Optional;
import org.json.JSONObject;

public class ApplicationAllocationImpl implements ApplicationAllocation {

	private final JSONObject json;
	private final JSONObject relationships;

	private final PteroApplicationImpl impl;

	public ApplicationAllocationImpl(JSONObject json, PteroApplicationImpl impl) {
		this.json = json.getJSONObject("attributes");
		this.relationships = json.getJSONObject("attributes").optJSONObject("relationships");
		this.impl = impl;
	}

	@Override
	public String getIP() {
		return json.getString("ip");
	}

	@Override
	public String getAlias() {
		return json.optString("alias", null);
	}

	@Override
	public int getPortInt() {
		return json.getInt("port");
	}

	@Override
	public boolean isAssigned() {
		return json.getBoolean("assigned");
	}

	@Override
	public String getNotes() {
		return json.optString("notes", null);
	}

	@Override
	public long getIdLong() {
		return json.getLong("id");
	}

	@Override
	public Optional<ApplicationServer> getServer() {
		if (!isAssigned() || !json.has("relationships")) return Optional.empty();
		return Optional.of(new ApplicationServerImpl(impl, relationships.getJSONObject("server")));
	}

	@Override
	public Optional<Node> getNode() {
		if (!json.has("relationships")) return Optional.empty();
		return Optional.of(new NodeImpl(relationships.getJSONObject("node"), impl));
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
