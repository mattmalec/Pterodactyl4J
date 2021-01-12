package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.Optional;

public class ScheduleTaskImpl implements Schedule.ScheduleTask {

	private JSONObject json;
	private Schedule schedule;

	public ScheduleTaskImpl(JSONObject json, Schedule schedule) {
		this.json = json.getJSONObject("attributes");
		this.schedule = schedule;
	}

	@Override
	public long getIdLong() {
		return json.getLong("id");
	}

	@Override
	public OffsetDateTime getCreationDate() {
		return OffsetDateTime.parse(json.getString("created_at"));
	}

	@Override
	public OffsetDateTime getUpdatedDate() {
		return OffsetDateTime.parse(json.getString("updated_at"));
	}

	@Override
	public int getSequenceId() {
		return json.getInt("sequence_id");
	}

	@Override
	public ScheduleAction getAction() {
		return ScheduleAction.valueOf(json.getString("action").toUpperCase());
	}

	@Override
	public Optional<PowerAction> getPowerPayload() {
		if(getAction() == ScheduleAction.COMMAND) return Optional.empty();
		return Optional.of(PowerAction.valueOf(json.getString("payload").toUpperCase()));
	}

	@Override
	public String getPayload() {
		return json.getString("payload");
	}

	@Override
	public boolean isQueued() {
		return json.getBoolean("is_queued");
	}

	@Override
	public long getTimeOffset() {
		return json.getLong("time_offset");
	}

	@Override
	public PteroAction<Void> delete() {
		return schedule.getTaskManager().deleteTask(this);
	}
}
