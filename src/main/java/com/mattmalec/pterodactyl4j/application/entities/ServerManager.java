package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;

import java.util.Set;

public class ServerManager {

	public PteroAction<Server> setName(String name) {
		return null;
	}

	public PteroAction<Server> setOwner(User user) {
		return null;
	}

	public PteroAction<Server> setDescription(String description) {
		return null;
	}

	public PteroAction<Server> setAllocation(Allocation allocation) {
		return null;
	}

	public PteroAction<Server> setMemory(long amount, DataType dataType) {
		return null;
	}

	public PteroAction<Server> setSwap(long amount, DataType dataType) {
		return null;
	}

	public PteroAction<Server> setIO(long amount) {
		return null;
	}

	public PteroAction<Server> setCPU(long amount) {
		return null;
	}

	public PteroAction<Server> setDisk(long amount, DataType dataType) {
		return null;
	}

	public PteroAction<Server> setAllowedDatabases(int amount) {
		return null;
	}

	public PteroAction<Server> setAllowedAllocations(int amount) {
		return null;
	}

	public PteroAction<Void> setStartupCommand(String command) {
		return null;
	}

	public PteroAction<Void> setEnvironment(Set<String> environment) {
		return null;
	}

	public PteroAction<Void> setEgg(Egg egg) {
		return null;
	}

	public PteroAction<Void> setImage(String dockerImage) {
		return null;
	}

	public PteroAction<Void> setSkipScripts(boolean skipScripts) {
		return null;
	}
}
