package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

public interface ScheduleTaskAction {

	ScheduleTaskAction setAction(Schedule.ScheduleTask.ScheduleAction action);
	ScheduleTaskAction setPowerPayload(PowerAction payload);
	ScheduleTaskAction setPayload(String payload);
	ScheduleTaskAction setTimeOffset(int seconds);
	default ScheduleTaskAction setTimeOffset(String seconds) {
		return setTimeOffset(Integer.parseUnsignedInt(seconds));
	}
	PteroAction<Schedule.ScheduleTask> build();

}
