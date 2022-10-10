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

package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.client.entities.Backup;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.client.managers.*;
import java.util.EnumSet;

/**
 * Represents the raw and description values used by Pterodactyl for Permissions
 */
public enum Permission {

	/**
	 * Allows a user to connect to the websocket instance for a server to stream the console.
	 *
	 * @see WebSocketBuilder#build()
	 */
	WEBSOCKET_CONNECT(
			"websocket.connect",
			"Allows a user to connect to the websocket instance for a server to stream the console."),

	/**
	 * Allows a user to send commands to the server instance via the console.
	 */
	CONTROL_CONSOLE("control.console", "Allows a user to send commands to the server instance via the console."),

	/**
	 * Allows a user to start the server if it is stopped.
	 *
	 * @see ClientServer#start()
	 */
	CONTROL_START("control.start", "Allows a user to start the server if it is stopped."),

	/**
	 * Allows a user to stop a server if it is running.
	 *
	 * @see ClientServer#stop()
	 */
	CONTROL_STOP("control.stop", "Allows a user to stop a server if it is running."),

	/**
	 * Allows a user to perform a server restart. This allows them to start the server if it is offline, but not put the server in a completely stopped state.
	 *
	 * @see ClientServer#restart()
	 */
	CONTROL_RESTART(
			"control.restart",
			"Allows a user to perform a server restart. This allows them to start the server if it is offline, but not put the server in a completely stopped state."),

	/**
	 * Allows a user to view the database associated with this server.
	 */
	DATABASE_READ("database.read", "Allows a user to view the database associated with this server."),

	/**
	 * Allows a user to create a new database for this server.
	 */
	DATABASE_CREATE("database.create", "Allows a user to create a new database for this server."),

	/**
	 * Allows a user to rotate the password on a database instance. If the user does not have the {@link Permission#DATABASE_VIEW_PASSWORD} permission they will not see the updated password.
	 */
	DATABASE_UPDATE(
			"database.update",
			"Allows a user to rotate the password on a database instance. If the user does not have the view_password permission they will not see the updated password."),

	/**
	 * Allows a user to remove a database instance from this server.
	 */
	DATABASE_DELETE("database.delete", "Allows a user to remove a database instance from this server."),

	/**
	 * Allows a user to view the password associated with a database instance for this server.
	 */
	DATABASE_VIEW_PASSWORD(
			"database.view_password",
			"Allows a user to view the password associated with a database instance for this server."),

	/**
	 * Allows a user to view schedules and the tasks associated with them for this server.
	 *
	 * @see ClientServer#retrieveSchedules()
	 */
	SCHEDULE_READ(
			"schedule.read", "Allows a user to view schedules and the tasks associated with them for this server."),

	/**
	 * Allows a user to create new schedules for this server.
	 *
	 * @see ScheduleManager#createSchedule()
	 */
	SCHEDULE_CREATE("schedule.create", "Allows a user to create new schedules for this server."),

	/**
	 * Allows a user to update schedules and schedule tasks for this server.
	 *
	 * @see ScheduleManager#editSchedule(Schedule)
	 */
	SCHEDULE_UPDATE("schedule.update", "Allows a user to update schedules and schedule tasks for this server."),

	/**
	 * Allows a user to delete schedules for this server.
	 *
	 * @see ScheduleManager#delete(Schedule)
	 */
	SCHEDULE_DELETE("schedule.delete", "Allows a user to delete schedules for this server."),

	/**
	 * Allows the user to view subusers and their permissions for the server.
	 *
	 * @see ClientServer#getSubusers()
	 */
	USER_READ("user.read", "Allows the user to view subusers and their permissions for the server."),

	/**
	 * Allows a user to create new subusers for the server.
	 *
	 * @see SubuserManager#createUser()
	 */
	USER_CREATE("user.create", "Allows a user to create new subusers for the server."),

	/**
	 * Allows a user to modify other subusers.
	 *
	 * @see SubuserManager#editUser(ClientSubuser)
	 */
	USER_UPDATE("user.update", "Allows a user to modify other subusers."),

	/**
	 * Allows a user to delete a subuser from the server.
	 *
	 * @see SubuserManager#deleteUser(ClientSubuser)
	 */
	USER_DELETE("user.delete", "Allows a user to delete a subuser from the server."),

	/**
	 * Allows a user to view all backups that exist for this server.
	 *
	 * @see ClientServer#retrieveBackups()
	 */
	BACKUP_READ("backup.read", "Allows a user to view all backups that exist for this server."),

	/**
	 * Allows a user to create new backups for this server.
	 *
	 * @see BackupManager#createBackup()
	 */
	BACKUP_CREATE("backup.create", "Allows a user to create new backups for this server."),

