/*
 *    Copyright 2021-2022 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Cron;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleTaskManager;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;

public class ScheduleImpl implements Schedule {

	private final JSONObject json;
	private final JSONObject tasks;

	private final ClientServer server;
	private final PteroClientImpl impl;

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
	public boolean isOnlyWhenServerIsOnline() {
		return json.getBoolean("only_when_online");
	}

	@Override
	public Optional<OffsetDateTime> getLastRunDate() {
		if (json.isNull("last_run_at")) return Optional.empty();
		return Optional.of(OffsetDateTime.parse(json.getString("last_run_at")));
	}

	@Override
	public OffsetDateTime getNextRunDate() {
		return OffsetDateTime.parse(json.getString("next_run_at"));
	}

	@Override
	public List<ScheduleTask> getTasks() {
		List<ScheduleTask> tasks = new ArrayList<>();
		for (Object o : this.tasks.getJSONArray("data")) {
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
