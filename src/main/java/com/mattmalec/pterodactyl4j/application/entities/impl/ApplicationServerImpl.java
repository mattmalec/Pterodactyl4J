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
import com.mattmalec.pterodactyl4j.ServerStatus;
import com.mattmalec.pterodactyl4j.application.entities.*;
import com.mattmalec.pterodactyl4j.application.managers.*;
import com.mattmalec.pterodactyl4j.entities.FeatureLimit;
import com.mattmalec.pterodactyl4j.entities.Limit;
import com.mattmalec.pterodactyl4j.entities.impl.FeatureLimitImpl;
import com.mattmalec.pterodactyl4j.entities.impl.LimitImpl;
import com.mattmalec.pterodactyl4j.requests.CompletedPteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.*;

public class ApplicationServerImpl implements ApplicationServer {

	private final PteroApplicationImpl impl;
	private final JSONObject json;
	private final JSONObject relationships;

	public ApplicationServerImpl(PteroApplicationImpl impl, JSONObject json) {
		this.impl = impl;
		this.json = json.getJSONObject("attributes");
		this.relationships = json.getJSONObject("attributes").optJSONObject("relationships");
	}

	@Override
	public String getExternalId() {
		return json.optString("external_id");
	}

	@Override
	public UUID getUUID() {
		return UUID.fromString(json.getString("uuid"));
	}

	@Override
	public String getIdentifier() {
		return json.getString("identifier");
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
	public boolean isSuspended() {
		return json.getBoolean("suspended");
	}

	@Override
	public Limit getLimits() {
		return new LimitImpl(json.getJSONObject("limits"));
	}

	@Override
	public FeatureLimit getFeatureLimits() {
		return new FeatureLimitImpl(json.getJSONObject("feature_limits"));
	}

	@Override
	public PteroAction<ApplicationUser> retrieveOwner() {
		if(!json.has("relationships"))
			return impl.retrieveUserById(getOwnerIdLong());

		return new CompletedPteroAction<>(impl.getP4J(), new ApplicationUserImpl(relationships.getJSONObject("user"), impl));
	}

	@Override
	public long getOwnerIdLong() {
		return json.getLong("user");
	}

	@Override
	public PteroAction<Node> retrieveNode() {
		if (!json.has("relationships"))
			return impl.retrieveNodeById(getNodeIdLong());

		return new CompletedPteroAction<>(impl.getP4J(), new NodeImpl(relationships.getJSONObject("node"), impl));
	}

	@Override
	public long getNodeIdLong() {
		return json.getLong("node");
	}

	@Override
	public Optional<List<ApplicationAllocation>> getAllocations() {
		if(!json.has("relationships")) return Optional.empty();
		List<ApplicationAllocation> allocations = new ArrayList<>();
		JSONObject json = relationships.getJSONObject("allocations");
		for(Object o : json.getJSONArray("data")) {
			JSONObject allocation = new JSONObject(o.toString());
			allocations.add(new ApplicationAllocationImpl(allocation, impl));
		}
		return Optional.of(Collections.unmodifiableList(allocations));
	}

	@Override
	public PteroAction<ApplicationAllocation> retrieveDefaultAllocation() {
		if (!json.has("relationships"))
			return impl.retrieveAllocationById(getDefaultAllocationIdLong());

		Optional<ApplicationAllocation> defaultAllocation = getAllocations().get().stream()
				.filter(a -> a.getIdLong() == getDefaultAllocationIdLong()).findFirst();

		return defaultAllocation.map(allocation -> new CompletedPteroAction<>(impl.getP4J(), allocation))
				.orElse(new CompletedPteroAction<>(impl.getP4J(), new AssertionError("Unreachable")));
	}

	@Override
	public long getDefaultAllocationIdLong() {
		return json.getLong("allocation");
	}

	@Override
	public PteroAction<Nest> retrieveNest() {
		if (!json.has("relationships"))
			return impl.retrieveNestById(getNestIdLong());

		return new CompletedPteroAction<>(impl.getP4J(), new NestImpl(relationships.getJSONObject("nest"), impl));
	}

	@Override
	public long getNestIdLong() {
		return json.getLong("nest");
	}

	@Override
	public PteroAction<ApplicationEgg> retrieveEgg() {
		if (!json.has("relationships"))
			return impl.retrieveEggById(getNestId(), getEggId());

		return new CompletedPteroAction<>(impl.getP4J(), new ApplicationEggImpl(relationships.getJSONObject("egg"), impl));
	}

	@Override
	public long getEggIdLong() {
		return json.getLong("egg");
	}

	@Override
	public ServerStatus getStatus() {
		if (json.isNull("status"))
			return ServerStatus.UNKNOWN;
		return ServerStatus.valueOf(json.getString("status").toUpperCase());
	}

	@Override
	public ServerDetailManager getDetailManager() {
		return new ServerDetailManagerImpl(this, impl);
	}

	@Override
	public ServerBuildManager getBuildManager() {
		return new ServerBuildManagerImpl(this, impl);
	}

	@Override
	public ServerStartupManager getStartupManager() {
		return new ServerStartupManagerImpl(this, impl);
	}

	@Override
	public ServerManager getManager() {
		return new ServerManager(this);
	}

	@Override
	public ServerController getController() {
		return new ServerController(this, impl);
	}

	@Override
	public PteroAction<List<ApplicationDatabase>> retrieveDatabases() {
		if (!json.has("relationships"))
			return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Databases.LIST_DATABASES.compile(getId()),
					(response, request) -> handleDatabases(response.getObject()));

		return new CompletedPteroAction<>(impl.getP4J(), handleDatabases(relationships.getJSONObject("databases")));
	}

	private List<ApplicationDatabase> handleDatabases(JSONObject json) {
		List<ApplicationDatabase> databases = new ArrayList<>();
		for (Object o : json.getJSONArray("data")) {
			JSONObject database = new JSONObject(o.toString());
			databases.add(new ApplicationDatabaseImpl(database, this, impl));
		}
		return Collections.unmodifiableList(databases);
	}

	@Override
	public PteroAction<ApplicationDatabase> retrieveDatabaseById(String id) {
		return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Databases.GET_DATABASE.compile(getId(), id),
				(response, request) -> new ApplicationDatabaseImpl(response.getObject(), this, impl));
	}

	@Override
	public ApplicationDatabaseManager getDatabaseManager() {
		return new ApplicationDatabaseManagerImpl(this, impl);
	}

	@Override
	public Container getContainer() {
		return new ContainerImpl(json.getJSONObject("container"));
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
