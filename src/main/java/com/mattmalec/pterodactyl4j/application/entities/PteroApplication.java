/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.managers.LocationManager;
import com.mattmalec.pterodactyl4j.application.managers.NodeManager;
import com.mattmalec.pterodactyl4j.application.managers.ServerCreationAction;
import com.mattmalec.pterodactyl4j.application.managers.UserManager;

import java.util.List;

/**
 * The core of PteroApplication. All parts of the the PteroApplication API can be accessed starting from this class.
 *
 * @see com.mattmalec.pterodactyl4j.PteroBuilder PteroBuilder
 */
public interface PteroApplication {

	/**
	 * Retrieves all of the ApplicationUsers from the Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Users</b> permission with <b>Read</b> access.
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUsers}
	 */
	PteroAction<List<ApplicationUser>> retrieveUsers();

	/**
	 * Retrieves an individual ApplicationUser represented by the provided id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Users</b> permission with <b>Read</b> access.
	 *
	 * @param  id
	 *         The user id
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the user cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUsers}
	 */
	PteroAction<ApplicationUser> retrieveUserById(String id);

	/**
	 * Retrieves an individual ApplicationUser represented by the provided id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Users</b> permission with <b>Read</b> access.
	 *
	 * @param  id
	 *         The user id
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the user cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUsers}
	 */
	default PteroAction<ApplicationUser> retrieveUserById(long id) {
		return retrieveUserById(Long.toUnsignedString(id));
	}

	/**
	 * Retrieves ApplicationUsers matching the provided username from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Users</b> permission with <b>Read</b> access.
	 *
	 * @param  name
	 *         The username
	 * @param caseSensetive
	 * 		   True - If P4J should search using case sensitivity
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the user cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUsers}
	 */
	PteroAction<List<ApplicationUser>> retrieveUsersByUsername(String name, boolean caseSensetive);

	/**
	 * Retrieves ApplicationUsers matching the provided email from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Users</b> permission with <b>Read</b> access.
	 *
	 * @param  email
	 *         The email
	 * @param caseSensetive
	 * 		   True - If P4J should search using case sensitivity
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the user cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUsers}
	 */
	PteroAction<List<ApplicationUser>> retrieveUsersByEmail(String email, boolean caseSensetive);

	/**
	 * Returns the {@link com.mattmalec.pterodactyl4j.application.managers.UserManager UserManager}, used to create, edit, and delete ApplicationUsers from the Pterodactyl instance
	 * <br>Executing any of the containing methods requires an <b>Application API key</b> with the <b>Users</b> permission with <b>Read &amp; Write</b> access.
	 *
	 * @return The User Manager
	 */
	UserManager getUserManager();

	/**
	 * Retrieves all of the Nodes from the Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nodes</b> permission with <b>Read</b> access.
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.Node Nodes}
	 */
	PteroAction<List<Node>> retrieveNodes();

	/**
	 * Retrieves an individual Node represented by the provided id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nodes</b> permission with <b>Read</b> access.
	 *
	 * @param  id
	 *         The node id
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the node cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.Node Node}
	 */
	PteroAction<Node> retrieveNodeById(String id);

	/**
	 * Retrieves an individual Node represented by the provided id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nodes</b> permission with <b>Read</b> access.
	 *
	 * @param  id
	 *         The node id
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the node cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.Node Node}
	 */
	default PteroAction<Node> retrieveNodeById(long id) {
		return retrieveNodeById(Long.toUnsignedString(id));
	}

	/**
	 * Retrieves Nodes matching the provided name from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nodes</b> permission with <b>Read</b> access.
	 *
	 * @param  name
	 *         The name
	 * @param caseSensetive
	 * 		   True - If P4J should search using case sensitivity
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.Node Nodes}
	 */
	PteroAction<List<Node>> retrieveNodesByName(String name, boolean caseSensetive);

	/**
	 * Retrieves Nodes matching the provided {@link com.mattmalec.pterodactyl4j.application.entities.Location Location} from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nodes</b> permission with <b>Read</b> access.
	 *
	 * @param  location
	 *         The location
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the user cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.Node Nodes}
	 */
	PteroAction<List<Node>> retrieveNodesByLocation(Location location);

	/**
	 * Returns the {@link com.mattmalec.pterodactyl4j.application.managers.NodeManager NodeManager}, used to create, edit, and delete Nodes from the Pterodactyl instance
	 * <br>Executing any of the containing methods requires an <b>Application API key</b> with the <b>Users</b> permission with <b>Read &amp; Write</b> access.
	 *
	 * @return The Node Manager
	 */
	NodeManager getNodeManager();

