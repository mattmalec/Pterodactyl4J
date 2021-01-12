package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Cron;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleTaskManager;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ScheduleImpl implements Schedule {

	private JSONObject json;
	private JSONObject tasks;

	private ClientServer server;
	private PteroClientImpl impl;

	public ScheduleImpl(JSONObject json, ClientServer server, PteroClientImpl impl) {
		this.json = json.getJSONObject("attributes");
		this.tasks = this.json.getJSONObject("relationships").getJSONObject("tasks");
		this.server = server;
		this.impl = impl;
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

	@Override
	public List<ScheduleTask> getTasks() {
		List<ScheduleTask> tasks = new ArrayList<>();
		for(Object o : this.tasks.getJSONArray("data")) {
			JSONObject task = new JSONObject(o.toString());
			tasks.add(new ScheduleTaskImpl(task, this));
		}
		return Collections.unmodifiableList(tasks);
	}

	@Override
	public ScheduleTaskManager getTaskManager() {
		return new ScheduleTaskManagerImpl(server, this, impl);
	}

	@Override
	public PteroAction<Void> delete() {
		return server.getScheduleManager().delete(this);
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
