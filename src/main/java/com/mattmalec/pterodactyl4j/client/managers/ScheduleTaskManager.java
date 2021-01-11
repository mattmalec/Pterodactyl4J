package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

public interface ScheduleTaskManager {

	ScheduleTaskAction createTask();
	ScheduleTaskAction editTask(Schedule.ScheduleTask task);
	PteroAction<Void> deleteTask(Schedule.ScheduleTask task);

}
