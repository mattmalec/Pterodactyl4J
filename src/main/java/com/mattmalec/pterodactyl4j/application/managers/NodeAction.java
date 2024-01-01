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

package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Node;

public interface NodeAction extends PteroAction<Node> {

	NodeAction setName(String name);

	NodeAction setLocation(Location location);

	NodeAction setPublic(boolean isPublic);

	NodeAction setFQDN(String fqdn);

	NodeAction setBehindProxy(boolean isBehindProxy);

	NodeAction setDaemonBase(String daemonBase);

	NodeAction setMemory(String memory);

	default NodeAction setMemory(long memory) {
		return setMemory(Long.toUnsignedString(memory));
	}

	NodeAction setMemoryOverallocate(String memoryOverallocate);

	default NodeAction setMemoryOverallocate(long memoryOverallocate) {
		return setMemoryOverallocate(Long.toUnsignedString(memoryOverallocate));
	}

	NodeAction setDiskSpace(String diskSpace);

	default NodeAction setDiskSpace(long diskSpace) {
		return setDiskSpace(Long.toUnsignedString(diskSpace));
	}

	NodeAction setDiskSpaceOverallocate(String diskSpaceOverallocate);

	default NodeAction setDiskSpaceOverallocate(long diskSpaceOverallocate) {
		return setDiskSpaceOverallocate(Long.toUnsignedString(diskSpaceOverallocate));
	}

	NodeAction setDaemonSFTPPort(String port);

	NodeAction setDaemonListenPort(String port);

	NodeAction setThrottle(boolean throttle);

	NodeAction setScheme(boolean secure);

	NodeAction setMaintanceMode(boolean isInMaintenanceMode);
}
