package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.*;
import com.mattmalec.pterodactyl4j.application.managers.LocationManager;
import com.mattmalec.pterodactyl4j.application.managers.NodeManager;
import com.mattmalec.pterodactyl4j.application.managers.ServerCreationAction;
import com.mattmalec.pterodactyl4j.application.managers.UserManager;
import com.mattmalec.pterodactyl4j.entities.PteroAPI;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PteroApplicationImpl implements PteroApplication {

	private PteroAPI api;

	public PteroApplicationImpl(PteroAPI api) {
		this.api = api;
	}

	public PteroAPI getPteroApi() {
		return api;
	}

	public PteroAction<ApplicationUser> retrieveUserById(String id) {
		return PteroActionImpl.onRequestExecute(api, Route.Users.GET_USER.compile(id),
				(response, request) -> new ApplicationUserImpl(response.getObject(), this));
	}

	@Override
	public PteroAction<List<ApplicationUser>> retrieveUsers() {
		return PteroActionImpl.onExecute(() -> {
			List<ApplicationUser> users = new ArrayList<>();
			JSONObject json = new PteroActionImpl<JSONObject>(api, Route.Users.LIST_USERS.compile("1"),
					(response, request) -> response.getObject()).execute();
			long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
			for (Object o : json.getJSONArray("data")) {
				JSONObject user = new JSONObject(o.toString());
				users.add(new ApplicationUserImpl(user, this));
			}
			for (int i = 2; i <= pages; i++) {
				JSONObject nextJson = new PteroActionImpl<JSONObject>(api, Route.Users.LIST_USERS.compile(Long.toUnsignedString(i)),
						(response, request) -> response.getObject()).execute();
				for (Object o : nextJson.getJSONArray("data")) {
					JSONObject user = new JSONObject(o.toString());
					users.add(new ApplicationUserImpl(user, this));
				}
			}
			return Collections.unmodifiableList(users);
		});
	}

	@Override
	public PteroAction<List<ApplicationUser>> retrieveUsersByUsername(String name, boolean caseSensetive) {
		return PteroActionImpl.onExecute(() ->
        {
				List<ApplicationUser> users = retrieveUsers().execute();
				Stream<ApplicationUser> newUsers = users.stream();

				if(caseSensetive) {
					newUsers = newUsers.filter(u -> u.getUserName().contains(name));
				} else {
					newUsers = newUsers.filter(u -> u.getUserName().toLowerCase().contains(name.toLowerCase()));
				}

				return Collections.unmodifiableList(newUsers.collect(Collectors.toList()));
        });
	}

	@Override
	public PteroAction<List<ApplicationUser>> retrieveUsersByEmail(String email, boolean caseSensetive) {
		return PteroActionImpl.onExecute(() ->
        {
				List<ApplicationUser> users = retrieveUsers().execute();
				Stream<ApplicationUser> newUsers = users.stream();

				if(caseSensetive) {
					newUsers = newUsers.filter(u -> u.getEmail().contains(email));
				} else {
					newUsers = newUsers.filter(u -> u.getEmail().toLowerCase().contains(email.toLowerCase()));
				}

				return Collections.unmodifiableList(newUsers.collect(Collectors.toList()));
        });
	}

	@Override
	public UserManager getUserManager() {
		return new UserManagerImpl(this);
	}

	@Override
	public PteroAction<List<Node>> retrieveNodes() {
		return PteroActionImpl.onExecute(() -> {
			List<Node> nodes = new ArrayList<>();
			JSONObject json = new PteroActionImpl<JSONObject>(api, Route.Nodes.LIST_NODES.compile("1"),
					(response, request) -> response.getObject()).execute();
			long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
			for (Object o : json.getJSONArray("data")) {
				JSONObject node = new JSONObject(o.toString());
				nodes.add(new NodeImpl(node, this));
			}
			for (int i = 2; i <= pages; i++) {
				JSONObject nextJson = new PteroActionImpl<JSONObject>(api, Route.Nodes.LIST_NODES.compile(Long.toUnsignedString(i)),
						(response, request) -> response.getObject()).execute();
				for (Object o : nextJson.getJSONArray("data")) {
					JSONObject node = new JSONObject(o.toString());
					nodes.add(new NodeImpl(node, this));
				}
			}
			return Collections.unmodifiableList(nodes);
		});
	}

	@Override
	public PteroAction<Node> retrieveNodeById(String id) {
		return PteroActionImpl.onRequestExecute(api, Route.Nodes.GET_NODE.compile(id),
				(response, request) -> new NodeImpl(response.getObject(), this));
	}

	@Override
	public PteroAction<List<Node>> retrieveNodesByName(String name, boolean caseSensetive) {
		return PteroActionImpl.onExecute(() ->
        {
				List<Node> nodes = retrieveNodes().execute();
				Stream<Node> newNodes = nodes.stream();

				if(caseSensetive) {
					newNodes = newNodes.filter(n -> n.getName().contains(name));
				} else {
					newNodes = newNodes.filter(n -> n.getName().toLowerCase().contains(name.toLowerCase()));
				}

				return Collections.unmodifiableList(newNodes.collect(Collectors.toList()));
        });
	}

	@Override
	public PteroAction<List<Node>> retrieveNodesByLocation(Location location) {
		return PteroActionImpl.onExecute(() ->
        {
				List<Node> nodes = retrieveNodes().execute();
				List<Node> newNodes = nodes.stream()
						.filter(n -> n.getLocation().retrieve().execute().getIdLong() == location.getIdLong()).collect(Collectors.toList());
				return Collections.unmodifiableList(newNodes);
        });
	}

	@Override
	public NodeManager getNodeManager() {
		return new NodeManagerImpl(this);
	}

	@Override
	public PteroAction<List<Allocation>> retrieveAllocationsByNode(Node node) {
		return PteroActionImpl.onExecute(() -> {
			JSONObject json = new PteroActionImpl<JSONObject>(api, Route.Nodes.LIST_ALLOCATIONS.compile("1"),
					(response, request) -> response.getObject()).execute();
			List<Allocation> allocations = new ArrayList<>();
			long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
			for (Object o : json.getJSONArray("data")) {
				JSONObject allocation = new JSONObject(o.toString());
				allocations.add(new AllocationImpl(allocation, this));
			}
			for (int i = 2; i <= pages; i++) {
				JSONObject nextJson = new PteroActionImpl<JSONObject>(api, Route.Nodes.LIST_ALLOCATIONS.compile(Long.toUnsignedString(i)),
						(response, request) -> response.getObject()).execute();
				for (Object o : nextJson.getJSONArray("data")) {
					JSONObject allocation = new JSONObject(o.toString());
					allocations.add(new AllocationImpl(allocation, this));
				}
			}
			return Collections.unmodifiableList(allocations);
		});
	}


	@Override
	public PteroAction<List<Allocation>> retrieveAllocations() {
		return PteroActionImpl.onExecute(() ->
        {
				List<Allocation> allocations = new ArrayList<>();
				List<Node> nodes = retrieveNodes().execute();
				for(Node node : nodes) {
					allocations.addAll(node.getAllocations().retrieve().execute());
				}
				return Collections.unmodifiableList(allocations);
        });
	}


	@Override
	public PteroAction<Allocation> retrieveAllocationById(String id) {
		return PteroActionImpl.onExecute(() -> retrieveAllocations().execute().stream()
						.filter(a -> a.getId().equals(id))
						.findFirst().orElse(null));
	}

	@Override
	public PteroAction<ApplicationEgg> retrieveEggById(Nest nest, String id) {
		return PteroActionImpl.onRequestExecute(api, Route.Nests.GET_EGG.compile(nest.getId(), id),
				(response, request) -> new ApplicationEggImpl(response.getObject(), this));
	}


	@Override
	public PteroAction<List<ApplicationEgg>> retrieveEggs() {
		return PteroActionImpl.onExecute(() ->
        {
				List<Nest> nests = retrieveNests().execute();
				List<ApplicationEgg> eggs = new ArrayList<>();
				for(Nest nest : nests) {
					eggs.addAll(nest.getEggs().get().orElseGet(() -> nest.getEggs().retrieve().execute()));
				}
				return Collections.unmodifiableList(eggs);
        });
	}

	@Override
	public PteroAction<List<ApplicationEgg>> retrieveEggsByNest(Nest nest) {
		return PteroActionImpl.onRequestExecute(api,
				Route.Nests.GET_EGGS.compile(nest.getId()), (response, request) -> {
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
		return PteroActionImpl.onRequestExecute(api, Route.Nests.GET_NEST.compile(id),
				(response, request) -> new NestImpl(response.getObject(), this));
	}

	@Override
	public PteroAction<List<Nest>> retrieveNests() {
		return PteroActionImpl.onExecute(() -> {
			List<Nest> nests = new ArrayList<>();
			JSONObject json = new PteroActionImpl<JSONObject>(api, Route.Nests.LIST_NESTS.compile("1"),
					(response, request) -> response.getObject()).execute();
			long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
			for (Object o : json.getJSONArray("data")) {
				JSONObject nest = new JSONObject(o.toString());
				nests.add(new NestImpl(nest, this));
			}
			for (int i = 2; i <= pages; i++) {
				JSONObject nextJson = new PteroActionImpl<JSONObject>(api,
						Route.Nests.LIST_NESTS.compile(Long.toUnsignedString(i)),
						(response, request) -> response.getObject()).execute();
				for (Object o : nextJson.getJSONArray("data")) {
					JSONObject nest = new JSONObject(o.toString());
					nests.add(new NestImpl(nest, this));
				}
			}
			return Collections.unmodifiableList(nests);
		});
	}

	@Override
	public PteroAction<List<Nest>> retrieveNestsByName(String name, boolean caseSensetive) {
		return PteroActionImpl.onExecute(() ->
        {
				List<Nest> nests = retrieveNests().execute();
				Stream<Nest> newNests = nests.stream();

				if(caseSensetive) {
					newNests = newNests.filter(n -> n.getName().contains(name));
				} else {
					newNests = newNests.filter(n -> n.getName().toLowerCase().contains(name.toLowerCase()));
				}

				return Collections.unmodifiableList(newNests.collect(Collectors.toList()));
        });
	}

	@Override
	public PteroAction<List<Nest>> retrieveNestsByAuthor(String author, boolean caseSensetive) {
		return PteroActionImpl.onExecute(() ->
        {
				List<Nest> nests = retrieveNests().execute();
				Stream<Nest> newNests = nests.stream();

				if (caseSensetive) {
					newNests = newNests.filter(n -> n.getAuthor().contains(author));
				} else {
					newNests = newNests.filter(n -> n.getAuthor().toLowerCase().contains(author.toLowerCase()));
				}

				return Collections.unmodifiableList(newNests.collect(Collectors.toList()));
        });
	}

	@Override
	public PteroAction<List<Location>> retrieveLocations() {
		return PteroActionImpl.onExecute(() -> {
			List<Location> locations = new ArrayList<>();
			JSONObject json = new PteroActionImpl<JSONObject>(api, Route.Locations.LIST_LOCATIONS.compile("1"),
					(response, request) -> response.getObject()).execute();
			long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
			for (Object o : json.getJSONArray("data")) {
				JSONObject location = new JSONObject(o.toString());
				locations.add(new LocationImpl(location, this));
			}
			for (int i = 2; i <= pages; i++) {
				JSONObject nextJson = new PteroActionImpl<JSONObject>(api,
						Route.Locations.LIST_LOCATIONS.compile(Long.toUnsignedString(i)),
						(response, request) -> response.getObject()).execute();
				for (Object o : nextJson.getJSONArray("data")) {
					JSONObject location = new JSONObject(o.toString());
					locations.add(new LocationImpl(location, this));
				}
			}
			return Collections.unmodifiableList(locations);
		});
	}

	@Override
	public PteroAction<Location> retrieveLocationById(String id) {
		return PteroActionImpl.onRequestExecute(api,Route.Locations.GET_LOCATION.compile(id),
				((response, request) -> new LocationImpl(response.getObject(), this)));
	}

	@Override
	public PteroAction<List<Location>> retrieveLocationsByShortCode(String name, boolean caseSensetive) {
		return PteroActionImpl.onExecute(() ->
        {
				List<Location> locations = retrieveLocations().execute();
				Stream<Location> newLocations = locations.stream();

				if(caseSensetive) {
					newLocations = newLocations.filter(l -> l.getShortCode().contains(name));
				} else {
					newLocations = newLocations.filter(l -> l.getShortCode().toLowerCase().contains(name.toLowerCase()));
				}

				return Collections.unmodifiableList(newLocations.collect(Collectors.toList()));
        });
	}

	@Override
	public LocationManager getLocationManager() {
		return new LocationManagerImpl(this);
	}

	@Override
	public PteroAction<List<ApplicationServer>> retrieveServers() {
		return PteroActionImpl.onExecute(() -> {
			List<ApplicationServer> servers = new ArrayList<>();
			JSONObject json = new PteroActionImpl<JSONObject>(api, Route.Servers.LIST_SERVERS.compile("1"),
					(response, request) -> response.getObject()).execute();
			long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
			for (Object o : json.getJSONArray("data")) {
				JSONObject server = new JSONObject(o.toString());
				servers.add(new ApplicationServerImpl(this, server));
			}
			for (int i = 2; i <= pages; i++) {
				JSONObject nextJson = new PteroActionImpl<JSONObject>(api,
						Route.Servers.LIST_SERVERS.compile(Long.toUnsignedString(i)),
						(response, request) -> response.getObject()).execute();
				for (Object o : nextJson.getJSONArray("data")) {
					JSONObject server = new JSONObject(o.toString());
					servers.add(new ApplicationServerImpl(this, server));
				}
			}
			return Collections.unmodifiableList(servers);
		});
	}

	@Override
	public PteroAction<ApplicationServer> retrieveServerById(String id) {
		return PteroActionImpl.onRequestExecute(api, Route.Servers.GET_SERVER.compile(id),
				(response, request) -> new ApplicationServerImpl(this, response.getObject()));
	}

	@Override
	public PteroAction<List<ApplicationServer>> retrieveServersByName(String name, boolean caseSensetive) {
		return PteroActionImpl.onExecute(() -> {
				List<ApplicationServer> servers = retrieveServers().execute();
				Stream<ApplicationServer> newServers = servers.stream();

				if(caseSensetive) {
					newServers = newServers.filter(s -> s.getName().contains(name));
				} else {
					newServers = newServers.filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()));
				}

				return Collections.unmodifiableList(newServers.collect(Collectors.toList()));
        });
	}

	@Override
	public PteroAction<List<ApplicationServer>> retrieveServersByOwner(ApplicationUser user) {
		return PteroActionImpl.onExecute(() ->
        {
				List<ApplicationServer> servers = retrieveServers().execute();
				List<ApplicationServer> newServers = servers.stream()
						.filter(s -> s.getOwner().get()
								.orElseGet(() -> s.getOwner().retrieve().execute()).getIdLong() == user.getIdLong())
						.collect(Collectors.toList());
				return Collections.unmodifiableList(newServers);
        });
	}

	@Override
	public PteroAction<List<ApplicationServer>> retrieveServersByNode(Node node) {
		return PteroActionImpl.onExecute(() ->
        {
				List<ApplicationServer> servers = retrieveServers().execute();
				List<ApplicationServer> newServers = servers.stream()
						.filter(s -> s.getNode().get()
								.orElseGet(() -> s.getNode().retrieve().execute()).getIdLong() == node.getIdLong())
						.collect(Collectors.toList());
				return Collections.unmodifiableList(newServers);
        });
	}

	@Override
	public PteroAction<List<ApplicationServer>> retrieveServersByLocation(Location location) {
		return PteroActionImpl.onExecute(() ->
        {
				List<ApplicationServer> servers = retrieveServers().execute();
				List<ApplicationServer> newServers = servers.stream()
						.filter(s -> s.getNode().retrieve().execute().getLocation().get()
								.orElseGet(() -> s.getNode().retrieve().execute()
										.getLocation().retrieve().execute()).getIdLong() == location.getIdLong())
						.collect(Collectors.toList());
				return Collections.unmodifiableList(newServers);
        });
	}

	@Override
	public ServerCreationAction createServer() {
		return new CreateServerImpl(this);
	}
}
