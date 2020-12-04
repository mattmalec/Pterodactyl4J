package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

public interface NodeAction {

	NodeAction setName(String name);
	NodeAction setLocation(Location location);
	NodeAction setPublic(boolean isPublic);
	NodeAction setFQDN(String fqdn);
	NodeAction setBehindProxy(boolean isBehindProxy);
	NodeAction setDaemonBase(String daemonBase);
	NodeAction setMemory(String memory);
	default NodeAction setMemory(long memory) { return setMemory(Long.toUnsignedString(memory)); }
	NodeAction setMemoryOverallocate(String memoryOverallocate);
	default NodeAction setMemoryOverallocate(long memoryOverallocate) { return setMemoryOverallocate(Long.toUnsignedString(memoryOverallocate)); }
	NodeAction setDiskSpace(String diskSpace);
	default NodeAction setDiskSpace(long diskSpace) { return setDiskSpace(Long.toUnsignedString(diskSpace)); }
	NodeAction setDiskSpaceOverallocate(String diskSpaceOverallocate);
	default NodeAction setDiskSpaceOverallocate(long diskSpaceOverallocate) { return setDiskSpaceOverallocate(Long.toUnsignedString(diskSpaceOverallocate)); }
	NodeAction setDaemonSFTPPort(String port);
	NodeAction setDaemonListenPort(String port);
	NodeAction setThrottle(boolean throttle);
	NodeAction setScheme(boolean secure);

	PteroAction<Node> build();

}
