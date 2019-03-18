package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAPI;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.application.entities.User;
import com.mattmalec.pterodactyl4j.application.managers.AllocationManager;
import com.mattmalec.pterodactyl4j.application.managers.LocationManager;
import com.mattmalec.pterodactyl4j.application.managers.NodeManager;
import com.mattmalec.pterodactyl4j.application.managers.UserManager;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PteroApplicationImpl implements PteroApplication, PteroAPI {

	private String token;
	private Requester requester;
	private String applicationUrl;

	public PteroApplicationImpl(String applicationUrl, String token) {
		this.token = token;
		this.applicationUrl = applicationUrl;
		this.requester = new Requester(this);
	}

	@Override
	public String getToken() {
		return this.token;
	}

	@Override
	public Requester getRequester() {
		return this.requester;
	}

	@Override
	public String getApplicationUrl() {
		return this.applicationUrl;
	}

	public PteroAction<User> retrieveUserById(String id) {
		return retrieveUserById(Long.parseLong(id));
	}

	public PteroAction<User> retrieveUserById(long id) {
		Route.CompiledRoute route = Route.Users.GET_USER.compile(Long.toUnsignedString(id));
		return new PteroAction<User>() {

			@Override
			public User execute() {
				JSONObject jsonObject = requester.request(route).toJSONObject();
				return new UserImpl(jsonObject);
			}
		};
	}

	@Override
	public PteroAction<List<User>> retrieveUsers() {
		return new PteroAction<List<User>>() {
			@Override
			public List<User> execute() {
				Route.CompiledRoute route = Route.Users.LIST_USERS.compile("1");
				JSONObject json = requester.request(route).toJSONObject();
				long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
				List<User> users = new ArrayList<>();
				for(Object o : json.getJSONArray("data")) {
					JSONObject user = new JSONObject(o.toString());
					users.add(new UserImpl(user));
				}
				for(int i=1; i < pages; i++) {
					Route.CompiledRoute nextRoute = Route.Users.LIST_USERS.compile(Long.toUnsignedString(pages));
					JSONObject nextJson = requester.request(nextRoute).toJSONObject();
					for(Object o : nextJson.getJSONArray("data")) {
						JSONObject user = new JSONObject(o.toString());
						users.add(new UserImpl(user));
					}
				}
				return Collections.unmodifiableList(users);
			}
		};
	}

	@Override
	public PteroAction<List<User>> retrieveUsersByUsername(String name, boolean caseSensetive) {
		return new PteroAction<List<User>>() {
			@Override
			public List<User> execute() {
				List<User> users = retrieveUsers().execute();
				List<User> newUsers = new ArrayList<>();
				for (User u : users) {
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
	public PteroAction<List<User>> retrieveUsersByEmail(String name, boolean caseSensetive) {
		return new PteroAction<List<User>>() {
			@Override
			public List<User> execute() {
				List<User> users = retrieveUsers().execute();
				List<User> newUsers = new ArrayList<>();
				for (User u : users) {
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
					Route.CompiledRoute nextRoute = Route.Nodes.LIST_NODES.compile(Long.toUnsignedString(pages));
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
	public AllocationManager getAllocationManager() {
		return new AllocationManagerImpl(this);
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
					Route.CompiledRoute nextRoute = Route.Locations.LIST_LOCATIONS.compile(Long.toUnsignedString(pages));
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
}
