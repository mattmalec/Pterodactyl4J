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
import com.mattmalec.pterodactyl4j.client.entities.Backup;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.managers.BackupAction;
import com.mattmalec.pterodactyl4j.client.managers.BackupManager;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;

public class BackupManagerImpl implements BackupManager {

	private final ClientServer server;
	private final PteroClientImpl impl;

	public BackupManagerImpl(ClientServer server, PteroClientImpl impl) {
		this.server = server;
		this.impl = impl;
	}

	@Override
	public BackupAction createBackup() {
		return new CreateBackupImpl(server, impl);
	}

	@Override
	public PteroAction<String> retrieveDownloadUrl(Backup backup) {
		return new PteroActionImpl<>(
				impl.getP4J(),
				Route.Backups.DOWNLOAD_BACKUP.compile(
						server.getIdentifier(), backup.getUUID().toString()),
				(response, request) ->
						response.getObject().getJSONObject("attributes").getString("url"));
	}

	@Override
	public PteroAction<Void> restoreBackup(Backup backup) {
		return PteroActionImpl.onRequestExecute(
				impl.getP4J(),
				Route.Backups.RESTORE_BACKUP.compile(
						server.getIdentifier(), backup.getUUID().toString()));
	}

	@Override
	public PteroAction<Backup> toggleLock(Backup backup) {
		return new PteroActionImpl<>(
				impl.getP4J(),
				Route.Backups.LOCK_BACKUP.compile(
						server.getIdentifier(), backup.getUUID().toString()),
				(response, request) -> new BackupImpl(response.getObject(), server));
	}

	@Override
	public PteroAction<Void> deleteBackup(Backup backup) {
		return PteroActionImpl.onRequestExecute(
				impl.getP4J(),
				Route.Backups.DELETE_BACKUP.compile(
						server.getIdentifier(), backup.getUUID().toString()));
	}
}
