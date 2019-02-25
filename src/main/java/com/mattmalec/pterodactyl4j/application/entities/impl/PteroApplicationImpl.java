package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAPI;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.application.entities.User;
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
	public PteroApplicationImpl() { }

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
			JSONObject jsonObject = requester.request(route).toJSONObject();
			@Override
			public User execute() {
				return new UserImpl(jsonObject);
			}
		};
	}

	@Override
	public PteroAction<List<User>> retrieveUsers() {
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
		return new PteroAction<List<User>>() {
			@Override
			public List<User> execute() {
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
	public UserManager getUserManager() {
		return new UserManagerImpl(this.requester);
	}

	@Override
	public PteroAction<List<Node>> retrieveNodes() {
		return null;
	}

	@Override
	public PteroAction<Node> retrieveNodeById(String id) {
		return null;
	}

	@Override
	public PteroAction<Node> retrieveNodeById(long id) {
		return null;
	}

	@Override
	public PteroAction<List<Node>> retrieveNodesByName(String name, boolean caseSensetive) {
		return null;
	}
}
