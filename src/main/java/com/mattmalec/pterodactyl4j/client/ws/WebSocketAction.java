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

package com.mattmalec.pterodactyl4j.client.ws;

import org.json.JSONArray;
import org.json.JSONObject;

public final class WebSocketAction {

	public static final String AUTH = "auth";
	public static final String SET_STATE = "set state";
	public static final String SEND_LOGS = "send logs";
	public static final String SEND_COMMAND = "send command";
	public static final String SEND_STATS = "send stats";

	public static String create(String event, String argument) {
		JSONObject returnable = new JSONObject();
		JSONArray args = new JSONArray();
		args.put(argument);
		returnable.put("event", event).put("args", args);
		return returnable.toString();
	}
}
