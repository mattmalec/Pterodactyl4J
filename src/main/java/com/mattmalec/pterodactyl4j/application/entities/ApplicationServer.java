package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.application.managers.ServerController;
import com.mattmalec.pterodactyl4j.application.managers.ServerManager;
import com.mattmalec.pterodactyl4j.entities.Server;

import java.util.List;

public interface ApplicationServer extends Server, ISnowflake {

	boolean isSuspended();
	String getExternalId();
	ApplicationUser getOwner();
	Node getNode();
	List<Allocation> getAllocations();
	Allocation getDefaultAllocation();
	Nest getNest();
	Egg getEgg();
	long getPack();
	Container getContainer();
	ServerManager getManager();
	ServerController getController();

}
