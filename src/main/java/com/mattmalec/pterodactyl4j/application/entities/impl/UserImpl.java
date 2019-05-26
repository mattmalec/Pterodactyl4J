package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.User;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class UserImpl implements User {

	private JSONObject json;
	private Requester requester;

	public UserImpl(JSONObject json, Requester requester) {
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
	public String getFullName() {
		return String.format("%s %s", getFirstName(), getLastName());
	}

	@Override
	public String getLanguage() {
		return json.getString("language");
	}

	@Override
	public Locale getLocale() {
		return Locale.forLanguageTag(getLanguage());
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
		return LocalDateTime.parse(json.optString("created_at"), DateTimeFormatter.ISO_LOCAL_DATE_TIME).atOffset(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
	}

	@Override
	public OffsetDateTime getUpdatedDate() {
		return LocalDateTime.parse(json.optString("updated_at"), DateTimeFormatter.ISO_LOCAL_DATE_TIME).atOffset(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
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
