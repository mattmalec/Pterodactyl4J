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

package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class AccountManager {

	private final PteroClientImpl impl;

	public AccountManager(PteroClientImpl impl) {
		this.impl = impl;
	}

	public PteroAction<String> get2FAImage() {
		return new PteroActionImpl<>(
				impl.getP4J(), Route.Accounts.GET_2FA_CODE.compile(), (response, request) -> response.getObject()
						.getJSONObject("data")
						.getString("image_url_data"));
	}

	public PteroAction<Set<String>> enable2FA(int code) {
		JSONObject obj = new JSONObject().put("code", code);
		return new PteroActionImpl<>(
				impl.getP4J(),
				Route.Accounts.ENABLE_2FA.compile(),
				PteroActionImpl.getRequestBody(obj),
				(response, request) -> Collections.unmodifiableSet(
						response.getObject().getJSONObject("attributes").getJSONArray("tokens").toList().stream()
								.map(Object::toString)
								.collect(Collectors.toSet())));
	}

	public PteroAction<Void> disable2FA(String password) {
		JSONObject obj = new JSONObject().put("password", password);
		return PteroActionImpl.onRequestExecute(
				impl.getP4J(), Route.Accounts.DISABLE_2FA.compile(), PteroActionImpl.getRequestBody(obj));
	}

	public PteroAction<Void> updateEmail(String newEmail, String password) {
		JSONObject obj = new JSONObject().put("email", newEmail).put("password", password);
		return PteroActionImpl.onRequestExecute(
				impl.getP4J(), Route.Accounts.UPDATE_EMAIL.compile(), PteroActionImpl.getRequestBody(obj));
	}

	public PteroAction<Void> updatePassword(String currentPassword, String newPassword) {
		JSONObject obj = new JSONObject()
				.put("current_password", currentPassword)
				.put("password", newPassword)
				.put("password_confirmation", newPassword);
		return PteroActionImpl.onRequestExecute(
				impl.getP4J(), Route.Accounts.UPDATE_PASSWORD.compile(), PteroActionImpl.getRequestBody(obj));
	}
}
