package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractScheduleTaskAction;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class EditScheduleTaskImpl extends AbstractScheduleTaskAction {

	public EditScheduleTaskImpl(ClientServer server, Schedule schedule, Schedule.ScheduleTask task, PteroClientImpl impl) {
		super(impl, schedule, Route.Schedules.UPDATE_TASK.compile(server.getUUID().toString(), schedule.getId(), task.getId()));
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
