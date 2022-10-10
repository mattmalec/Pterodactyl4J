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
import com.mattmalec.pterodactyl4j.client.entities.File;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;
import com.mattmalec.pterodactyl4j.client.managers.CompressAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class CompressActionImpl extends PteroActionImpl<File> implements CompressAction {

	private final List<GenericFile> files;

	public CompressActionImpl(ClientServer server, PteroClientImpl impl) {
		super(
				impl.getP4J(),
				Route.Files.COMPRESS_FILES.compile(server.getIdentifier()),
				(response, request) -> new FileImpl(response.getObject(), "/", server));
		this.files = new ArrayList<>();
	}

	@Override
	public CompressAction addFile(GenericFile file) {
		files.add(file);
		return this;
	}

	@Override
	public CompressAction addFiles(GenericFile file, GenericFile... files) {
		this.files.add(file);

		if (files.length > 0) this.files.addAll(Arrays.asList(files));

		return this;
	}

	@Override
	public CompressAction clearFiles() {
		files.clear();
		return this;
	}

	@Override
	protected RequestBody finalizeData() {
		List<String> array = files.stream().map(GenericFile::getPath).collect(Collectors.toList());

		JSONObject json = new JSONObject().put("root", "/").put("files", array);
		return getRequestBody(json);
	}
}
