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

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.requests.CompletedPteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationImpl implements Location {

	private final JSONObject json;
	private final JSONObject relationships;
	private final PteroApplicationImpl impl;

	public LocationImpl(JSONObject json, PteroApplicationImpl impl) {
		this.json = json.getJSONObject("attributes");
		this.relationships = json.getJSONObject("attributes").optJSONObject("relationships");
		this.impl = impl;
	}

	@Override
	public String getShortCode() {
		return json.getString("short");
	}

	@Override
	public String getDescription() {
		return json.getString("long");
	}

	@Override
	public PteroAction<List<Node>> getNodes() {
		if (!json.has("relationships"))
			return impl.retrieveNodesByLocation(this);

		List<Node> nodes = new ArrayList<>();
		JSONObject json = relationships.getJSONObject("nodes");
		for(Object o : json.getJSONArray("data")) {
			JSONObject node = new JSONObject(o.toString());
			nodes.add(new NodeImpl(node, impl));
		}
		return new CompletedPteroAction<>(impl.getP4J(), Collections.unmodifiableList(nodes));
	}

	@Override
	public PteroAction<List<ApplicationServer>> getServers() {
		if (!json.has("relationships"))
			return impl.retrieveServersByLocation(this);

		List<ApplicationServer> servers = new ArrayList<>();
		JSONObject json = relationships.getJSONObject("servers");
		for(Object o : json.getJSONArray("data")) {
			JSONObject server = new JSONObject(o.toString());
			servers.add(new ApplicationServerImpl(impl, server));
		}
		return new CompletedPteroAction<>(impl.getP4J(), Collections.unmodifiableList(servers));
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
	public LocationAction edit() {
		return new EditLocationImpl(this, impl);
	}

	@Override
	public PteroAction<Void> delete() {
		return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Locations.DELETE_LOCATION.compile(getId()));
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
