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
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.OffsetDateTime;
import java.util.EnumSet;
import org.json.JSONObject;

public class GenericFileImpl implements GenericFile {

	private final JSONObject json;
	private final String path;
	private final ClientServer server;

	public GenericFileImpl(JSONObject json, String context, ClientServer server) {
		this.json = json.getJSONObject("attributes");

		// weird shit with root directories
		this.path = context.equals("/") ? context.substring(1) : context;
		this.server = server;
	}

	@Override
	public String getName() {
		return json.getString("name");
	}

	@Override
	public String getPath() {
		return String.format("%s/%s", path, getName());
	}

	@Override
	public long getSizeBytes() {
		return json.getLong("size");
	}

	@Override
	public Permission getPermissions() {
		return new Permission() {
			@Override
			public int getBits() {
				return json.getInt("mode_bits");
			}

			@Override
			public EnumSet<PosixFilePermission> getPosixPermissions() {
				return EnumSet.copyOf(
						PosixFilePermissions.fromString(json.getString("mode").substring(1)));
			}
		};
	}

	@Override
	public boolean isFile() {
		return json.getBoolean("is_file");
	}

	@Override
	public boolean isSymlink() {
		return json.getBoolean("is_symlink");
	}

	@Override
	public String getMimeType() {
		return json.getString("mimetype");
	}

	@Override
	public OffsetDateTime getCreationDate() {
		return OffsetDateTime.parse(json.getString("created_at"));
	}

	@Override
	public OffsetDateTime getModifedDate() {
		return OffsetDateTime.parse(json.getString("modified_at"));
	}

	@Override
	public PteroAction<Void> rename(String name) {
		return server.getFileManager().rename().addFile(this, name);
	}

	@Override
	public PteroAction<Void> delete() {
		return server.getFileManager().delete().addFile(this);
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
