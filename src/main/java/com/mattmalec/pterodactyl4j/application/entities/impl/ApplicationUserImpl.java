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

package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Relationed;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.*;

public class ApplicationUserImpl implements ApplicationUser {

	private final JSONObject json;
	private final JSONObject relationships;
	private final PteroApplicationImpl impl;

	public ApplicationUserImpl(JSONObject json, PteroApplicationImpl impl) {
		this.json = json.getJSONObject("attributes");
		this.relationships = json.getJSONObject("attributes").optJSONObject("relationships");
		this.impl = impl;
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
	public UUID getUUID() {
		return UUID.fromString(json.getString("uuid"));
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
	public Relationed<List<ApplicationServer>> getServers() {
		ApplicationUserImpl user = this;
		return new Relationed<List<ApplicationServer>>() {
			@Override
			public PteroAction<List<ApplicationServer>> retrieve() {
				return impl.retrieveServersByOwner(user);
			}

			@Override
			public Optional<List<ApplicationServer>> get() {
				if(!json.has("relationships")) return Optional.empty();
				List<ApplicationServer> servers = new ArrayList<>();
				JSONObject json = relationships.getJSONObject("servers");
				for(Object o : json.getJSONArray("data")) {
					JSONObject server = new JSONObject(o.toString());
					servers.add(new ApplicationServerImpl(impl, server));
				}
				return Optional.of(Collections.unmodifiableList(servers));
			}
		};
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
		return new EditUserImpl(this, impl);
	}

	@Override
	public PteroAction<Void> delete() {
		return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Users.DELETE_USER.compile(getId()));
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
