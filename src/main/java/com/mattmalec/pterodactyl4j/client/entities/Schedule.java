package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.application.entities.ISnowflake;

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

	interface ScheduleTask extends ISnowflake {
		int getSequenceId();
		ScheduleAction getAction();
		Optional<PowerAction> getPowerPayload();
		String getPayload();
		boolean isQueued();
		long getTimeOffset();

		enum ScheduleAction {
			POWER,
			COMMAND
		}
	}
}
