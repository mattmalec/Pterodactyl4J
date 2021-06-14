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
				.put("minute", minute == null ? cron.getMinute() : minute)
				.put("hour", hour == null ? cron.getHour() : hour)
				.put("day_of_week", dayOfWeek == null ? cron.getDayOfWeek() : dayOfWeek)
				.put("day_of_month", dayOfMonth == null ? cron.getDayOfMonth() : dayOfMonth);
		return getRequestBody(json);
	}
}
