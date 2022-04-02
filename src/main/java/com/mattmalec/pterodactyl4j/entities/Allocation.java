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

import com.mattmalec.pterodactyl4j.client.entities.ClientAllocation;

/**
 * Represents a Pterodactyl {@link com.mattmalec.pterodactyl4j.entities.Allocation Allocation}.
 * This should contain all information provided from the Pterodactyl instance about an Allocation.
 */
public interface Allocation {

	/**
	 * The IP address of this Allocation
	 *
	 * @return Never-null String containing the IP address of this Allocation
	 */
	String getIP();

	/**
	 * The socket address/endpoint for this Allocation
	 * <p>This is the equivalent of calling
	 * {@link java.lang.String#format(String, Object...) String.format}("%s:%d", allocation.getIP(), allocation.getPortInt())
	 *
	 * @return Never-null String containing the socket address for this Allocation, for example 192.168.0.1:3000
	 */
	default String getFullAddress() {
		return getIP() + ":" + getPortInt();
	}

	/**
	 * The alias for this Allocation
	 *
	 * @return Possibly-null String containing the allocation's alias
	 */
	String getAlias();

	/**
	 * The notes for this Allocation
	 * <br>This is assigned by a user having control of the {@link com.mattmalec.pterodactyl4j.client.entities.ClientServer ClientServer}
	 * currently utilizing the Allocation
	 *
	 * <p>This value can be modified using {@link ClientAllocation#setNote(String) ClientAllocation.setNote(String)}.
	 *
	 * @return Possibly-null String containing the allocation's notes
	 */
	String getNotes();

	/**
	 * The port used for connection with this Allocation
	 *
	 * @return The port
	 */
	int getPortInt();

	/**
	 * The port used for connection with this Allocation
	 *
	 * @return Never-null String containing the port
	 */
	default String getPort() { return Integer.toUnsignedString(getPortInt()); }

	/**
	 * The id of this Allocation. This is unique to every Allocation and will never change.
	 *
	 * @return Long containing the id.
	 */
	long getIdLong();

	/**
	 * The id of this Allocation. This is unique to every Allocation and will never change.
	 *
	 * @return Never-null String containing the id.
	 */
	default String getId() { return Long.toUnsignedString(getIdLong()); }

}
