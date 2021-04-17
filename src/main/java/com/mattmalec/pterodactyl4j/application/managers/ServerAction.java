package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.application.entities.*;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

import java.util.*;

public interface ServerAction {

	ServerAction setName(String name);
	ServerAction setDescription(String description);
	ServerAction setOwner(ApplicationUser owner);
	ServerAction setEgg(ApplicationEgg egg);
	ServerAction setDockerImage(String dockerImage);
	ServerAction setStartupCommand(String command);
	ServerAction setMemory(long amount, DataType dataType);
	ServerAction setSwap(long amount, DataType dataType);
	ServerAction setDisk(long amount, DataType dataType);
	ServerAction setIO(long amount);
	ServerAction setCPU(long amount);
	ServerAction setThreads(String cores);
	ServerAction setDatabases(long amount);
	ServerAction setAllocations(long amount);
	ServerAction setBackups(long amount);
	ServerAction setEnvironment(Map<String, EnvironmentValue<?>> environment);
	ServerAction setLocations(Set<Location> locations);
	default ServerAction setLocation(Location location) {
		return setLocations(Collections.singleton(location));
	}
	ServerAction setDedicatedIP(boolean dedicatedIP);
	ServerAction setPortRange(Set<Integer> ports);
	default ServerAction setPort(int port) {
		return setPortRange(Collections.singleton(port));
	}
	default ServerAction setAllocations(Allocation defaultAllocation, Allocation... additionalAllocations) {
		return setAllocations(defaultAllocation, Arrays.asList(additionalAllocations));
	}
	default ServerAction setAllocation(Allocation defaultAllocation) {
		return setAllocations(defaultAllocation);
	}
	ServerAction setAllocations(Allocation defaultAllocation, Collection<Allocation> additionalAllocations);
	ServerAction startOnCompletion(boolean start);
	ServerAction skipScripts(boolean skip);
	PteroAction<ApplicationServer> build();

}
