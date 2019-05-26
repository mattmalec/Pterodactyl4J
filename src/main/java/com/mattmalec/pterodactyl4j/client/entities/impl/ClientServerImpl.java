package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.entities.FeatureLimit;
import com.mattmalec.pterodactyl4j.entities.Limit;
import com.mattmalec.pterodactyl4j.entities.impl.FeatureLimitImpl;
import com.mattmalec.pterodactyl4j.entities.impl.LimitImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import org.json.JSONObject;

public class ClientServerImpl implements ClientServer {

	private JSONObject json;

	public ClientServerImpl(JSONObject json) {
		this.json = json.getJSONObject("attributes");
	}

	@Override
	public boolean isServerOwner() {
		return json.getBoolean("server_owner");
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
	public Limit getLimits() {
		return new LimitImpl(json.getJSONObject("limits"));
	}

	@Override
	public FeatureLimit getFeatureLimits() {
		return new FeatureLimitImpl(json.getJSONObject("feature_limits"));
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
