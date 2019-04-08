package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.*;
import org.json.JSONObject;

import java.time.OffsetDateTime;

public class ServerImpl implements Server {

	private JSONObject json;

	public ServerImpl(JSONObject json) {
		this.json = json.getJSONObject("attributes");
	}

	@Override
	public String getExternalId() {
		return json.getString("external_id");
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
		return null;
	}

	@Override
	public FeatureLimit getFeatureLimits() {
		return null;
	}

	@Override
	public User getOwner() {
		return null;
	}

	@Override
	public Node getNode() {
		return null;
	}

	@Override
	public Allocation getAllocation() {
		return null;
	}

	@Override
	public Nest getNest() {
		return null;
	}

	@Override
	public Egg getEgg() {
		return null;
	}

	@Override
	public String getPack() {
		return null;
	}

	@Override
	public Container getContainer() {
		return null;
	}

	@Override
	public long getIdLong() {
		return 0;
	}

	@Override
	public OffsetDateTime getCreationDate() {
		return null;
	}

	@Override
	public OffsetDateTime getUpdatedDate() {
		return null;
	}
}
