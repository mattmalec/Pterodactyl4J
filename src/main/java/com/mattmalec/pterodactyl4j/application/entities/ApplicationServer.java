package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.managers.ServerController;
import com.mattmalec.pterodactyl4j.application.managers.ServerManager;

public interface ApplicationServer extends Server, ISnowflake {

	boolean isSuspended();
	String getExternalId();
	PteroAction<User> retrieveOwner();
	PteroAction<Node> retrieveNode();
	PteroAction<Allocation> retrieveAllocation();
	PteroAction<Nest> retrieveNest();
	PteroAction<Egg> retrieveEgg();
	String getPack();
	Container getContainer();
	ServerManager getManager();
	ServerController getController();

}
