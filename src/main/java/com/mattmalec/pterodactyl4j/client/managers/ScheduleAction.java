package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.Cron;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;

public interface ScheduleAction extends PteroAction<Schedule> {

	ScheduleAction setName(String name);
	ScheduleAction setActive(boolean active);
	ScheduleAction setCron(Cron cron);
	ScheduleAction setCronExpression(String expression);
	ScheduleAction setMinute(String minute);
	ScheduleAction setHour(String hour);
	ScheduleAction setDayOfWeek(String dayOfWeek);
	ScheduleAction setDayOfMonth(String dayOfMonth);

}
