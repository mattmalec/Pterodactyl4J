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

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractScheduleAction;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class EditScheduleImpl extends AbstractScheduleAction {

	private final Schedule schedule;

	public EditScheduleImpl(ClientServer server, Schedule schedule, PteroClientImpl impl) {
		super(
				impl,
				server,
				Route.Schedules.UPDATE_SCHEDULE.compile(server.getUUID().toString(), schedule.getId()));
		this.schedule = schedule;
	}

	@Override
	protected RequestBody finalizeData() {
		JSONObject json = new JSONObject()
				.put("name", name == null ? schedule.getName() : name)
				.put("is_active", active == null ? schedule.isActive() : active)
				.put(
						"only_when_online",
						whenServerIsOnline == null ? schedule.isOnlyWhenServerIsOnline() : whenServerIsOnline)
				.put(
						"minute",
						minute == null
								? ((cron == null || cron.getMinute() == null)
										? schedule.getCron().getMinute()
										: cron.getMinute())
								: minute)
				.put(
						"hour",
						hour == null
								? ((cron == null || cron.getHour() == null)
										? schedule.getCron().getHour()
										: cron.getHour())
								: hour)
				.put(
						"day_of_week",
						dayOfWeek == null
								? ((cron == null || cron.getDayOfWeek() == null)
										? schedule.getCron().getDayOfWeek()
										: cron.getDayOfWeek())
								: dayOfWeek)
				.put(
						"day_of_month",
						dayOfMonth == null
								? ((cron == null || cron.getDayOfMonth() == null)
										? schedule.getCron().getDayOfMonth()
										: cron.getDayOfMonth())
								: dayOfMonth)
				.put(
						"month",
						month == null
								? ((cron == null || cron.getMonth() == null)
										? schedule.getCron().getMonth()
										: cron.getMonth())
								: month);

		return getRequestBody(json);
	}
}
