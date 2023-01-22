/*
 *    Copyright 2021-2023 Matt Malec, and the Pterodactyl4J contributors
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

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientAllocation;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.managers.ClientAllocationManager;
import org.json.JSONObject;

public class ClientAllocationImpl implements ClientAllocation {

	private final JSONObject json;
	private final ClientAllocationManager allocationManager;

	public ClientAllocationImpl(JSONObject json, ClientServer server) {
		this.json = json.getJSONObject("attributes");
		this.allocationManager = server.getAllocationManager();
	}

	@Override
	public String getIP() {
		return json.getString("ip");
	}

	@Override
	public String getAlias() {
		return json.optString("ip_alias", null);
	}

	@Override
	public int getPortInt() {
		return json.getInt("port");
	}

	@Override
	public boolean isDefault() {
		return json.getBoolean("is_default");
	}

	@Override
	public String getNotes() {
		return json.optString("notes", null);
	}

	@Override
	public long getIdLong() {
		return json.getLong("id");
	}

	@Override
	public PteroAction<ClientAllocation> setNote(String note) {
		return allocationManager.setNote(this, note);
	}

	@Override
	public PteroAction<ClientAllocation> setPrimary() {
		return allocationManager.setPrimary(this);
	}

	@Override
	public PteroAction<Void> unassign() {
		return allocationManager.unassignAllocation(this);
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
