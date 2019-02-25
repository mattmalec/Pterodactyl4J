package com.mattmalec.pterodactyl4j.application;

import com.mattmalec.pterodactyl4j.PteroAPI;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.User;
import com.mattmalec.pterodactyl4j.application.entities.impl.UserImpl;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;


public class PteroApplicationImpl implements PteroAPI {

	private String token;
	private final Requester requester = new Requester(this);
	private String applicationUrl;

	public PteroApplicationImpl(String applicationUrl, String token) {
		this.token = token;
		this.applicationUrl = applicationUrl;

	}
	public PteroApplicationImpl() { }

	@Override
	public String getToken() {
		return token;
	}

	@Override
	public Requester getRequester() {
		return requester;
	}

	@Override
	public String getApplicationUrl() {
		return applicationUrl;
	}

	@Override
	public PteroApplicationImpl setToken(String token) {
		this.token = token;
		return this;
	}

	@Override
	public PteroApplicationImpl setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
		return this;
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
}
