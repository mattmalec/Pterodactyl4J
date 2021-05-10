package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.application.managers.ServerController;
import com.mattmalec.pterodactyl4j.application.managers.ServerManager;
import com.mattmalec.pterodactyl4j.entities.Server;
import com.mattmalec.pterodactyl4j.utils.Relationed;

import java.util.List;
import java.util.Optional;

/**
 * Represents a Pterodactyl {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
 * This should contain all information provided from the Pterodactyl instance about an ApplicationServer.
 *
 * @see PteroApplication#retrieveServers()
 * @see PteroApplication#retrieveServerById(long)
 * @see PteroApplication#retrieveServersByName(String, boolean)
 * @see PteroApplication#retrieveServersByOwner(ApplicationUser)
 * @see PteroApplication#retrieveServersByNode(Node)
 * @see PteroApplication#retrieveServersByLocation(Location)
 */
public interface ApplicationServer extends Server, ISnowflake {

	/**
	 * Returns whether or not this ApplicationServer is suspsneded
	 * The server suspension state can be controlled using {@link ServerController#suspend()} and {@link ServerController#unsuspend()}.
	 *
	 * @return True - if this ApplicationServer is suspended
	 */
	boolean isSuspended();

	/**
	 * The external id of the ApplicationServer
	 *
	 * @return Never-null String containing the Servers's identifier.
	 */
	String getExternalId();

	/**
	 * The owner of the ApplicationServer
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.utils.Relationed Relationed} - Type {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUser}
	 */
	Relationed<ApplicationUser> getOwner();

	/**
	 * The Node the ApplicationServer is running on
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.utils.Relationed Relationed} - Type {@link com.mattmalec.pterodactyl4j.application.entities.Node Node}
	 */
	Relationed<Node> getNode();

	/**
	 * The Allocations assigned to the ApplicationServer
	 *
	 * @return {@link java.util.Optional Optional} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.Allocation Allocations}
	 */
	Optional<List<Allocation>> getAllocations();
	Relationed<Allocation> getDefaultAllocation();
	Relationed<Nest> getNest();
	Relationed<ApplicationEgg> getEgg();
	long getPack();
	Container getContainer();
	ServerManager getManager();
	ServerController getController();

}
