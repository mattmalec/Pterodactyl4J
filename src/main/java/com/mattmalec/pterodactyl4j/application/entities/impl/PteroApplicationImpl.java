package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.*;
import com.mattmalec.pterodactyl4j.application.managers.LocationManager;
import com.mattmalec.pterodactyl4j.application.managers.NodeManager;
import com.mattmalec.pterodactyl4j.application.managers.ServerAction;
import com.mattmalec.pterodactyl4j.application.managers.UserManager;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PteroApplicationImpl implements PteroApplication {

	private Requester requester;

	public PteroApplicationImpl(Requester requester) {
		this.requester = requester;
	}

	public Requester getRequester() {
		return this.requester;
	}

	public PteroAction<ApplicationUser> retrieveUserById(String id) {
		return retrieveUserById(Long.parseLong(id));
	}

	public PteroAction<ApplicationUser> retrieveUserById(long id) {
		PteroApplicationImpl impl = this;
		Route.CompiledRoute route = Route.Users.GET_USER.compile(Long.toUnsignedString(id));
		return PteroActionImpl.onExecute(() ->
        {
			JSONObject jsonObject = requester.request(route).toJSONObject();
			return new ApplicationUserImpl(jsonObject, impl);
		});
	}

	@Override
	public PteroAction<List<ApplicationUser>> retrieveUsers() {
		PteroApplicationImpl impl = this;
		return PteroActionImpl.onExecute(() ->
        {
				Route.CompiledRoute route = Route.Users.LIST_USERS.compile("1");
				JSONObject json = requester.request(route).toJSONObject();
				long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
				List<ApplicationUser> users = new ArrayList<>();
				for(Object o : json.getJSONArray("data")) {
					JSONObject user = new JSONObject(o.toString());
					users.add(new ApplicationUserImpl(user, impl));
				}
				for(int i=1; i < pages; i++) {
					Route.CompiledRoute nextRoute = Route.Users.LIST_USERS.compile(Long.toUnsignedString(i));
					JSONObject nextJson = requester.request(nextRoute).toJSONObject();
					for(Object o : nextJson.getJSONArray("data")) {
						JSONObject user = new JSONObject(o.toString());
						users.add(new ApplicationUserImpl(user, impl));
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
		PteroApplicationImpl impl = this;
		return PteroActionImpl.onExecute(() ->
        {
				Route.CompiledRoute route = Route.Nodes.LIST_NODES.compile("1");
				JSONObject json = requester.request(route).toJSONObject();
				long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
				List<Node> nodes = new ArrayList<>();
				for(Object o : json.getJSONArray("data")) {
					JSONObject node = new JSONObject(o.toString());
					nodes.add(new NodeImpl(node, impl));
				}
				for(int i=1; i < pages; i++) {
					Route.CompiledRoute nextRoute = Route.Nodes.LIST_NODES.compile(Long.toUnsignedString(i));
					JSONObject nextJson = requester.request(nextRoute).toJSONObject();
					for(Object o : nextJson.getJSONArray("data")) {
						JSONObject node = new JSONObject(o.toString());
						nodes.add(new NodeImpl(node, impl));
					}
				}
				return Collections.unmodifiableList(nodes);
        });
	}

	@Override
	public PteroAction<Node> retrieveNodeById(String id) {
		return retrieveNodeById(Long.parseLong(id));
	}

	@Override
	public PteroAction<Node> retrieveNodeById(long id) {
		PteroApplicationImpl impl = this;
		Route.CompiledRoute route = Route.Nodes.GET_NODE.compile(Long.toUnsignedString(id));
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject jsonObject = requester.request(route).toJSONObject();
				return new NodeImpl(jsonObject, impl);
        });
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
				List<Node> newNodes = nodes.stream().filter(n -> n.getLocation().retrieve().execute().getIdLong() == location.getIdLong()).collect(Collectors.toList());
				return Collections.unmodifiableList(newNodes);
        });
	}

	@Override
	public NodeManager getNodeManager() {
		return new NodeManagerImpl(this);
	}

	@Override
	public PteroAction<List<Allocation>> retrieveAllocationsByNode(Node node) {
		PteroApplicationImpl impl = this;
		return PteroActionImpl.onExecute(() ->
        {
				Route.CompiledRoute route = Route.Nodes.LIST_ALLOCATIONS.compile(node.getId(), "1");
				JSONObject json = requester.request(route).toJSONObject();
				long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
				List<Allocation> allocations = new ArrayList<>();
				for (Object o : json.getJSONArray("data")) {
					JSONObject allocation = new JSONObject(o.toString());
					allocations.add(new AllocationImpl(allocation, impl));
				}
				for (int i = 2; i <= pages; i++) {
					Route.CompiledRoute nextRoute = Route.Nodes.LIST_ALLOCATIONS.compile(node.getId(), Long.toUnsignedString(i));
					JSONObject nextJson = requester.request(nextRoute).toJSONObject();
					for (Object o : nextJson.getJSONArray("data")) {
						JSONObject allocation = new JSONObject(o.toString());
						allocations.add(new AllocationImpl(allocation, impl));
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
		return PteroActionImpl.onExecute(() ->
        {
				List<Allocation> allocations = retrieveAllocations().execute();
				for(Allocation allocation : allocations) {
					if(allocation.getId().equals(id)) {
						return allocation;
					}
				}
				return null;
        });
	}

	@Override
	public PteroAction<Allocation> retrieveAllocationById(long id) {
		return retrieveAllocationById(Long.toUnsignedString(id));
	}
	@Override
	public PteroAction<ApplicationEgg> retrieveEggById(Nest nest, String id) {
		PteroApplicationImpl impl = this;
		return PteroActionImpl.onExecute(() ->
        {
				Route.CompiledRoute route = Route.Nests.GET_EGG.compile(nest.getId(), id);
				JSONObject json = requester.request(route).toJSONObject();
				return new ApplicationEggImpl(json, impl);
        });
	}

	@Override
	public PteroAction<ApplicationEgg> retrieveEggById(Nest nest, long id) {
		return retrieveEggById(nest, Long.toUnsignedString(id));
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
		PteroApplicationImpl impl = this;
		return PteroActionImpl.onExecute(() ->
        {
				Route.CompiledRoute route = Route.Nests.GET_EGGS.compile(nest.getId());
				JSONObject json = requester.request(route).toJSONObject();
				List<ApplicationEgg> eggs = new ArrayList<>();
				for (Object o : json.getJSONArray("data")) {
					JSONObject egg = new JSONObject(o.toString());
					eggs.add(new ApplicationEggImpl(egg, impl));
				}
				return Collections.unmodifiableList(eggs);
        });
	}

	@Override
	public PteroAction<Nest> retrieveNestById(String id) {
		return retrieveNestById(Long.parseLong(id));
	}

	@Override
	public PteroAction<Nest> retrieveNestById(long id) {
		PteroApplicationImpl impl = this;
		return PteroActionImpl.onExecute(() -> {
			Route.CompiledRoute route = Route.Nests.GET_NEST.compile(Long.toUnsignedString(id));
			JSONObject jsonObject = requester.request(route).toJSONObject();
			return new NestImpl(jsonObject, impl);
        });
	}

	@Override
	public PteroAction<List<Nest>> retrieveNests() {
		PteroApplicationImpl impl = this;
		return PteroActionImpl.onExecute(() ->
        {
				Route.CompiledRoute route = Route.Nests.LIST_NESTS.compile("1");
				JSONObject json = requester.request(route).toJSONObject();
				long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
				List<Nest> nests = new ArrayList<>();
				for (Object o : json.getJSONArray("data")) {
					JSONObject nest = new JSONObject(o.toString());
					nests.add(new NestImpl(nest, impl));
				}
				for (int i = 2; i <= pages; i++) {
					Route.CompiledRoute nextRoute = Route.Nests.LIST_NESTS.compile(Long.toUnsignedString(i));
					JSONObject nextJson = requester.request(nextRoute).toJSONObject();
					for (Object o : nextJson.getJSONArray("data")) {
						JSONObject nest = new JSONObject(o.toString());
						nests.add(new NestImpl(nest, impl));
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
		PteroApplicationImpl impl = this;
		return PteroActionImpl.onExecute(() ->
        {
				Route.CompiledRoute route = Route.Locations.LIST_LOCATIONS.compile("1");
				JSONObject json = requester.request(route).toJSONObject();
				long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
				List<Location> locations = new ArrayList<>();
				for(Object o : json.getJSONArray("data")) {
					JSONObject location = new JSONObject(o.toString());
					locations.add(new LocationImpl(location, impl));
				}
				for(int i=1; i < pages; i++) {
					Route.CompiledRoute nextRoute = Route.Locations.LIST_LOCATIONS.compile(Long.toUnsignedString(i));
					JSONObject nextJson = requester.request(nextRoute).toJSONObject();
					for(Object o : nextJson.getJSONArray("data")) {
						JSONObject location = new JSONObject(o.toString());
						locations.add(new LocationImpl(location, impl));
					}
				}
				return Collections.unmodifiableList(locations);
        });
	}

	@Override
	public PteroAction<Location> retrieveLocationById(String id) {
		return retrieveLocationById(Long.parseLong(id));
	}

	@Override
	public PteroActionImpl<Location> retrieveLocationById(long id) {
		PteroApplicationImpl impl = this;
		Route.CompiledRoute route = Route.Locations.GET_LOCATION.compile(Long.toUnsignedString(id));
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject jsonObject = requester.request(route).toJSONObject();
				return new LocationImpl(jsonObject, impl);
        });
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
		return new LocationManagerImpl(this, this.requester);
	}

	@Override
	public PteroAction<List<ApplicationServer>> retrieveServers() {
		PteroApplicationImpl impl = this;
		return PteroActionImpl.onExecute(() ->
        {
				Route.CompiledRoute route = Route.Servers.LIST_SERVERS.compile("1");
				JSONObject json = requester.request(route).toJSONObject();
				long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
				List<ApplicationServer> servers = new ArrayList<>();
				for(Object o : json.getJSONArray("data")) {
					JSONObject server = new JSONObject(o.toString());
					servers.add(new ApplicationServerImpl(impl, server));
				}
				for(int i=1; i < pages; i++) {
					Route.CompiledRoute nextRoute = Route.Servers.LIST_SERVERS.compile(Long.toUnsignedString(i));
					JSONObject nextJson = requester.request(nextRoute).toJSONObject();
					for(Object o : nextJson.getJSONArray("data")) {
						JSONObject server = new JSONObject(o.toString());
						servers.add(new ApplicationServerImpl(impl, server));
					}
				}
				return Collections.unmodifiableList(servers);
        });
	}

	@Override
	public PteroAction<ApplicationServer> retrieveServerById(String id) {
		PteroApplicationImpl impl = this;
		Route.CompiledRoute route = Route.Servers.GET_SERVER.compile(id);
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject jsonObject = requester.request(route).toJSONObject();
				return new ApplicationServerImpl(impl, jsonObject);
        });
	}

	@Override
	public PteroAction<ApplicationServer> retrieveServerById(long id) {
		return retrieveServerById(Long.toUnsignedString(id));
	}

	@Override
	public PteroAction<List<ApplicationServer>> retrieveServersByName(String name, boolean caseSensetive) {
		return PteroActionImpl.onExecute(() ->
        {
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
								.orElseGet(() -> s.getNode().retrieve().execute().getLocation().retrieve().execute()).getIdLong() == location.getIdLong())
						.collect(Collectors.toList());
				return Collections.unmodifiableList(newServers);
        });
	}

	@Override
	public ServerAction createServer() {
		return new CreateServerImpl(this);
	}
}
