package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.Egg;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.User;

import java.util.Map;
import java.util.Set;

public interface ServerAction {

	ServerAction setName(String name);
	ServerAction setDescription(String description);
	ServerAction setOwner(User owner);
	ServerAction setEgg(Egg egg);
	ServerAction setDockerImage(String dockerImage);
	ServerAction setStartupCommand(String command);
	ServerAction setMemory(long amount, DataType dataType);
	ServerAction setSwap(long amount, DataType dataType);
	ServerAction setDisk(long amount, DataType dataType);
	ServerAction setIO(long amount);
	ServerAction setCPU(long amount);
	ServerAction setDatabases(long amount);
	ServerAction setAllocations(long amount);
	ServerAction setEnvironment(Map<String, String> environment);
	ServerAction setLocations(Set<Location> locations);
	ServerAction setDedicatedIP(boolean dedicatedIP);
	ServerAction setPortRange(Set<String> ports);
	ServerAction startOnCompletion(boolean start);
	ServerAction skipScripts(boolean skip);
	PteroAction<ApplicationServer> build();

}
