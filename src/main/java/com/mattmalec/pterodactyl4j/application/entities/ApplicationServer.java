package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.application.managers.ServerController;
import com.mattmalec.pterodactyl4j.application.managers.ServerManager;
import com.mattmalec.pterodactyl4j.entities.Server;
import com.mattmalec.pterodactyl4j.utils.Relationed;

import java.util.List;
import java.util.Optional;

public interface ApplicationServer extends Server, ISnowflake {

	boolean isSuspended();
	String getExternalId();
	Relationed<ApplicationUser> getOwner();
	Relationed<Node> getNode();
	Optional<List<Allocation>> getAllocations();
	Relationed<Allocation> getDefaultAllocation();
	Relationed<Nest> getNest();
	Relationed<ApplicationEgg> getEgg();
	long getPack();
	Container getContainer();
	ServerManager getManager();
	ServerController getController();

}
