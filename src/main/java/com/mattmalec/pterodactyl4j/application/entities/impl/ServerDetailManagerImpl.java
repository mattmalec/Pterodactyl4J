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

import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.application.managers.ServerDetailManager;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractManagerBase;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class ServerDetailManagerImpl extends AbstractManagerBase implements ServerDetailManager {

	private final ApplicationServer server;

	private String name;
	private ApplicationUser owner;
	private String description;
	private String externalId;

	public ServerDetailManagerImpl(ApplicationServer server, PteroApplicationImpl impl) {
		super(impl, Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()));

		this.server = server;
		this.description = server.getDescription();
		this.externalId = server.getExternalId();
	}

	@Override
	public ServerDetailManager remove(long fields) {
		set &= ~fields;

		if ((fields & DESCRIPTION) == DESCRIPTION) this.description = null;
		if ((fields & EXTERNAL_ID) == EXTERNAL_ID) this.externalId = null;
		return this;
	}

	@Override
	public ServerDetailManager remove(long... fields) {
		Checks.notNull(fields, "Fields");

		if (fields.length == 0) return this;
		else if (fields.length == 1) return remove(fields[0]);

		long sum = fields[0];
		for (int i = 1; i < fields.length; i++) sum |= fields[i];

		return remove(sum);
	}

	@Override
	public ServerDetailManager remove() {
		super.reset();
		this.description = null;
		this.externalId = null;
		return this;
	}

	@Override
	public ServerDetailManager setName(String name) {
		Checks.notNull(name, "Name");
		Checks.check(name.length() >= 1 && name.length() <= 191, "Name must be between 1-191 characters long");
		this.name = name;
		return this;
	}

	@Override
	public ServerDetailManager setOwner(ApplicationUser user) {
		Checks.notNull(user, "Owner");
		this.owner = user;
		return this;
	}

	@Override
	public ServerDetailManager setDescription(String description) {
		this.description = description;
		set |= DESCRIPTION;
		return this;
	}

	@Override
	public ServerDetailManager setExternalId(String id) {
		if (id != null)
			Checks.check(id.length() >= 1 && id.length() <= 191, "ID must be between 1-191 characters long");

		this.externalId = id;
		set |= EXTERNAL_ID;
		return this;
	}

	@Override
	protected RequestBody finalizeData() {
		JSONObject obj = new JSONObject()
				.put("name", name == null ? server.getName() : name)
				.put("user", owner == null ? server.getOwnerIdLong() : owner.getId())
				.put("description", shouldUpdate(DESCRIPTION) ? description : null)
				.put("external_id", shouldUpdate(EXTERNAL_ID) ? externalId : null);
		remove();
		return getRequestBody(obj);
	}
}
