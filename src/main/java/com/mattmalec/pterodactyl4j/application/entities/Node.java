/*
 *    Copyright 2021-2024 Matt Malec, and the Pterodactyl4J contributors
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

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.managers.ApplicationAllocationManager;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;
import com.mattmalec.pterodactyl4j.requests.PaginationAction;
import com.mattmalec.pterodactyl4j.utils.StreamUtils;
import java.util.List;
import java.util.UUID;

public interface Node extends ISnowflake {

	boolean isPublic();

	String getName();

	String getDescription();

	PteroAction<Location> retrieveLocation();

	ApplicationAllocationManager getAllocationManager();

	String getFQDN();

	String getScheme();

	boolean isBehindProxy();

	boolean isInMaintenanceMode();

	/**
	 * @return {@code true} if the node is in maintenance mode
	 *
	 * @deprecated For spelling mistake only. Use {@link #isInMaintenanceMode()} instead
	 */
	@Deprecated
	default boolean hasMaintanceMode() {
		return isInMaintenanceMode();
	}

	default String getMemory() {
		return Long.toUnsignedString(getMemoryLong());
	}

	long getMemoryLong();

	default String getMemoryOverallocate() {
		return Long.toUnsignedString(getMemoryOverallocateLong());
	}

	long getMemoryOverallocateLong();

	default String getDisk() {
		return Long.toUnsignedString(getDiskLong());
	}

	long getDiskLong();

	default String getDiskOverallocate() {
		return Long.toUnsignedString(getDiskOverallocateLong());
	}

	long getDiskOverallocateLong();

	default String getUploadLimit() {
		return Long.toUnsignedString(getUploadLimitLong());
	}

	long getUploadLimitLong();

	long getAllocatedMemoryLong();

	default String getAllocatedMemory() {
		return Long.toUnsignedString(getAllocatedMemoryLong());
	}

	long getAllocatedDiskLong();

	default String getAllocatedDisk() {
		return Long.toUnsignedString(getAllocatedDiskLong());
	}

	String getDaemonListenPort();

	String getDaemonSFTPPort();

	String getDaemonBase();

	PteroAction<List<ApplicationServer>> retrieveServers();

	PaginationAction<ApplicationAllocation> retrieveAllocations();

	default PteroAction<List<ApplicationAllocation>> retrieveAllocationsByPort(int port) {
		return retrieveAllocations().all().map(List::stream).map(stream -> stream.filter(a -> a.getPortInt() == port)
				.collect(StreamUtils.toUnmodifiableList()));
	}

	default PteroAction<List<ApplicationAllocation>> retrieveAllocationsByPort(String port) {
		return retrieveAllocationsByPort(Integer.parseUnsignedInt(port));
	}

	PteroAction<Node.Configuration> retrieveConfiguration();

	NodeAction edit();

	PteroAction<Void> delete();

	interface Configuration {
		boolean isDebug();

		UUID getUUID();

		String getTokenId();

		String getToken();

		APIConfiguration getAPI();

		SystemConfiguration getSystem();

		List<String> getAllowedMounts();

		String getRemote();
	}

	interface APIConfiguration {
		String getHost();

		int getPort();

		boolean isSSLEnabled();

		String getSSLCertPath();

		String getSSLKeyPath();

		int getUploadLimit();
	}

	interface SystemConfiguration {
		String getDataPath();

		int getSFTPPort();
	}
}