	/**
	 * Retrieves an individual Allocation represented by the provided id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Allocations</b> permission with <b>Read</b> access.
	 *
	 * @param  id
	 *         The allocation id
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the allocation cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link ApplicationAllocation Allocation}
	 */
	PteroAction<ApplicationAllocation> retrieveAllocationById(String id);

	/**
	 * Retrieves an individual Allocation represented by the provided id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Allocations</b> permission with <b>Read</b> access.
	 *
	 * @param  id
	 *         The allocation id
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the allocation cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link ApplicationAllocation Allocation}
	 */
	default PteroAction<ApplicationAllocation> retrieveAllocationById(long id) {
		return retrieveAllocationById(Long.toUnsignedString(id));
	}

	/**
	 * Retrieves Allocations from the provided {@link com.mattmalec.pterodactyl4j.application.entities.Node Node} from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Allocations</b> permission with <b>Read</b> access.
	 *
	 * @param  node
	 *         The node
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link ApplicationAllocation Allocations}
	 */
	PteroAction<List<ApplicationAllocation>> retrieveAllocationsByNode(Node node);

	/**
	 * Retrieves all of the Allocations from the Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Allocations</b> permission with <b>Read</b> access.
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link ApplicationAllocation Allocations}
	 */
	PteroAction<List<ApplicationAllocation>> retrieveAllocations();

	/**
	 * Retrieves all of the Locations from the Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Locations</b> permission with <b>Read</b> access.
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.Location Locations}
	 */
	PteroAction<List<Location>> retrieveLocations();

	/**
	 * Retrieves an individual Location represented by the provided id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Locations</b> permission with <b>Read</b> access.
	 *
	 * @param  id
	 *         The location id
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the location cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.Location Location}
	 */
	PteroAction<Location> retrieveLocationById(String id);

	/**
	 * Retrieves an individual Location represented by the provided id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Locations</b> permission with <b>Read</b> access.
	 *
	 * @param  id
	 *         The location id
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the location cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.Location Location}
	 */
	default PteroAction<Location> retrieveLocationById(long id) {
		return retrieveLocationById(Long.toUnsignedString(id));
	}

	/**
	 * Retrieves Locations matching the provided short code from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Locations</b> permission with <b>Read</b> access.
	 *
	 * @param  name
	 *         The short code
	 *
	 * @param caseSensetive
	 * 		   True - If P4J should search using case sensitivity
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.Location Locations}
	 */
	PteroAction<List<Location>> retrieveLocationsByShortCode(String name, boolean caseSensetive);

	/**
	 * Returns the {@link com.mattmalec.pterodactyl4j.application.managers.LocationManager LocationManager}, used to create, edit, and delete Locations from the Pterodactyl instance
	 * <br>Executing any of the containing methods requires an <b>Application API key</b> with the <b>Locations</b> permission with <b>Read &amp; Write</b> access.
	 *
	 * @return The Location Manager
	 */
	LocationManager getLocationManager();

	/**
	 * Retrieves ApplicationEggs from the provided {@link com.mattmalec.pterodactyl4j.application.entities.Nest Nest} from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nests</b> and <b>Eggs</b> permissions with <b>Read</b> access.
	 *
	 * @param  nest
	 *         The nest
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg ApplicationEggs}
	 */
	PteroAction<List<ApplicationEgg>> retrieveEggsByNest(Nest nest);

	/**
	 * Retrieves all of the ApplicationEggs from the Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nests</b> and <b>Eggs</b> permissions with <b>Read</b> access.
	 *
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg ApplicationEggs}
	 */
	PteroAction<List<ApplicationEgg>> retrieveEggs();

	/**
	 * Retrieves an individual ApplicationEgg represented by the provided {@link com.mattmalec.pterodactyl4j.application.entities.Nest Nest} and id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nests</b> and <b>Eggs</b> permissions with <b>Read</b> access.
	 *
	 * @param  nest
	 *         The nest
	 *
	 * @param  id
	 * 		   The id of the egg from in nest
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the egg cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg ApplicationEgg}
	 */
	PteroAction<ApplicationEgg> retrieveEggById(Nest nest, String id);

	/**
	 * Retrieves an individual ApplicationEgg represented by the provided {@link com.mattmalec.pterodactyl4j.application.entities.Nest Nest} and id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nests</b> and <b>Eggs</b> permissions with <b>Read</b> access.
	 *
	 * @param  nest
	 *         The nest
	 *
	 * @param  id
	 * 		   The id of the egg from in nest
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the egg cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg ApplicationEgg}
	 */
	default PteroAction<ApplicationEgg> retrieveEggById(Nest nest, long id) {
		return retrieveEggById(nest, Long.toUnsignedString(id));
	}

