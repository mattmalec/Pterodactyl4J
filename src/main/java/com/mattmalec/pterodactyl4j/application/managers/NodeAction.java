package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.application.entities.Location;

public interface NodeAction {

	NodeAction setName(String name);
	NodeAction setLocation(Location location);
	NodeAction setPublic(boolean isPublic);
	NodeAction setFQDN(String fqdn);
	NodeAction setBehindProxy(boolean isBehindProxy);
	NodeAction setDaemonBase(String daemonBase);
	NodeAction setMemory(String memory);
	NodeAction setMemoryOverallocate(String memoryOverallocate);
	NodeAction setDiskSpace(String disk);

}
