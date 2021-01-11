package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.Cron;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.Optional;

public class ScheduleImpl implements Schedule {

	private JSONObject json;

	public ScheduleImpl(JSONObject json) {
		this.json = json.getJSONObject("attributes");
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
	public String getName() {
		return json.getString("name");
	}

	@Override
	public Cron getCron() {
		return new CronImpl(json);
	}

	@Override
	public boolean isActive() {
		return json.getBoolean("is_active");
	}

	@Override
	public boolean isProcessing() {
		return json.getBoolean("is_processing");
	}

	@Override
	public Optional<OffsetDateTime> getLastRunDate() {
		if(json.isNull("last_run_at")) return Optional.empty();
		return Optional.of(OffsetDateTime.parse(json.getString("last_run_at")));
	}

	@Override
	public OffsetDateTime getNextRunDate() {
		return OffsetDateTime.parse(json.getString("next_run_at"));
	}
}
