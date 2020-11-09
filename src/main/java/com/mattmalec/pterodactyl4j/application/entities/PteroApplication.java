package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.managers.LocationManager;
import com.mattmalec.pterodactyl4j.application.managers.NodeManager;
import com.mattmalec.pterodactyl4j.application.managers.ServerAction;
import com.mattmalec.pterodactyl4j.application.managers.UserManager;

import java.util.List;

public interface PteroApplication {

	PteroAction<List<ApplicationUser>> retrieveUsers();
	PteroAction<ApplicationUser> retrieveUserById(String id);
	PteroAction<ApplicationUser> retrieveUserById(long id);
	PteroAction<List<ApplicationUser>> retrieveUsersByUsername(String name, boolean caseSensetive);
	PteroAction<List<ApplicationUser>> retrieveUsersByEmail(String name, boolean caseSensetive);
	UserManager getUserManager();

	PteroAction<List<Node>> retrieveNodes();
	PteroAction<Node> retrieveNodeById(String id);
	PteroAction<Node> retrieveNodeById(long id);
	PteroAction<List<Node>> retrieveNodesByName(String name, boolean caseSensetive);
	NodeManager getNodeManager();

	PteroAction<Allocation> retrieveAllocationById(String id);
	PteroAction<Allocation> retrieveAllocationById(long id);
	PteroAction<List<Allocation>> retrieveAllocationsByNode(Node node);
	PteroAction<List<Allocation>> retrieveAllocations();



	PteroAction<List<Location>> retrieveLocations();
	PteroAction<Location> retrieveLocationById(String id);
	PteroAction<Location> retrieveLocationById(long id);
	PteroAction<List<Location>> retrieveLocationsByShortCode(String name, boolean caseSensetive);
	LocationManager getLocationManager();

	PteroAction<List<Egg>> retrieveEggsByNest(Nest nest);
	PteroAction<List<Egg>> retrieveEggs();

	PteroAction<Egg> retrieveEggById(Nest nest, String id);
	PteroAction<Egg> retrieveEggById(Nest nest, long id);
	PteroAction<Nest> retrieveNestById(String id);
	PteroAction<Nest> retrieveNestById(long id);
	PteroAction<List<Nest>> retrieveNests();
	PteroAction<List<Nest>> retrieveNestsByAuthor(String author, boolean caseSensetive);
	PteroAction<List<Nest>> retrieveNestsByName(String name, boolean caseSensetive);

	PteroAction<List<ApplicationServer>> retrieveServers();
	PteroAction<ApplicationServer> retrieveServerById(String id);
	PteroAction<ApplicationServer> retrieveServerById(long id);
	PteroAction<List<ApplicationServer>> retrieveServersByName(String name, boolean caseSensetive);
	PteroAction<List<ApplicationServer>> retrieveServersByOwner(ApplicationUser user);
	ServerAction createServer();


}
