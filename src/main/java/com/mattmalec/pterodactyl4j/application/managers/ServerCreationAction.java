package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.*;

import java.util.*;

public interface ServerCreationAction extends PteroAction<ApplicationServer> {

	ServerCreationAction setName(String name);
	ServerCreationAction setDescription(String description);
	ServerCreationAction setOwner(ApplicationUser owner);
	ServerCreationAction setEgg(ApplicationEgg egg);
	ServerCreationAction setDockerImage(String dockerImage);
	ServerCreationAction setStartupCommand(String command);
	ServerCreationAction setMemory(long amount, DataType dataType);
	ServerCreationAction setSwap(long amount, DataType dataType);
	ServerCreationAction setDisk(long amount, DataType dataType);
	ServerCreationAction setIO(long amount);
	ServerCreationAction setCPU(long amount);
	ServerCreationAction setThreads(String cores);
	ServerCreationAction setDatabases(long amount);
	ServerCreationAction setAllocations(long amount);
	ServerCreationAction setBackups(long amount);
	ServerCreationAction setEnvironment(Map<String, EnvironmentValue<?>> environment);
	ServerCreationAction setLocations(Set<Location> locations);
	default ServerCreationAction setLocation(Location location) {
		return setLocations(Collections.singleton(location));
	}
	ServerCreationAction setDedicatedIP(boolean dedicatedIP);
	ServerCreationAction setPortRange(Set<Integer> ports);
	default ServerCreationAction setPort(int port) {
		return setPortRange(Collections.singleton(port));
	}
	default ServerCreationAction setAllocations(Allocation defaultAllocation, Allocation... additionalAllocations) {
		return setAllocations(defaultAllocation, Arrays.asList(additionalAllocations));
	}
	default ServerCreationAction setAllocation(Allocation defaultAllocation) {
		return setAllocations(defaultAllocation);
	}
	ServerCreationAction setAllocations(Allocation defaultAllocation, Collection<Allocation> additionalAllocations);
	ServerCreationAction startOnCompletion(boolean start);
	ServerCreationAction skipScripts(boolean skip);

}
