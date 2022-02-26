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
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractScheduleAction;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class CreateScheduleImpl extends AbstractScheduleAction {

	public CreateScheduleImpl(ClientServer server, PteroClientImpl impl) {
		super(impl, server, Route.Schedules.CREATE_SCHEDULE.compile(server.getUUID().toString()));
	}

	@Override
	protected RequestBody finalizeData() {
		Checks.notBlank(this.name, "Name");
		JSONObject json = new JSONObject()
				.put("name", name)
				.put("is_active", active)
				.put("only_when_online", whenServerIsOnline != null)
				.put("minute", minute == null ? cron.getMinute() : minute)
				.put("hour", hour == null ? cron.getHour() : hour)
				.put("day_of_week", dayOfWeek == null ? cron.getDayOfWeek() : dayOfWeek)
				.put("month", month == null ? cron.getMonth() : month)
				.put("day_of_month", dayOfMonth == null ? cron.getDayOfMonth() : dayOfMonth);
		return getRequestBody(json);
	}
}
