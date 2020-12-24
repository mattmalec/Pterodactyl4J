package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.client.managers.BackupManager;
import com.mattmalec.pterodactyl4j.client.managers.ClientServerManager;
import com.mattmalec.pterodactyl4j.client.managers.SubuserManager;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketBuilder;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.entities.Server;
import com.mattmalec.pterodactyl4j.utils.Relationed;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ClientServer extends Server {

	boolean isServerOwner();
	long getInternalIdLong();
	default String getInternalId() { return Long.toUnsignedString(getInternalIdLong()); }
	SFTP getSFTPDetails();
	String getInvocation();
	Set<String> getEggFeatures();
	String getNode();
	boolean isSuspended();
	boolean isInstalling();
	WebSocketBuilder getWebSocketBuilder();
	List<ClientSubuser> getSubusers();
	Relationed<ClientSubuser> getSubuser(UUID uuid);
	default Relationed<ClientSubuser> getSubuser(String uuid) {
		return getSubuser(UUID.fromString(uuid));
	}
	SubuserManager getSubuserManager();
	ClientEgg getEgg();
	ClientServerManager getManager();
	PteroAction<List<Backup>> retrieveBackups();
	PteroAction<Backup> retrieveBackup(UUID uuid);
	default PteroAction<Backup> retrieveBackup(String uuid) {
		return retrieveBackup(UUID.fromString(uuid));
	}
	BackupManager getBackupManager();

}
