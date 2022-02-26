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

package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ISnowflake;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleTaskManager;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface Schedule extends ISnowflake {

	String getName();
	Cron getCron();
	boolean isActive();
	boolean isProcessing();
	boolean isOnlyWhenServerIsOnline();
	Optional<OffsetDateTime> getLastRunDate();
	OffsetDateTime getNextRunDate();
	List<ScheduleTask> getTasks();
	ScheduleTaskManager getTaskManager();
	PteroAction<Void> delete();

	interface ScheduleTask extends ISnowflake {
		int getSequenceId();
		ScheduleAction getAction();
		Optional<PowerAction> getPowerPayload();
		String getPayload();
		boolean isQueued();
		boolean isContinueOnFailure();
		long getTimeOffset();
		PteroAction<Void> delete();

		enum ScheduleAction {
			POWER,
			COMMAND,
			BACKUP
		}
	}
}
