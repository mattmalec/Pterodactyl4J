/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
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

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.Optional;

public class ScheduleTaskImpl implements Schedule.ScheduleTask {

	private final JSONObject json;
	private final Schedule schedule;

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

	@Override
	public String toString() {
		return json.toString(4);
	}
}
