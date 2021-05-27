package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleTaskAction;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleTaskManager;
import com.mattmalec.pterodactyl4j.requests.Route;

public class ScheduleTaskManagerImpl implements ScheduleTaskManager {

	private ClientServer server;
	private Schedule schedule;
	private PteroClientImpl impl;

	public ScheduleTaskManagerImpl(ClientServer server, Schedule schedule, PteroClientImpl impl) {
		this.server = server;
		this.schedule = schedule;
		this.impl = impl;
	}

	@Override
	public ScheduleTaskAction createTask() {
		return new CreateScheduleTaskImpl(server, schedule, impl);
	}

	@Override
	public ScheduleTaskAction editTask(Schedule.ScheduleTask task) {
		return new EditScheduleTaskImpl(server, schedule, task, impl);
	}

	@Override
	public PteroAction<Void> deleteTask(Schedule.ScheduleTask task) {
		return PteroActionImpl.onRequestExecute(impl.getPteroApi(),
				Route.Schedules.DELETE_TASK.compile(server.getUUID().toString(), schedule.getId(), task.getId()));
	}
}
