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

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;
import com.mattmalec.pterodactyl4j.client.managers.RenameAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

public class RenameActionImpl extends PteroActionImpl<Void> implements RenameAction {

	private final Map<GenericFile, String> files;

	public RenameActionImpl(ClientServer server, PteroClientImpl impl) {
		super(impl.getP4J(), Route.Files.RENAME_FILES.compile(server.getIdentifier()));
		this.files = new HashMap<>();
	}

	@Override
	public RenameAction addFile(GenericFile file, String newName) {
		files.put(file, newName);
		return this;
	}

	@Override
	public RenameAction clearFiles() {
		files.clear();
		return this;
	}

	@Override
	protected RequestBody finalizeData() {
		JSONArray array = new JSONArray();
		Map<String, String> mappedFiles = files.entrySet().stream()
				.collect(Collectors.toMap(k -> k.getKey().getPath(), e -> {
					String[] context = e.getKey().getPath().split("/");
					context[context.length - 1] = e.getValue();
					return String.join("/", context);
				}));

		mappedFiles.forEach((k, v) -> {
			JSONObject obj = new JSONObject().put("from", k).put("to", v);
			array.put(obj);
		});

		JSONObject json = new JSONObject().put("root", "/").put("files", array);
		return getRequestBody(json);
	}
}
