package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;

public interface ScheduleTaskManager {

	ScheduleTaskAction createTask();
	ScheduleTaskAction editTask(Schedule.ScheduleTask task);
	PteroAction<Void> deleteTask(Schedule.ScheduleTask task);

}
