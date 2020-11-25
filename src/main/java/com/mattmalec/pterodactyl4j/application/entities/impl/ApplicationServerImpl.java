package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.*;
import com.mattmalec.pterodactyl4j.application.managers.ServerController;
import com.mattmalec.pterodactyl4j.application.managers.ServerManager;
import com.mattmalec.pterodactyl4j.entities.FeatureLimit;
import com.mattmalec.pterodactyl4j.entities.Limit;
import com.mattmalec.pterodactyl4j.entities.impl.FeatureLimitImpl;
import com.mattmalec.pterodactyl4j.entities.impl.LimitImpl;
import org.json.JSONObject;

import java.time.OffsetDateTime;

public class ApplicationServerImpl implements ApplicationServer {

	private PteroApplicationImpl impl;
	private JSONObject json;
	private JSONObject relationships;

	public ApplicationServerImpl(PteroApplicationImpl impl, JSONObject json) {
		this.impl = impl;
		this.json = json.getJSONObject("attributes");
		this.relationships = json.getJSONObject("attributes").getJSONObject("relationships");
	}

	@Override
	public String getExternalId() {
		return json.optString("external_id");
	}

	@Override
	public String getUUID() {
		return json.getString("uuid");
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
		return new PteroAction<ApplicationUser>() {
			@Override
			public ApplicationUser execute() {
				return impl.retrieveUserById(json.getLong("user")).execute();
			}
		};
	}

	@Override
	public PteroAction<Node> retrieveNode() {
		return new PteroAction<Node>() {
			@Override
			public Node execute() {
				return impl.retrieveNodeById(json.getLong("node")).execute();
			}
		};
	}

	@Override
	public PteroAction<Allocation> retrieveAllocation() {
		return new PteroAction<Allocation>() {
			@Override
			public Allocation execute() {
				return impl.retrieveAllocationById(json.getLong("allocation")).execute();
			}
		};
	}

	@Override
	public PteroAction<Nest> retrieveNest() {
		return new PteroAction<Nest>() {
			@Override
			public Nest execute() {
				return impl.retrieveNestById(json.getLong("nest")).execute();
			}
		};
	}

	@Override
	public PteroAction<Egg> retrieveEgg() {
		return new PteroAction<Egg>() {
			@Override
			public Egg execute() {

				return impl.retrieveEggById(retrieveNest().execute(), json.getLong("egg")).execute();
			}
		};
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
	public long getPack() {
		return json.getLong("pack");
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