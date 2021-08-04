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
import com.mattmalec.pterodactyl4j.application.managers.ServerController;
import com.mattmalec.pterodactyl4j.application.managers.ServerManager;
import com.mattmalec.pterodactyl4j.entities.FeatureLimit;
import com.mattmalec.pterodactyl4j.entities.Limit;
import com.mattmalec.pterodactyl4j.entities.impl.FeatureLimitImpl;
import com.mattmalec.pterodactyl4j.entities.impl.LimitImpl;
import com.mattmalec.pterodactyl4j.utils.Relationed;
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
	public Relationed<ApplicationUser> getOwner() {
		return new Relationed<ApplicationUser>() {
			@Override
			public PteroAction<ApplicationUser> retrieve() {
				return impl.retrieveUserById(json.getLong("user"));
			}

			@Override
			public Optional<ApplicationUser> get() {
				if(!json.has("relationships")) return Optional.empty();
				return Optional.of(new ApplicationUserImpl(relationships.getJSONObject("user"), impl));
			}
		};
	}

	@Override
	public Relationed<Node> getNode() {
		return new Relationed<Node>() {
			@Override
			public PteroAction<Node> retrieve() {
				return impl.retrieveNodeById(json.getLong("node"));
			}

			@Override
			public Optional<Node> get() {
				if(!json.has("relationships")) return Optional.empty();
				return Optional.of(new NodeImpl(relationships.getJSONObject("node"), impl));
			}
		};
	}

	@Override
	public Optional<List<Allocation>> getAllocations() {
		if(!json.has("relationships")) return Optional.empty();
		List<Allocation> allocations = new ArrayList<>();
		JSONObject json = relationships.getJSONObject("allocations");
		for(Object o : json.getJSONArray("data")) {
			JSONObject allocation = new JSONObject(o.toString());
			allocations.add(new AllocationImpl(allocation, impl));
		}
		return Optional.of(Collections.unmodifiableList(allocations));
	}

	@Override
	public Relationed<Allocation> getDefaultAllocation() {
		return new Relationed<Allocation>() {
			@Override
			public PteroAction<Allocation> retrieve() {
				return impl.retrieveAllocationById(json.getLong("allocation"));
			}

			@Override
			public Optional<Allocation> get() {
				if(!json.has("relationships")) return Optional.empty();
				List<Allocation> allocations = getAllocations().get();
				for (Allocation a : allocations) {
					if (a.getIdLong() == json.getLong("allocation")) {
						return Optional.of(a);
					}
				}
				return Optional.empty();
			}
		};
	}

	@Override
	public Relationed<Nest> getNest() {
		return new Relationed<Nest>() {
			@Override
			public PteroAction<Nest> retrieve() {
				return impl.retrieveNestById(json.getLong("nest"));
			}

			@Override
			public Optional<Nest> get() {
				if(!json.has("relationships")) return Optional.empty();
				return Optional.of(new NestImpl(relationships.getJSONObject("nest"), impl));
			}
		};
	}

	@Override
	public Relationed<ApplicationEgg> getEgg() {
		return new Relationed<ApplicationEgg>() {
			@Override
			public PteroAction<ApplicationEgg> retrieve() {
				return impl.retrieveEggById(getNest().retrieve().execute(), json.getLong("egg"));
			}

			@Override
			public Optional<ApplicationEgg> get() {
				if(!json.has("relationships")) return Optional.empty();
				return Optional.of(new ApplicationEggImpl(relationships.getJSONObject("egg"), impl));
			}
		};
	}

	@Override
	public ServerStatus getStatus() {
		if (json.isNull("status"))
			return ServerStatus.UNKNOWN;
		return ServerStatus.valueOf(json.getString("status").toLowerCase());
	}

	@Override
	public ServerManager getManager() {
		return new ServerManager(this, impl);
	}

	@Override
	public ServerController getController() {
		return new ServerController(this, impl);
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
