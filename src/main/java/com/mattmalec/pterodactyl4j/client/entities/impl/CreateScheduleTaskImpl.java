package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleTaskAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

public class CreateScheduleTaskImpl implements ScheduleTaskAction {

	private ClientServer server;
	private Schedule schedule;
	private PteroClientImpl impl;

	private Schedule.ScheduleTask.ScheduleAction action;
	private String payload;
	private String timeOffset;


	public CreateScheduleTaskImpl(ClientServer server, Schedule schedule, PteroClientImpl impl) {
		this.server = server;
		this.schedule = schedule;
		this.impl = impl;
	}

	@Override
	public ScheduleTaskAction setAction(Schedule.ScheduleTask.ScheduleAction action) {
		this.action = action;
		return this;
	}

	@Override
	public ScheduleTaskAction setPowerPayload(PowerAction payload) {
		this.payload = payload.name().toLowerCase();
		return this;
	}

	@Override
	public ScheduleTaskAction setPayload(String payload) {
		this.payload = payload;
		return this;
	}

	@Override
	public ScheduleTaskAction setTimeOffset(String seconds) {
		this.timeOffset = seconds;
		return this;
	}

	@Override
	public PteroAction<Schedule.ScheduleTask> build() {
		return PteroActionImpl.onExecute(() -> {
			JSONObject json = new JSONObject()
					.put("action", action.name().toLowerCase())
					.put("payload", payload)
					.put("time_offset", timeOffset);
			Route.CompiledRoute route = Route.Schedules.CREATE_TASK.compile(server.getUUID().toString(), schedule.getId()).withJSONdata(json);
			JSONObject obj = impl.getRequester().request(route).toJSONObject();
			return new ScheduleTaskImpl(obj, schedule);
		});
	}
}