	/**
	 * Retrieves an individual Nest represented by the provided id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nests</b> permission with <b>Read</b> access.
	 *
	 * @param  id
	 *         The id
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the egg cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.Nest Nest}
	 */
	PteroAction<Nest> retrieveNestById(String id);

	/**
	 * Retrieves an individual Nest represented by the provided id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nests</b> permission with <b>Read</b> access.
	 *
	 * @param  id
	 *         The id
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the egg cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.Nest Nest}
	 */
	default PteroAction<Nest> retrieveNestById(long id) {
		return retrieveNestById(Long.toUnsignedString(id));
	}

	/**
	 * Retrieves all of the Nests from the Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nests</b> permissions with <b>Read</b> access.
	 *
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg ApplicationEggs}
	 */
	PteroAction<List<Nest>> retrieveNests();

	/**
	 * Retrieves Nests matching the provided author from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nests</b> permission with <b>Read</b> access.
	 *
	 * @param  author
	 *         The email address of the author
	 * @param caseSensetive
	 * 		   True - If P4J should search using case sensitivity
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.Nest Nests}
	 */
	PteroAction<List<Nest>> retrieveNestsByAuthor(String author, boolean caseSensetive);

	/**
	 * Retrieves Nests matching the provided name from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Nests</b> permission with <b>Read</b> access.
	 *
	 * @param  name
	 *         The name
	 * @param caseSensetive
	 * 		   True - If P4J should search using case sensitivity
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.Nest Nests}
	 */
	PteroAction<List<Nest>> retrieveNestsByName(String name, boolean caseSensetive);

	/**
	 * Retrieves all of the ApplicationServers from the Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Servers</b> permissions with <b>Read</b> access.
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServers}
	 */
	PteroAction<List<ApplicationServer>> retrieveServers();

	/**
	 * Retrieves an individual ApplicationServer represented by the provided id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Servers</b> permission with <b>Read</b> access.
	 *
	 * @param  id
	 *         The id
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the server cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}
	 */
	PteroAction<ApplicationServer> retrieveServerById(String id);

	/**
	 * Retrieves an individual ApplicationServer represented by the provided id from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Servers</b> permission with <b>Read</b> access.
	 *
	 * @param  id
	 *         The id
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.NotFoundException
	 * 		   If the server cannot be found
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}
	 */
	default PteroAction<ApplicationServer> retrieveServerById(long id) {
		return retrieveServerById(Long.toUnsignedString(id));
	}

	/**
	 * Retrieves ApplicationServers matching the provided name from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Servers</b> permission with <b>Read</b> access.
	 *
	 * @param  name
	 *         The name
	 * @param caseSensetive
	 * 		   True - If P4J should search using case sensitivity
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServers}
	 */
	PteroAction<List<ApplicationServer>> retrieveServersByName(String name, boolean caseSensetive);

	/**
	 * Retrieves ApplicationServers owned by the provided {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUser} from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Servers</b> and <b>Users</b> permissions with <b>Read</b> access.
	 *
	 * @param  user
	 *         The owner
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServers}
	 */
	PteroAction<List<ApplicationServer>> retrieveServersByOwner(ApplicationUser user);

	/**
	 * Retrieves ApplicationServers running on the provided {@link com.mattmalec.pterodactyl4j.application.entities.Node Node} from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Servers</b> and <b>Nodes</b> permissions with <b>Read</b> access.
	 *
	 * @param  node
	 *         The node
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServers}
	 */
	PteroAction<List<ApplicationServer>> retrieveServersByNode(Node node);

	/**
	 * Retrieves ApplicationServers in the provided {@link com.mattmalec.pterodactyl4j.application.entities.Location Location} from Pterodactyl instance
	 * <br>This requires an <b>Application API key</b> with the <b>Servers</b> and <b>Locations</b> permissions with <b>Read</b> access.
	 *
	 * @param  location
	 *         The location
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link java.util.List List} of {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServers}
	 */
	PteroAction<List<ApplicationServer>> retrieveServersByLocation(Location location);

	/**
	 * Returns a {@link com.mattmalec.pterodactyl4j.application.managers.ServerCreationAction}, used to create servers on the Pterodactyl instance
	 * <br>Creating a server requires an <b>Application API key</b> with the <b>Locations</b>, <b>Nests</b> and <b>Nodes</b> with <b>Read</b> access
	 * and <b>Servers</b> and <b>Users</b> permission with <b>Read &amp; Write</b> access.
	 *
	 * @return The ServerAction used to create servers
	 */
	ServerCreationAction createServer();


}
