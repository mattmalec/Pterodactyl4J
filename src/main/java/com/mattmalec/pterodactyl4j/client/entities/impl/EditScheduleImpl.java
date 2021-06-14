package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractScheduleAction;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class EditScheduleImpl extends AbstractScheduleAction {

	private Schedule schedule;

	public EditScheduleImpl(ClientServer server, Schedule schedule, PteroClientImpl impl) {
		super(impl, server, Route.Schedules.UPDATE_SCHEDULE.compile(server.getUUID().toString(), schedule.getId()));
		this.schedule = schedule;
	}

	@Override
	protected RequestBody finalizeData() {
		JSONObject json = new JSONObject()
				.put("name", name == null ? schedule.getName() : name)
				.put("is_active", active == null ? schedule.isActive() : active)
				.put("minute", minute == null ? ((cron == null || cron.getMinute() == null) ? schedule.getCron().getMinute() : cron.getMinute()) : minute)
				.put("hour", hour == null ? ((cron == null || cron.getHour() == null) ? schedule.getCron().getHour() : cron.getHour()) : hour)
				.put("day_of_week", dayOfWeek == null ? ((cron == null || cron.getDayOfWeek() == null) ? schedule.getCron().getDayOfWeek() : cron.getDayOfWeek()) : dayOfWeek)
				.put("day_of_month", dayOfMonth == null ? ((cron == null || cron.getDayOfMonth() == null) ? schedule.getCron().getDayOfMonth() : cron.getDayOfMonth()) : dayOfMonth);
		return getRequestBody(json);
	}
}
