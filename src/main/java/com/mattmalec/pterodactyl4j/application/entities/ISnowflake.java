package com.mattmalec.pterodactyl4j.application.entities;

import java.time.OffsetDateTime;

public interface ISnowflake {

	default String getId() { return Long.toUnsignedString(getIdLong()); }

	long getIdLong();

	OffsetDateTime getCreationDate();

	OffsetDateTime getUpdatedDate();


}
