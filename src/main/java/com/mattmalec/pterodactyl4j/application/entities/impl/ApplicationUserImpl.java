package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.time.OffsetDateTime;

public class ApplicationUserImpl implements ApplicationUser {

	private final JSONObject json;
	private final Requester requester;

	public ApplicationUserImpl(JSONObject json, Requester requester) {
		this.json = json.getJSONObject("attributes");
		this.requester = requester;
	}

	@Override
	public long getIdLong() {
		return json.getLong("id");
	}

	@Override
	public String getExternalId() {
		return null;
	}

	@Override
	public String getUUID() {
		return json.getString("uuid");
	}

	@Override
	public String getUserName() {
		return json.getString("username");
	}

	@Override
	public String getEmail() {
		return json.getString("email");
	}

	@Override
	public String getFirstName() {
		return json.getString("first_name");
	}

	@Override
	public String getLastName() {
		return json.getString("last_name");
	}

	@Override
	public String getLanguage() {
		return json.getString("language");
	}

	@Override
	public boolean isRootAdmin() {
		return json.getBoolean("root_admin");
	}

	@Override
	public boolean has2FA() {
		return json.getBoolean("2fa");
	}

	@Override
	public OffsetDateTime getCreationDate() {
		return OffsetDateTime.parse(json.optString("created_at"));
	}

	@Override
	public OffsetDateTime getUpdatedDate() {
		return OffsetDateTime.parse(json.optString("updated_at"));
	}

	@Override
	public UserAction edit() {
		return new EditUserImpl(this, requester);
	}

	@Override
	public PteroAction<Void> delete() {
		return new PteroAction<Void>() {
			Route.CompiledRoute route = Route.Users.DELETE_USER.compile(getId());
			@Override
			public Void execute() {
				requester.request(route);
				return null;
			}
		};
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
