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

package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.utils.Relationed;

import java.util.Optional;

public interface Allocation {

	String getIP();
	String getFullAddress();
	String getAlias();
	String getPort();
	String getNotes();
	@Deprecated
	default long getPortLong() {
		return Long.parseUnsignedLong(getPort());
	}
	default int getPortInt() { return Integer.parseInt(getPort()); }
	boolean isAssigned();
	long getIdLong();
	default String getId() { return Long.toUnsignedString(getIdLong()); }
	Optional<Relationed<ApplicationServer>> getServer();
	Optional<Relationed<Node>> getNode();

}
