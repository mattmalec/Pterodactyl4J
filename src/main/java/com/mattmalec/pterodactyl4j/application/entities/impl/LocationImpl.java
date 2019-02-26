package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LocationImpl implements Location {

	private JSONObject json;
	private PteroApplicationImpl impl;

	public LocationImpl(JSONObject json, PteroApplicationImpl impl) {
		this.json = json.getJSONObject("attributes");
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
	public List<Node> getNodes() {
		return null;
	}

	@Override
	public long getIdLong() {
		return json.getLong("id");
	}

	@Override
	public OffsetDateTime getCreationDate() {
		return LocalDateTime.parse(json.optString("created_at"), DateTimeFormatter.ISO_LOCAL_DATE_TIME).atOffset(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
	}

	@Override
	public OffsetDateTime getUpdatedDate() {
		return LocalDateTime.parse(json.optString("updated_at"), DateTimeFormatter.ISO_LOCAL_DATE_TIME).atOffset(ZoneId.systemDefault().getRules().getOffset(Instant.now()));

	}
}
