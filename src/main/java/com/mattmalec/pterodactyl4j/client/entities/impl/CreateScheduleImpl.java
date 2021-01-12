package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Cron;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import com.mattmalec.pterodactyl4j.utils.CronUtils;
import org.json.JSONObject;

public class CreateScheduleImpl implements ScheduleAction {

	private ClientServer server;
	private PteroClientImpl impl;

	private String name;
	private boolean active;
	private Cron cron;
	private String minute;
	private String hour;
	private String dayOfWeek;
	private String dayOfMonth;

	public CreateScheduleImpl(ClientServer server, PteroClientImpl impl) {
		this.server = server;
		this.impl = impl;
	}
	@Override
	public ScheduleAction setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public ScheduleAction setActive(boolean active) {
		this.active = active;
		return this;
	}

	@Override
	public ScheduleAction setCron(Cron cron) {
		this.cron = cron;
		return this;
	}

	@Override
	public ScheduleAction setCronExpression(String expression) {
		this.cron = CronUtils.ofExpression(expression);
		return this;
	}

	@Override
	public ScheduleAction setMinute(String minute) {
		this.minute = minute;
		return this;
	}

	@Override
	public ScheduleAction setHour(String hour) {
		this.hour = hour;
		return this;
	}

	@Override
	public ScheduleAction setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
		return this;
	}

	@Override
	public ScheduleAction setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
		return this;
	}

	@Override
	public PteroAction<Schedule> build() {
		Checks.notBlank(this.name, "Name");
		JSONObject json = new JSONObject()
				.put("name", name)
				.put("is_active", active)
				.put("minute", minute == null ? cron.getMinute() : minute)
				.put("hour", hour == null ? cron.getHour() : hour)
				.put("day_of_week", dayOfWeek == null ? cron.getDayOfWeek() : dayOfWeek)
				.put("day_of_month", dayOfMonth == null ? cron.getDayOfMonth() : dayOfMonth);
		return PteroActionImpl.onExecute(() -> {
			Route.CompiledRoute route = Route.Schedules.CREATE_SCHEDULE.compile(server.getUUID().toString()).withJSONdata(json);
			JSONObject obj = impl.getRequester().request(route).toJSONObject();
			return new ScheduleImpl(obj, server, impl);
		});
	}
}
