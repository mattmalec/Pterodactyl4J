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

package com.mattmalec.pterodactyl4j.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a pterodactyl allocation
 */
public interface Allocation {

	/**
	 * Retrieve the IP of this {@link Allocation}.
	 *
	 * @return Allocation ip
	 */
	@NotNull
	String getIP();

	/**
	 * Retrieve the full (formatted) IP Address of this {@link Allocation}.
	 *
	 * @return Formatted address as <code>ADDRESS:PORT</code>
	 */
	@NotNull
	default String getFullAddress() {
		return String.format("%s:%d", getIP(), getPortInt());
	}

	/**
	 * Retrieve the alias for this {@link Allocation}.
	 *
	 * @return Alias
	 */
	@Nullable
	String getAlias();

	/**
	 * Retrieve notes for this {@link Allocation}
	 *
	 * @return Notes
	 */
	@Nullable
	String getNotes();

	int getPortInt();

	/**
	 * Retrieve the port for this {@link Allocation} as a {@link String}.
	 *
	 * @return Port
	 */
	@NotNull
	default String getPort() { return Integer.toUnsignedString(getPortInt()); }

	long getIdLong();

	/**
	 * Retrieve the id for this {@link Allocation} as a {@link String}.
	 *
	 * @return Id
	 */
	@NotNull
	default String getId() { return Long.toUnsignedString(getIdLong()); }

}
