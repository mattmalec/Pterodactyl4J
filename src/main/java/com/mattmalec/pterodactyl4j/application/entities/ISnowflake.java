package com.mattmalec.pterodactyl4j.application.entities;

import java.time.OffsetDateTime;

/**
 * Marks a snowflake entity. Snowflake entities are ones that have an id that uniquely identifies them.
 */
public interface ISnowflake {

	/**
	 * The Snowflake id of this entity. This is unique to every entity and will never change.
	 *
	 * @return Never-null String containing the id.
	 */
	default String getId() { return Long.toUnsignedString(getIdLong()); }

	/**
	 * The Snowflake id of this entity. This is unique to every entity and will never change.
	 *
	 * @return Long containing the id.
	 */
	long getIdLong();

	/**
	 * The time this entity was created.
	 *
	 * @return OffsetDateTime - Time this entity was created at.
	 *
	 * @see    #getUpdatedDate()
	 */
	OffsetDateTime getCreationDate();

	/**
	 * The time this entity was updated.
	 *
	 * @return OffsetDateTime - Time this entity was last updated at.
	 *
	 * @see    #getCreationDate()
	 */
	OffsetDateTime getUpdatedDate();


}