	BACKUP_UPDATE("backup.update", ""),

	/**
	 * Allows a user to remove backups from the system.
	 *
	 * @see BackupManager#deleteBackup(Backup)
	 */
	BACKUP_DELETE("backup.delete", "Allows a user to remove backups from the system."),

	/**
	 * Allows a user to download backups.
	 *
	 * <p><b>Danger: This allows a user to access all files for the server in the backup.</b>
	 *
	 * @see BackupManager#retrieveDownloadUrl(Backup)
	 */
	BACKUP_DOWNLOAD("backup.download", "Allows a user to download backups."),

	/**
	 * Allows a user to restore backups for the server.
	 *
	 * <p><b>Danger: this allows the user to delete all of the server files in the process.</b>
	 *
	 * @see BackupManager#restoreBackup(Backup)
	 */
	BACKUP_RESTORE("backup.restore", "Allows a user to restore backups for the server."),

	/**
	 * Allows a user to view all allocations currently assigned to this server. Users with any level of access to this server can always view the primary allocation.
	 */
	ALLOCATION_READ(
			"allocation.read",
			"Allows a user to view all allocations currently assigned to this server. Users with any level of access to this server can always view the primary allocation."),

	/**
	 * Allows a user to assign additional allocations to the server.
	 */
	ALLOCATION_CREATE("allocation.create", "Allows a user to assign additional allocations to the server."),

	/**
	 * Allows a user to change the primary server allocation and attach notes to each allocation.
	 */
	ALLOCATION_UPDATE(
			"allocation.update",
			"Allows a user to change the primary server allocation and attach notes to each allocation."),

	/**
	 * Allows a user to delete an allocation from the server.
	 */
	ALLOCATION_DELETE("allocation.delete", "Allows a user to delete an allocation from the server."),

	/**
	 * Allows a user to view the contents of a directory, but not view the contents of or download files.
	 */
	FILE_READ(
			"file.read",
			"Allows a user to view the contents of a directory, but not view the contents of or download files."),

	/**
	 * Allows a user to view the contents of a given file. This will also allow the user to download files.
	 */
	FILE_READ_CONTENT(
			"file.read-content",
			"Allows a user to view the contents of a given file. This will also allow the user to download files."),

	/**
	 * Allows a user to create additional files and folders via the Panel or direct upload.
	 */
	FILE_CREATE("file.create", "Allows a user to create additional files and folders via the Panel or direct upload."),

	/**
	 * Allows a user to update the contents of an existing file or directory.
	 */
	FILE_UPDATE("file.update", "Allows a user to update the contents of an existing file or directory."),

	/**
	 * Allows a user to delete files or directories.
	 */
	FILE_DELETE("file.delete", "Allows a user to delete files or directories."),

	/**
	 * Allows a user to archive the contents of a directory as well as decompress existing archives on the system.
	 */
	FILE_ARCHIVE(
			"file.archive",
			"Allows a user to archive the contents of a directory as well as decompress existing archives on the system."),

	/**
	 * Allows a user to connect to SFTP and manage server files using the other assigned file permissions.
	 */
	FILE_SFTP(
			"file.sftp",
			"Allows a user to connect to SFTP and manage server files using the other assigned file permissions."),

	/**
	 * Allows a user to view the startup variables for a server.
	 */
	STARTUP_READ("startup.read", "Allows a user to view the startup variables for a server."),

	/**
	 * Allows a user to modify the startup variables for the server.
	 */
	STARTUP_UPDATE("startup.update", "Allows a user to modify the startup variables for the server."),

	/**
	 * Allows a user to modify the Docker image used when running the server.
	 */
	STARTUP_DOCKER_IMAGE(
			"startup.docker-image", "Allows a user to modify the Docker image used when running the server."),

	/**
	 * Allows a user to rename this server.
	 *
	 * @see ClientServerManager#setName(String)
	 */
	SETTINGS_RENAME("settings.rename", "Allows a user to rename this server."),

	/**
	 * Allows a user to trigger a reinstall of this server.
	 *
	 * @see ClientServerManager#reinstall()
	 */
	SETTINGS_REINSTALL("settings.reinstall", "Allows a user to trigger a reinstall of this server."),

	/**
	 * Unknown Permission
	 */
	UNKNOWN("unknown", "Unknown Permission");

	private final String raw;
	private final String description;

	/**
	 * Represents a set of all the available permissions
	 */
	public static final Permission[] ALL_PERMISSIONS = Permission.values();

	/**
	 * All permissions that apply to controlling a server's state
	 */
	public static final Permission[] CONTROL_PERMISSIONS =
			new Permission[] {CONTROL_CONSOLE, CONTROL_START, CONTROL_STOP, CONTROL_RESTART};

