package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.application.entities.ISnowflake;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleTaskManager;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface Schedule extends ISnowflake {

	String getName();
	Cron getCron();
	boolean isActive();
	boolean isProcessing();
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
		long getTimeOffset();
		PteroAction<Void> delete();

		enum ScheduleAction {
			POWER,
			COMMAND,
			BACKUP
		}
	}
}
