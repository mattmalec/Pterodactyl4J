package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.*;
import com.mattmalec.pterodactyl4j.application.managers.LocationManager;
import com.mattmalec.pterodactyl4j.application.managers.NodeManager;
import com.mattmalec.pterodactyl4j.application.managers.ServerAction;
import com.mattmalec.pterodactyl4j.application.managers.UserManager;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


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
		Route.CompiledRoute route = Route.Users.GET_USER.compile(Long.toUnsignedString(id));
		return new PteroAction<ApplicationUser>() {

			@Override
			public ApplicationUser execute() {
				JSONObject jsonObject = requester.request(route).toJSONObject();
				return new ApplicationUserImpl(jsonObject, requester);
			}
		};
	}

	@Override
	public PteroAction<List<ApplicationUser>> retrieveUsers() {
		return new PteroAction<List<ApplicationUser>>() {
			@Override
			public List<ApplicationUser> execute() {
				Route.CompiledRoute route = Route.Users.LIST_USERS.compile("1");
				JSONObject json = requester.request(route).toJSONObject();
				long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
				List<ApplicationUser> users = new ArrayList<>();
				for(Object o : json.getJSONArray("data")) {
					JSONObject user = new JSONObject(o.toString());
					users.add(new ApplicationUserImpl(user, requester));
				}
				for(int i=1; i < pages; i++) {
					Route.CompiledRoute nextRoute = Route.Users.LIST_USERS.compile(Long.toUnsignedString(i));
					JSONObject nextJson = requester.request(nextRoute).toJSONObject();
					for(Object o : nextJson.getJSONArray("data")) {
						JSONObject user = new JSONObject(o.toString());
						users.add(new ApplicationUserImpl(user, requester));
					}
				}
				return Collections.unmodifiableList(users);
			}
		};
	}

	@Override
	public PteroAction<List<ApplicationUser>> retrieveUsersByUsername(String name, boolean caseSensetive) {
		return new PteroAction<List<ApplicationUser>>() {
			@Override
			public List<ApplicationUser> execute() {
				List<ApplicationUser> users = retrieveUsers().execute();
				List<ApplicationUser> newUsers = new ArrayList<>();
				for (ApplicationUser u : users) {
					if (caseSensetive) {
						if (u.getUserName().contains(name))
							newUsers.add(u);
					} else {
						if (u.getUserName().toLowerCase().contains(name.toLowerCase()))
							newUsers.add(u);
					}
				}
				return Collections.unmodifiableList(newUsers);
			}
		};
	}

	@Override
	public PteroAction<List<ApplicationUser>> retrieveUsersByEmail(String name, boolean caseSensetive) {
		return new PteroAction<List<ApplicationUser>>() {
			@Override
			public List<ApplicationUser> execute() {
				List<ApplicationUser> users = retrieveUsers().execute();
				List<ApplicationUser> newUsers = new ArrayList<>();
				for (ApplicationUser u : users) {
					if (caseSensetive) {
						if (u.getEmail().contains(name))
							newUsers.add(u);
					} else {
						if (u.getEmail().toLowerCase().contains(name.toLowerCase()))
							newUsers.add(u);
					}
				}
				return Collections.unmodifiableList(newUsers);
			}
		};
	}

	@Override
	public UserManager getUserManager() {
		return new UserManagerImpl(this.requester);
	}

	@Override
	public PteroAction<List<Node>> retrieveNodes() {
		PteroApplicationImpl impl = this;
		return new PteroAction<List<Node>>() {
			@Override
			public List<Node> execute() {
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
			}
		};
	}

	@Override
	public PteroAction<Node> retrieveNodeById(String id) {
		return retrieveNodeById(Long.parseLong(id));
	}

	@Override
	public PteroAction<Node> retrieveNodeById(long id) {
		PteroApplicationImpl impl = this;
		Route.CompiledRoute route = Route.Nodes.GET_NODE.compile(Long.toUnsignedString(id));
		return new PteroAction<Node>() {
			@Override
			public Node execute() {
				JSONObject jsonObject = requester.request(route).toJSONObject();
				return new NodeImpl(jsonObject, impl);
			}
		};
	}

	@Override
	public PteroAction<List<Node>> retrieveNodesByName(String name, boolean caseSensetive) {
		return new PteroAction<List<Node>>() {
			@Override
			public List<Node> execute() {
				List<Node> nodes = retrieveNodes().execute();
				List<Node> newNodes = new ArrayList<>();
				for (Node n : nodes) {
					if (caseSensetive) {
						if (n.getName().contains(name))
							newNodes.add(n);
					} else {
						if (n.getName().toLowerCase().contains(name.toLowerCase()))
							newNodes.add(n);
					}
				}
				return Collections.unmodifiableList(newNodes);
			}
		};
	}

	@Override
	public NodeManager getNodeManager() {
		return new NodeManagerImpl(this);
	}

	@Override
	public PteroAction<List<Allocation>> retrieveAllocationsByNode(Node node) {
		return new PteroAction<List<Allocation>>() {
			@Override
			public List<Allocation> execute() {
				List<Allocation> allocations = node.getAllocations();
				return Collections.unmodifiableList(allocations);
			}
		};
	}


	@Override
	public PteroAction<List<Allocation>> retrieveAllocations() {
		return new PteroAction<List<Allocation>>() {
			@Override
			public List<Allocation> execute() {
				List<Allocation> allocations = new ArrayList<>();
				List<Node> nodes = retrieveNodes().execute();
				for(Node node : nodes) {
					allocations.addAll(node.getAllocations());
				}
				return Collections.unmodifiableList(allocations);
			}
		};
	}


	@Override
	public PteroAction<Allocation> retrieveAllocationById(String id) {
		return new PteroAction<Allocation>() {
			@Override
			public Allocation execute() {
				List<Allocation> allocations = retrieveAllocations().execute();
				for(Allocation allocation : allocations) {
					if(allocation.getId().equals(id)) {
						return allocation;
					}
				}
				return null;
			}
		};
	}

	@Override
	public PteroAction<Allocation> retrieveAllocationById(long id) {
		return retrieveAllocationById(Long.toUnsignedString(id));
	}
	@Override
	public PteroAction<Egg> retrieveEggById(Nest nest, String id) {
		PteroApplicationImpl impl = this;
		return new PteroAction<Egg>() {
			@Override
			public Egg execute() {
				Route.CompiledRoute route = Route.Nests.GET_EGG.compile(nest.getId(), id);
				JSONObject json = requester.request(route).toJSONObject();
				return new EggImpl(json, impl);
			}
		};
	}

	@Override
	public PteroAction<Egg> retrieveEggById(Nest nest, long id) {
		return retrieveEggById(nest, Long.toUnsignedString(id));
	}


	@Override
	public PteroAction<List<Egg>> retrieveEggs() {
		return new PteroAction<List<Egg>>() {
			@Override
			public List<Egg> execute() {
				List<Nest> nests = retrieveNests().execute();
				List<Egg> eggs = new ArrayList<>();
				for(Nest nest : nests) {
					eggs.addAll(nest.retrieveEggs().execute());
				}
				return Collections.unmodifiableList(eggs);
			}
		};
	}

	@Override
	public PteroAction<List<Egg>> retrieveEggsByNest(Nest nest) {
		PteroApplicationImpl impl = this;
		return new PteroAction<List<Egg>>() {
			@Override
			public List<Egg> execute() {
				Route.CompiledRoute route = Route.Nests.GET_EGGS.compile(nest.getId());
				JSONObject json = requester.request(route).toJSONObject();
				List<Egg> eggs = new ArrayList<>();
				for (Object o : json.getJSONArray("data")) {
					JSONObject egg = new JSONObject(o.toString());
					eggs.add(new EggImpl(egg, impl));
				}
				return Collections.unmodifiableList(eggs);
			}
		};
	}

	@Override
	public PteroAction<Nest> retrieveNestById(String id) {
		return retrieveNestById(Long.parseLong(id));
	}

	@Override
	public PteroAction<Nest> retrieveNestById(long id) {
		PteroApplicationImpl impl = this;
		return new PteroAction<Nest>() {
			Route.CompiledRoute route = Route.Nests.GET_NEST.compile(Long.toUnsignedString(id));
			@Override
			public Nest execute() {
				JSONObject jsonObject = requester.request(route).toJSONObject();
				return new NestImpl(jsonObject, impl);
			}
		};
	}

	@Override
	public PteroAction<List<Nest>> retrieveNests() {
		PteroApplicationImpl impl = this;
		return new PteroAction<List<Nest>>() {
			@Override
			public List<Nest> execute() {
				Route.CompiledRoute route = Route.Nests.LIST_NESTS.compile("1");
				JSONObject json = requester.request(route).toJSONObject();
				long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
				List<Nest> nests = new ArrayList<>();
				for (Object o : json.getJSONArray("data")) {
					JSONObject nest = new JSONObject(o.toString());
					nests.add(new NestImpl(nest, impl));
				}
				for (int i = 1; i < pages; i++) {
					Route.CompiledRoute nextRoute = Route.Nests.LIST_NESTS.compile(Long.toUnsignedString(i));
					JSONObject nextJson = requester.request(nextRoute).toJSONObject();
					for (Object o : nextJson.getJSONArray("data")) {
						JSONObject nest = new JSONObject(o.toString());
						nests.add(new NestImpl(nest, impl));
					}
				}
				return Collections.unmodifiableList(nests);
			}
		};
	}

	@Override
	public PteroAction<List<Nest>> retrieveNestsByName(String name, boolean caseSensetive) {
		return new PteroAction<List<Nest>>() {
			@Override
			public List<Nest> execute() {
				List<Nest> nests = retrieveNests().execute();
				List<Nest> newNests = new ArrayList<>();
				for (Nest n : nests) {
					if (caseSensetive) {
						if (n.getName().contains(name))
							newNests.add(n);
					} else {
						if (n.getName().toLowerCase().contains(name.toLowerCase()))
							newNests.add(n);
					}
				}
				return Collections.unmodifiableList(newNests);
			}
		};
	}

	@Override
	public PteroAction<List<Nest>> retrieveNestsByAuthor(String author, boolean caseSensetive) {
		return new PteroAction<List<Nest>>() {
			@Override
			public List<Nest> execute() {
				List<Nest> nests = retrieveNests().execute();
				List<Nest> newNests = new ArrayList<>();
				for (Nest n : nests) {
					if (caseSensetive) {
						if (n.getAuthor().contains(author))
							newNests.add(n);
					} else {
						if (n.getAuthor().toLowerCase().contains(author.toLowerCase()))
							newNests.add(n);
					}
				}
				return Collections.unmodifiableList(newNests);
			}
		};
	}

	@Override
	public PteroAction<List<Location>> retrieveLocations() {
		PteroApplicationImpl impl = this;
		return new PteroAction<List<Location>>() {
			@Override
			public List<Location> execute() {
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
			}
		};
	}

	@Override
	public PteroAction<Location> retrieveLocationById(String id) {
		return retrieveLocationById(Long.parseLong(id));
	}

	@Override
	public PteroAction<Location> retrieveLocationById(long id) {
		PteroApplicationImpl impl = this;
		Route.CompiledRoute route = Route.Locations.GET_LOCATION.compile(Long.toUnsignedString(id));
		return new PteroAction<Location>() {
			@Override
			public Location execute() {
				JSONObject jsonObject = requester.request(route).toJSONObject();
				return new LocationImpl(jsonObject, impl);
			}
		};
	}

	@Override
	public PteroAction<List<Location>> retrieveLocationsByShortCode(String name, boolean caseSensetive) {
		return new PteroAction<List<Location>>() {
			@Override
			public List<Location> execute() {
				List<Location> locations = retrieveLocations().execute();
				List<Location> newLocations = new ArrayList<>();
				for (Location l : locations) {
					if (caseSensetive) {
						if (l.getShortCode().contains(name))
							newLocations.add(l);
					} else {
						if (l.getShortCode().toLowerCase().contains(name.toLowerCase()))
							newLocations.add(l);
					}
				}
				return Collections.unmodifiableList(newLocations);
			}
		};
	}

	@Override
	public LocationManager getLocationManager() {
		return new LocationManagerImpl(this, this.requester);
	}

	@Override
	public PteroAction<List<ApplicationServer>> retrieveServers() {
		PteroApplicationImpl impl = this;
		return new PteroAction<List<ApplicationServer>>() {
			@Override
			public List<ApplicationServer> execute() {
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
			}
		};
	}

	@Override
	public PteroAction<ApplicationServer> retrieveServerById(String id) {
		PteroApplicationImpl impl = this;
		Route.CompiledRoute route = Route.Servers.GET_SERVER.compile(id);
		return new PteroAction<ApplicationServer>() {
			@Override
			public ApplicationServer execute() {
				JSONObject jsonObject = requester.request(route).toJSONObject();
				return new ApplicationServerImpl(impl, jsonObject);
			}
		};
	}

	@Override
	public PteroAction<ApplicationServer> retrieveServerById(long id) {
		return retrieveServerById(Long.toUnsignedString(id));
	}

	@Override
	public PteroAction<List<ApplicationServer>> retrieveServersByName(String name, boolean caseSensetive) {
		return new PteroAction<List<ApplicationServer>>() {
			@Override
			public List<ApplicationServer> execute() {
				List<ApplicationServer> servers = retrieveServers().execute();
				List<ApplicationServer> newServers = new ArrayList<>();
				for (ApplicationServer s : servers) {
					if (caseSensetive) {
						if (s.getName().contains(name))
							newServers.add(s);
					} else {
						if (s.getName().toLowerCase().contains(name.toLowerCase()))
							newServers.add(s);
					}
				}
				return Collections.unmodifiableList(newServers);
			}
		};
	}

	@Override
	public PteroAction<List<ApplicationServer>> retrieveServersByOwner(ApplicationUser user) {
		return new PteroAction<List<ApplicationServer>>() {
			@Override
			public List<ApplicationServer> execute() {
				List<ApplicationServer> servers = retrieveServers().execute();
				List<ApplicationServer> newServers = new ArrayList<>();
				for (ApplicationServer s : servers) {
					ApplicationUser owner = s.getOwner().get().orElseGet(() -> s.getOwner().retrieve().execute());
					if (owner.getIdLong() == user.getIdLong()) {
						newServers.add(s);
					}
				}
				return Collections.unmodifiableList(newServers);
			}
		};
	}

	@Override
	public ServerAction createServer() {
		return new CreateServerImpl(this);
	}
}
