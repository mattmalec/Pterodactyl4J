package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.SFTP;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketBuilder;
import com.mattmalec.pterodactyl4j.entities.FeatureLimit;
import com.mattmalec.pterodactyl4j.entities.Limit;
import com.mattmalec.pterodactyl4j.entities.impl.FeatureLimitImpl;
import com.mattmalec.pterodactyl4j.entities.impl.LimitImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ClientServerImpl implements ClientServer {

	private JSONObject json;
	private PteroClientImpl impl;

	public ClientServerImpl(JSONObject json, PteroClientImpl impl) {
		this.json = json.getJSONObject("attributes");
		this.impl = impl;
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
	public long getInternalIdLong() {
		return json.getLong("internal_id");
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
	public SFTP getSFTPDetails() {
		return new SFTPImpl(json.getJSONObject("sftp_details"));
	}

	@Override
	public String getInvocation() {
		return json.getString("invocation");
	}

	@Override
	public Set<String> getEggFeatures() {
		JSONArray features = json.getJSONArray("egg_features");
		HashSet<String> eggFeatures = new HashSet<>();
		features.forEach(o -> eggFeatures.add(o.toString()));
		return Collections.unmodifiableSet(eggFeatures);
	}

	@Override
	public String getNode() {
		return json.getString("node");
	}

	@Override
	public boolean isSuspended() {
		return json.getBoolean("is_suspended");
	}

	@Override
	public boolean isInstalling() {
		return json.getBoolean("is_installing");
	}

	@Override
	public WebSocketBuilder getWebSocketBuilder() {
		return new WebSocketBuilder(impl, this);
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
