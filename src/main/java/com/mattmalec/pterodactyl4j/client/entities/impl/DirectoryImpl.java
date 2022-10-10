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
import com.mattmalec.pterodactyl4j.client.entities.Directory;
import com.mattmalec.pterodactyl4j.client.entities.File;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;
import com.mattmalec.pterodactyl4j.client.managers.CompressAction;
import com.mattmalec.pterodactyl4j.client.managers.DeleteAction;
import com.mattmalec.pterodactyl4j.client.managers.RenameAction;
import com.mattmalec.pterodactyl4j.client.managers.UploadFileAction;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class DirectoryImpl implements Directory {

	private final JSONObject json;
	private final String context;
	private final GenericFile genericFile;
	private final ClientServer server;

	public DirectoryImpl(JSONObject json, GenericFile genericFile, ClientServer server) {
		this.json = json;
		this.genericFile = genericFile;
		this.context = genericFile.getPath();
		this.server = server;
	}

	@Override
	public List<GenericFile> getFiles() {
		List<GenericFile> files = new ArrayList<>();
		for (Object o : json.getJSONArray("data")) {
			JSONObject file = new JSONObject(o.toString());
			GenericFile genericFile = new GenericFileImpl(file, getPath(), server);
			if (genericFile.isFile()) files.add(new FileImpl(file, getPath(), server));
			else files.add(new CachedDirectoryImpl(file, genericFile, server));
		}
		return Collections.unmodifiableList(files);
	}

	@Override
	public PteroAction<Void> createFolder(String folder) {
		return server.getFileManager().createDirectory().setRoot(this).setName(folder);
	}

	@Override
	public PteroAction<Void> createFile(String name, String content) {
		return server.getFileManager().createFile(this, name, content);
	}

	@Override
	public RenameAction rename() {
		return server.getFileManager().rename();
	}

	@Override
	public CompressAction compress() {
		return server.getFileManager().compress();
	}

	@Override
	public PteroAction<Void> decompress(File compressedFile) {
		return server.getFileManager().decompress(compressedFile);
	}

	@Override
	public PteroAction<Directory> into(Directory directory) {
		return server.retrieveDirectory(this, directory);
	}

	@Override
	public PteroAction<Directory> back() {
		String[] array = context.split("/");

		StringBuilder sb = new StringBuilder();

		int length = array.length;
		for (int i = 0; i < length - 1; i++) {
			if (length < length + i) sb.append("/");
			sb.append(array[i]);
		}

		return server.retrieveDirectory(sb.toString());
	}

	@Override
	public String getName() {
		return genericFile.getName();
	}

	@Override
	public String getPath() {
		return genericFile.getPath();
	}

	@Override
	public long getSizeBytes() {
		return genericFile.getSizeBytes();
	}

	@Override
	public Permission getPermissions() {
		return genericFile.getPermissions();
	}

	@Override
	public boolean isFile() {
		return genericFile.isFile();
	}

	@Override
	public boolean isSymlink() {
		return genericFile.isSymlink();
	}

	@Override
	public String getMimeType() {
		return genericFile.getMimeType();
	}

	@Override
	public OffsetDateTime getCreationDate() {
		return genericFile.getCreationDate();
	}

	@Override
	public OffsetDateTime getModifedDate() {
		return genericFile.getModifedDate();
	}

	@Override
	public PteroAction<Void> rename(String name) {
		return genericFile.rename(name);
	}

	@Override
	public PteroAction<Void> delete() {
		return genericFile.delete();
	}

	@Override
	public DeleteAction deleteFiles() {
		return server.getFileManager().delete();
	}

	@Override
	public UploadFileAction upload() {
		return server.getFileManager().upload(this);
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
