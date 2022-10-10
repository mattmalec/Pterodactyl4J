/*
 *    Copyright 2021-2022 Matt Malec, and the Pterodactyl4J contributors
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

package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.*;
import com.mattmalec.pterodactyl4j.application.managers.LocationManager;
import com.mattmalec.pterodactyl4j.application.managers.NodeManager;
import com.mattmalec.pterodactyl4j.application.managers.ServerCreationAction;
import com.mattmalec.pterodactyl4j.application.managers.UserManager;
import com.mattmalec.pterodactyl4j.entities.P4J;
import com.mattmalec.pterodactyl4j.requests.PaginationAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.impl.PaginationResponseImpl;
import com.mattmalec.pterodactyl4j.utils.StreamUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.json.JSONObject;

public class PteroApplicationImpl implements PteroApplication {

	private final P4J api;

	public PteroApplicationImpl(P4J api) {
		this.api = api;
	}

	public P4J getP4J() {
		return api;
	}

	public PteroAction<ApplicationUser> retrieveUserById(String id) {
		return PteroActionImpl.onRequestExecute(
				api,
				Route.Users.GET_USER.compile(id),
				(response, request) -> new ApplicationUserImpl(response.getObject(), this));
	}

	@Override
	public PaginationAction<ApplicationUser> retrieveUsers() {
		return PaginationResponseImpl.onPagination(
				api, Route.Users.LIST_USERS.compile(), (object) -> new ApplicationUserImpl(object, this));
	}

	@Override
	public PteroAction<List<ApplicationUser>> retrieveUsersByUsername(String name, boolean caseSensitive) {
		return PteroActionImpl.onExecute(api, () -> {
			Stream<ApplicationUser> users = retrieveUsers().stream();

			if (caseSensitive) {
				users = users.filter(u -> u.getUserName().contains(name));
			} else {
				users = users.filter(u -> u.getUserName().toLowerCase().contains(name.toLowerCase()));
			}

			return users.collect(StreamUtils.toUnmodifiableList());
		});
	}

	@Override
	public PteroAction<List<ApplicationUser>> retrieveUsersByEmail(String email, boolean caseSensitive) {
		return PteroActionImpl.onExecute(api, () -> {
			Stream<ApplicationUser> users = retrieveUsers().stream();

			if (caseSensitive) {
				users = users.filter(u -> u.getEmail().contains(email));
			} else {
				users = users.filter(u -> u.getEmail().toLowerCase().contains(email.toLowerCase()));
			}

			return users.collect(StreamUtils.toUnmodifiableList());
		});
	}

	@Override
	public UserManager getUserManager() {
		return new UserManagerImpl(this);
	}

	@Override
	public PaginationAction<Node> retrieveNodes() {
		return PaginationResponseImpl.onPagination(
				api, Route.Nodes.LIST_NODES.compile(), (object) -> new NodeImpl(object, this));
	}

	@Override
	public PteroAction<Node> retrieveNodeById(String id) {
		return PteroActionImpl.onRequestExecute(
				api, Route.Nodes.GET_NODE.compile(id), (response, request) -> new NodeImpl(response.getObject(), this));
	}

	@Override
	public PteroAction<List<Node>> retrieveNodesByName(String name, boolean caseSensitive) {
		return PteroActionImpl.onExecute(api, () -> {
			Stream<Node> nodes = retrieveNodes().stream();

			if (caseSensitive) {
				nodes = nodes.filter(n -> n.getName().contains(name));
			} else {
				nodes = nodes.filter(n -> n.getName().toLowerCase().contains(name.toLowerCase()));
			}

			return nodes.collect(StreamUtils.toUnmodifiableList());
		});
	}

	@Override
	public PteroAction<List<Node>> retrieveNodesByLocation(Location location) {
		return retrieveNodes().all().map(List::stream).map(stream -> stream.filter(
						n -> n.retrieveLocation().map(ISnowflake::getIdLong).execute() == location.getIdLong())
				.collect(StreamUtils.toUnmodifiableList()));
	}

	@Override
	public NodeManager getNodeManager() {
		return new NodeManagerImpl(this);
	}

	@Override
	public PaginationAction<ApplicationAllocation> retrieveAllocationsByNode(Node node) {
		return PaginationResponseImpl.onPagination(
				api,
				Route.Nodes.LIST_ALLOCATIONS.compile(node.getId()),
				(object) -> new ApplicationAllocationImpl(object, this));
	}

	@Override
	public PteroAction<List<ApplicationAllocation>> retrieveAllocations() {
		return PteroActionImpl.onExecute(api, () -> {
			List<ApplicationAllocation> allocations = new ArrayList<>();
			List<Node> nodes = retrieveNodes().all().execute();
			for (Node node : nodes) {
				allocations.addAll(node.retrieveAllocations().execute());
			}
			return Collections.unmodifiableList(allocations);
		});
	}

	@Override
	public PteroAction<ApplicationAllocation> retrieveAllocationById(String id) {
		return retrieveAllocations()
				.map(List::stream)
				.map(stream ->
						stream.filter(a -> a.getId().equals(id)).findFirst().orElse(null));
	}

	@Override
	public PteroAction<ApplicationEgg> retrieveEggById(Nest nest, String id) {
		return PteroActionImpl.onRequestExecute(
				api,
				Route.Nests.GET_EGG.compile(nest.getId(), id),
				(response, request) -> new ApplicationEggImpl(response.getObject(), this));
	}

	protected PteroAction<ApplicationEgg> retrieveEggById(String nest, String egg) {
		return PteroActionImpl.onRequestExecute(
				api,
				Route.Nests.GET_EGG.compile(nest, egg),
				(response, request) -> new ApplicationEggImpl(response.getObject(), this));
	}

	@Override
	public PteroAction<List<ApplicationEgg>> retrieveEggs() {
		return PteroActionImpl.onExecute(api, () -> {
			List<Nest> nests = retrieveNests().all().execute();
			List<ApplicationEgg> eggs = new ArrayList<>();
			for (Nest nest : nests) {
				eggs.addAll(nest.retrieveEggs().execute());
			}
			return Collections.unmodifiableList(eggs);
		});
	}

	@Override
	public PteroAction<List<ApplicationEgg>> retrieveEggsByNest(Nest nest) {
		return PteroActionImpl.onRequestExecute(
				api, Route.Nests.GET_EGGS.compile(nest.getId()), (response, request) -> {
					List<ApplicationEgg> eggs = new ArrayList<>();
					JSONObject json = response.getObject();
					for (Object o : json.getJSONArray("data")) {
						JSONObject egg = new JSONObject(o.toString());
						eggs.add(new ApplicationEggImpl(egg, this));
					}
					return Collections.unmodifiableList(eggs);
				});
	}

	@Override
	public PteroAction<Nest> retrieveNestById(String id) {
		return PteroActionImpl.onRequestExecute(
				api, Route.Nests.GET_NEST.compile(id), (response, request) -> new NestImpl(response.getObject(), this));
	}

	@Override
	public PaginationAction<Nest> retrieveNests() {
		return PaginationResponseImpl.onPagination(
				api, Route.Nests.LIST_NESTS.compile(), (object) -> new NestImpl(object, this));
	}

	@Override
	public PteroAction<List<Nest>> retrieveNestsByName(String name, boolean caseSensitive) {
		return PteroActionImpl.onExecute(api, () -> {
			Stream<Nest> nests = retrieveNests().stream();

			if (caseSensitive) {
				nests = nests.filter(n -> n.getName().contains(name));
			} else {
				nests = nests.filter(n -> n.getName().toLowerCase().contains(name.toLowerCase()));
			}

			return nests.collect(StreamUtils.toUnmodifiableList());
		});
	}

	@Override
	public PteroAction<List<Nest>> retrieveNestsByAuthor(String author, boolean caseSensitive) {
		return PteroActionImpl.onExecute(api, () -> {
			Stream<Nest> nests = retrieveNests().stream();

			if (caseSensitive) {
				nests = nests.filter(n -> n.getAuthor().contains(author));
			} else {
				nests = nests.filter(n -> n.getAuthor().toLowerCase().contains(author.toLowerCase()));
			}

			return nests.collect(StreamUtils.toUnmodifiableList());
		});
	}

	@Override
	public PaginationAction<Location> retrieveLocations() {
		return PaginationResponseImpl.onPagination(
				api, Route.Locations.LIST_LOCATIONS.compile(), (object) -> new LocationImpl(object, this));
	}

	@Override
	public PteroAction<Location> retrieveLocationById(String id) {
		return PteroActionImpl.onRequestExecute(
				api,
				Route.Locations.GET_LOCATION.compile(id),
				((response, request) -> new LocationImpl(response.getObject(), this)));
	}

	@Override
	public PteroAction<List<Location>> retrieveLocationsByShortCode(String name, boolean caseSensitive) {
		return retrieveLocations().all().map(List::stream).map(stream -> stream.filter(
						l -> StreamUtils.compareString(l.getShortCode(), name, caseSensitive))
				.collect(StreamUtils.toUnmodifiableList()));
	}

	@Override
	public LocationManager getLocationManager() {
		return new LocationManagerImpl(this);
	}

	@Override
	public PaginationAction<ApplicationServer> retrieveServers() {
		return PaginationResponseImpl.onPagination(
				api, Route.Servers.LIST_SERVERS.compile(), (object) -> new ApplicationServerImpl(this, object));
	}

	@Override
	public PteroAction<ApplicationServer> retrieveServerById(String id) {
		return PteroActionImpl.onRequestExecute(
				api,
				Route.Servers.GET_SERVER.compile(id),
				(response, request) -> new ApplicationServerImpl(this, response.getObject()));
	}

	@Override
	public PteroAction<List<ApplicationServer>> retrieveServersByName(String name, boolean caseSensitive) {
		return retrieveServers().all().map(List::stream).map(stream -> stream.filter(
						s -> StreamUtils.compareString(s.getName(), name, caseSensitive))
				.collect(StreamUtils.toUnmodifiableList()));
	}

	@Override
	public PteroAction<List<ApplicationServer>> retrieveServersByOwner(ApplicationUser user) {
		return retrieveServers().all().map(List::stream).map(stream -> stream.filter(
						s -> s.retrieveOwner().map(ISnowflake::getIdLong).execute() == user.getIdLong())
				.collect(StreamUtils.toUnmodifiableList()));
	}

	@Override
	public ServerCreationAction createServer() {
		return new CreateServerImpl(this);
	}
}
