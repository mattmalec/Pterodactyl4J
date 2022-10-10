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

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.DownloadableFile;
import com.mattmalec.pterodactyl4j.client.entities.File;
import com.mattmalec.pterodactyl4j.client.managers.FileManager;
import org.json.JSONObject;

public class FileImpl extends GenericFileImpl implements File {

	private final FileManager fileManager;

	public FileImpl(JSONObject json, String context, ClientServer server) {
		super(json, context, server);
		this.fileManager = server.getFileManager();
	}

	@Override
	public PteroAction<String> retrieveContent() {
		return fileManager.retrieveContent(this);
	}

	@Override
	public PteroAction<DownloadableFile> retrieveDownload() {
		return fileManager.retrieveDownload(this);
	}

	@Override
	public PteroAction<Void> write(String content) {
		return fileManager.write(this, content);
	}

	@Override
	public PteroAction<Void> copy() {
		return fileManager.copy(this);
	}

	@Override
	public PteroAction<Void> decompress() {
		return fileManager.decompress(this);
	}
}
