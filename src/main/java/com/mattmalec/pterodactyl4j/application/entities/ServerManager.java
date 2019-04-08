package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;

import java.util.Set;

public interface ServerManager {

	PteroAction<Server> setName(String name);
	PteroAction<Server> setOwner(User user);
	PteroAction<Server> setDescription(String description);

	PteroAction<Server> setAllocation(Allocation allocation);
	PteroAction<Server> setMemory(long amount, DataType dataType);
	PteroAction<Server> setSwap(long amount, DataType dataType);
	PteroAction<Server> setIO(long amount);
	PteroAction<Server> setCPU(long amount);
	PteroAction<Server> setDisk(long amount, DataType dataType);

	PteroAction<Server> setAllowedDatabases(int amount);
	PteroAction<Server> setAllowedAllocations(int amount);

	PteroAction<Void> setStartupCommand(String command);
	PteroAction<Void> setEnvironment(Set<String> environment);
	PteroAction<Void> setEgg(Egg egg);
	PteroAction<Void> setImage(String dockerImage);
	PteroAction<Void> setSkipScripts(boolean skipScripts);


}
