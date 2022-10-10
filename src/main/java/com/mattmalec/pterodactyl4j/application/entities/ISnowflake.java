/*
 *    Copyright 2021-2022 Matt Malec, and the Pterodactyl4J contributors
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
	default String getId() {
		return Long.toUnsignedString(getIdLong());
	}

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
