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
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;
import java.nio.file.attribute.PosixFilePermission;
import java.time.OffsetDateTime;
import java.util.EnumSet;

public class GenericRootFileImpl implements GenericFile {

	private final String rootName;

	private final String rootPath;

	public GenericRootFileImpl(String rootPath) {
		this.rootPath = rootPath;
		String[] context = rootPath.split("/");
		this.rootName = rootPath.equals("/") ? "Root Directory" : context[context.length - 1];
	}

	@Override
	public String getName() {
		return rootName;
	}

	@Override
	public String getPath() {
		return rootPath;
	}

	@Override
	public long getSizeBytes() {
		return 0;
	}

	@Override
	public Permission getPermissions() {
		return new Permission() {
			@Override
			public int getBits() {
				return 0;
			}

			@Override
			public EnumSet<PosixFilePermission> getPosixPermissions() {
				return EnumSet.noneOf(PosixFilePermission.class);
			}
		};
	}

	@Override
	public boolean isFile() {
		return false;
	}

	@Override
	public boolean isSymlink() {
		return false;
	}

	@Override
	public String getMimeType() {
		return "inode/directory";
	}

	@Override
	public OffsetDateTime getCreationDate() {
		return OffsetDateTime.now();
	}

	@Override
	public OffsetDateTime getModifedDate() {
		return OffsetDateTime.now();
	}

	@Override
	public PteroAction<Void> rename(String name) {
		throw new UnsupportedOperationException("You cannot rename the root directory");
	}

	@Override
	public PteroAction<Void> delete() {
		throw new UnsupportedOperationException("You cannot delete the root directory");
	}
}
