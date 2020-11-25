package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.*;
import com.mattmalec.pterodactyl4j.application.managers.ServerController;
import com.mattmalec.pterodactyl4j.application.managers.ServerManager;
import com.mattmalec.pterodactyl4j.entities.FeatureLimit;
import com.mattmalec.pterodactyl4j.entities.Limit;
import com.mattmalec.pterodactyl4j.entities.impl.FeatureLimitImpl;
import com.mattmalec.pterodactyl4j.entities.impl.LimitImpl;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	public ApplicationUser getOwner() {
		return new ApplicationUserImpl(relationships.getJSONObject("user"), impl.getRequester());
	}

	@Override
	public Node getNode() {
		return new NodeImpl(relationships.getJSONObject("node"), impl);
	}

	@Override
	public List<Allocation> getAllocations() {
		List<Allocation> allocations = new ArrayList<>();
		JSONObject json = relationships.getJSONObject("allocations");
		for(Object o : json.getJSONArray("data")) {
			JSONObject allocation = new JSONObject(o.toString());
			allocations.add(new AllocationImpl(allocation));
		}
		return Collections.unmodifiableList(allocations);
	}

	@Override
	public Allocation getDefaultAllocation() {
		List<Allocation> allocations = getAllocations();
		for(Allocation a : allocations) {
			if(a.getIdLong() == json.getLong("allocation")) {
				return a;
			}
		}
		return null;
	}

	@Override
	public Nest getNest() {
		return new NestImpl(relationships.getJSONObject("nest"), impl);
	}

	@Override
	public Egg getEgg() {
		return new EggImpl(relationships.getJSONObject("egg"), impl);
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
