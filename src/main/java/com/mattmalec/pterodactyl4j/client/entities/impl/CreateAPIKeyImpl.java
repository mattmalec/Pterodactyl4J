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

import com.mattmalec.pterodactyl4j.client.entities.APIKey;
import com.mattmalec.pterodactyl4j.client.managers.APIKeyAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class CreateAPIKeyImpl extends PteroActionImpl<APIKey> implements APIKeyAction {

	private String description;
	private final Set<String> allowedIps;

	public CreateAPIKeyImpl(PteroClientImpl impl) {
		super(
				impl.getP4J(),
				Route.Accounts.CREATE_API_KEY.compile(),
				(response, request) -> new APIKeyImpl(response.getObject(), impl));
		this.allowedIps = new HashSet<>();
	}

	@Override
	public APIKeyAction setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public APIKeyAction setAllowedIps(String... ips) {
		allowedIps.clear();
		return addAllowedIps(ips);
	}

	@Override
	public APIKeyAction addAllowedIps(String... ips) {
		allowedIps.addAll(Arrays.asList(ips));
		return this;
	}

	@Override
	public APIKeyAction addAllowedIp(String ip) {
		allowedIps.add(ip);
		return this;
	}

	@Override
	public APIKeyAction removeAllowedIp(String ip) {
		allowedIps.remove(ip);
		return this;
	}

	@Override
	public APIKeyAction removeAllowedIps(String... ips) {
		Arrays.asList(ips).forEach(allowedIps::remove);
		return this;
	}

	@Override
	protected RequestBody finalizeData() {
		Checks.notBlank(description, "Description");
		Checks.check(description.length() <= 500, "The description cannot be over 500 characters");

		JSONObject json = new JSONObject().put("description", description);

		if (!allowedIps.isEmpty()) json.put("allowed_ips", allowedIps);

		return getRequestBody(json);
	}
}
