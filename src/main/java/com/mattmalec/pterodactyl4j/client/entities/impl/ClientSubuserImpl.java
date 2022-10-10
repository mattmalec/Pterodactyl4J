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

package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.Permission;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.UUID;
import org.json.JSONObject;

public class ClientSubuserImpl implements ClientSubuser {

	private final JSONObject json;

	public ClientSubuserImpl(JSONObject json) {
		this.json = json.getJSONObject("attributes");
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
	public String getImage() {
		return json.getString("image");
	}

	@Override
	public boolean has2FA() {
		return json.getBoolean("2fa_enabled");
	}

	@Override
	public EnumSet<Permission> getPermissions() {
		EnumSet<Permission> perms = EnumSet.noneOf(Permission.class);
		for (Object o : json.getJSONArray("permissions")) {
			perms.add(Permission.ofRaw(o.toString()));
		}
		return perms;
	}

	@Override
	public boolean hasPermission(Permission... permission) {
		return getPermissions().containsAll(Arrays.asList(permission));
	}

	@Override
	public UUID getUUID() {
		return UUID.fromString(json.getString("uuid"));
	}

	@Override
	public OffsetDateTime getCreationDate() {
		return OffsetDateTime.parse(json.getString("created_at"));
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