	/**
	 * All permissions that apply to controlling a server's database
	 */
	public static final Permission[] DATABASE_PERMISSIONS =
			new Permission[] {DATABASE_READ, DATABASE_CREATE, DATABASE_UPDATE, DATABASE_DELETE, DATABASE_VIEW_PASSWORD};

	/**
	 * All permissions that apply to a server's schedules
	 */
	public static final Permission[] SCHEDULE_PERMISSIONS =
			new Permission[] {SCHEDULE_READ, SCHEDULE_CREATE, SCHEDULE_UPDATE, SCHEDULE_DELETE};

	/**
	 * All permissions that apply to controlling a server's subuser access
	 */
	public static final Permission[] USER_PERMISSIONS =
			new Permission[] {USER_READ, USER_CREATE, USER_UPDATE, USER_DELETE};

	/**
	 * All permissions that apply to controlling a server's backups
	 */
	public static final Permission[] BACKUP_PERMISSIONS =
			new Permission[] {BACKUP_READ, BACKUP_CREATE, BACKUP_UPDATE, BACKUP_DELETE, BACKUP_DOWNLOAD, BACKUP_RESTORE
			};

	/**
	 * All permissions that apply to controlling a server's allocations
	 */
	public static final Permission[] ALLOCATION_PERMISSIONS =
			new Permission[] {ALLOCATION_READ, ALLOCATION_CREATE, ALLOCATION_UPDATE, ALLOCATION_DELETE};

	/**
	 * All permissions that apply to modifying a server's files
	 */
	public static final Permission[] FILE_PERMISSIONS = new Permission[] {
		FILE_READ, FILE_READ_CONTENT, FILE_CREATE, FILE_UPDATE, FILE_DELETE, FILE_ARCHIVE, FILE_SFTP
	};

	/**
	 * All permissions that apply to modifying a server's startup command
	 */
	public static final Permission[] STARTUP_PERMISSIONS = new Permission[] {STARTUP_READ, STARTUP_UPDATE};

	/**
	 * All permissions that apply to renaming and reinstalling a server
	 */
	public static final Permission[] SETTINGS_PERMISSIONS = new Permission[] {SETTINGS_RENAME, SETTINGS_REINSTALL};

	Permission(String raw, String description) {
		this.raw = raw;
		this.description = description;
	}

	/**
	 * The value of this permission when viewed as a raw value.
	 *
	 * @return The raw value of this {@link com.mattmalec.pterodactyl4j.Permission}
	 */
	public String getRaw() {
		return raw;
	}

	/**
	 * The readable description as used in Pterodactyl
	 *
	 * @return The readable description of this {@link com.mattmalec.pterodactyl4j.Permission}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * A set of all {@link com.mattmalec.pterodactyl4j.Permission Permissions} that are specified by the collection of
	 * raw permissions.
	 *
	 * @param  permissions
	 *         The raw representation of permissions
	 *
	 * @return Possibly-empty EnumSet of {@link com.mattmalec.pterodactyl4j.Permission Permissions}.
	 *
	 */
	public static EnumSet<Permission> getPermissions(String... permissions) {
		EnumSet<Permission> perms = EnumSet.noneOf(Permission.class);
		if (permissions.length == 0) {
			return perms;
		}
		for (String raw : permissions) {
			Permission perm = ofRaw(raw);
			if (perm != UNKNOWN) perms.add(perm);
		}
		return perms;
	}

	/**
	 * This is effectively the opposite of {@link #getPermissions(String...)}, this takes 1 or more {@link com.mattmalec.pterodactyl4j.Permission Permissions}
	 * and returns a list of the raw representations.
	 *
	 * @param  permissions
	 *         The collection of permissions of which to return into the raw representation
	 *
	 * @return Never-empty String array representating the raw values of the {@link com.mattmalec.pterodactyl4j.Permission Permissions}
	 */
	public static String[] getRaw(Permission... permissions) {
		int length = permissions.length;
		String[] perms = new String[length];
		for (int i = 0; i < length; i++) {
			perms[i] = permissions[i].getRaw();
		}
		return perms;
	}

	/**
	 * This is effectively {@link #getPermissions(String...)}, designed for returning a single {@link com.mattmalec.pterodactyl4j.Permission Permission}
	 *
	 * @param  raw
	 *         The raw representation of the permission
	 *
	 * @return Never-null {@link com.mattmalec.pterodactyl4j.Permission Permission}.
	 *
	 */
	public static Permission ofRaw(String raw) {
		for (Permission perm : ALL_PERMISSIONS) {
			if (perm.getRaw().equals(raw)) {
				return perm;
			}
		}
		return Permission.UNKNOWN;
	}
}
