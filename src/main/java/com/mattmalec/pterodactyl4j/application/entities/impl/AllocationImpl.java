package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import org.json.JSONObject;

import java.time.OffsetDateTime;

public class AllocationImpl implements Allocation {

	private JSONObject json;

	public AllocationImpl(JSONObject json) {
		this.json = json.getJSONObject("attributes");
	}

	@Override
	public String getIP() {
		return json.getString("ip");
	}

	@Override
	public String getAlias() {
		return json.getString("alias");
	}

	@Override
	public String getPort() {
		return Long.toUnsignedString(json.getLong("port"));
	}

	@Override
	public boolean isAssigned() {
		return json.getBoolean("assigned");
	}

	@Override
	public String getNotes() {
		return json.getString("notes");
	}

	@Override
	public long getIdLong() {
		return json.getLong("id");
	}

	@Override
	public OffsetDateTime getCreationDate() {
		return null;
	}

	@Override
	public OffsetDateTime getUpdatedDate() {
		return null;
	}

	@Override
	public String getFullAddress() {
		return getIP() + ":" + getPort();
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
