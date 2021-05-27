package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;

public interface ScheduleManager {

	ScheduleAction createSchedule();
	ScheduleAction editSchedule(Schedule schedule);
	PteroAction<Void> delete(Schedule schedule);

}
