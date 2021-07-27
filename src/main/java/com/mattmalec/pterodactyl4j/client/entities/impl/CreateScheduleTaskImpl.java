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

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractScheduleTaskAction;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class CreateScheduleTaskImpl extends AbstractScheduleTaskAction {

	public CreateScheduleTaskImpl(ClientServer server, Schedule schedule, PteroClientImpl impl) {
		super(impl, schedule, Route.Schedules.CREATE_TASK.compile(server.getUUID().toString(), schedule.getId()));
	}

	@Override
	protected RequestBody finalizeData() {
		JSONObject json = new JSONObject()
				.put("action", action.name().toLowerCase())
				.put("payload", payload)
				.put("time_offset", timeOffset == null ? "0" : timeOffset);
		return getRequestBody(json);
	}
}
