/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleAction;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleManager;
import com.mattmalec.pterodactyl4j.requests.Route;

public class ScheduleManagerImpl implements ScheduleManager {

	private final ClientServer server;
	private final PteroClientImpl impl;

	public ScheduleManagerImpl(ClientServer server, PteroClientImpl impl) {
		this.server = server;
		this.impl = impl;
	}

	@Override
	public ScheduleAction createSchedule() {
		return new CreateScheduleImpl(server, impl);
	}

	@Override
	public ScheduleAction editSchedule(Schedule schedule) {
		return new EditScheduleImpl(server, schedule, impl);
	}

	@Override
	public PteroAction<Void> delete(Schedule schedule) {
		return PteroActionImpl.onRequestExecute(impl.getP4J(),
				Route.Schedules.DELETE_SCHEDULE.compile(server.getUUID().toString(), schedule.getId()));
	}
}
