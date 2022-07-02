/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
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

package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.client.managers.*;
import com.mattmalec.pterodactyl4j.entities.Server;
import com.mattmalec.pterodactyl4j.requests.PaginationAction;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a {@link PteroClient}'s server
 * Use a {@link PteroClient} to retrieve a ptero information and servers
 * To build a {@link PteroClient} use the {@link PteroBuilder#buildClient()} method.
 */
public interface ClientServer extends Server {

    boolean isServerOwner();

    long getInternalIdLong();

    default String getInternalId() {
        return Long.toUnsignedString(getInternalIdLong());
    }

    SFTP getSFTPDetails();

    String getInvocation();

    Set<String> getEggFeatures();

    ClientEgg getEgg();

    String getNode();

    boolean isSuspended();

    boolean isInstalling();

    boolean isTransferring();

    ClientServerManager getManager();

    PteroAction<Utilization> retrieveUtilization();

    PteroAction<Void> setPower(PowerAction powerAction);

    default PteroAction<Void> start() {
        return setPower(PowerAction.START);
    }

    default PteroAction<Void> stop() {
        return setPower(PowerAction.STOP);
    }

    default PteroAction<Void> restart() {
        return setPower(PowerAction.RESTART);
    }

    default PteroAction<Void> kill() {
        return setPower(PowerAction.KILL);
    }

    PteroAction<Void> sendCommand(String command);

    WebSocketBuilder getWebSocketBuilder();

    List<ClientSubuser> getSubusers();

    PteroAction<ClientSubuser> retrieveSubuser(UUID uuid);

    default PteroAction<ClientSubuser> retrieveSubuser(String uuid) {
        return retrieveSubuser(UUID.fromString(uuid));
    }

    SubuserManager getSubuserManager();

    PaginationAction<Backup> retrieveBackups();

    PteroAction<Backup> retrieveBackup(UUID uuid);

    default PteroAction<Backup> retrieveBackup(String uuid) {
        return retrieveBackup(UUID.fromString(uuid));
    }

    BackupManager getBackupManager();

    PteroAction<List<Schedule>> retrieveSchedules();

    default PteroAction<Schedule> retrieveSchedule(long id) {
        return retrieveSchedule(Long.toUnsignedString(id));
    }

    PteroAction<Schedule> retrieveSchedule(String id);

    ScheduleManager getScheduleManager();

    FileManager getFileManager();

    default PteroAction<Directory> retrieveDirectory() {
        return retrieveDirectory("/");
    }

    PteroAction<Directory> retrieveDirectory(Directory previousDirectory, Directory directory);

    PteroAction<Directory> retrieveDirectory(String path);

    PteroAction<List<ClientDatabase>> retrieveDatabases();

    default PteroAction<Optional<ClientDatabase>> retrieveDatabaseById(String id) {
        return retrieveDatabases().map(List::stream).map(stream -> stream.filter(db -> db.getId().equals(id)).findFirst());
    }

    default PteroAction<Optional<ClientDatabase>> retrieveDatabaseByName(String name, boolean caseSensitive) {
        return retrieveDatabases().map(List::stream).map(stream -> stream.filter(db -> caseSensitive ?
                db.getName().contains(name) : db.getName().toLowerCase().contains(name.toLowerCase())).findFirst());
    }

    ClientDatabaseManager getDatabaseManager();

    List<ClientAllocation> getAllocations();

    default ClientAllocation getPrimaryAllocation() {
        return getAllocations().stream().filter(ClientAllocation::isDefault).findFirst().get();
    }

    default Optional<ClientAllocation> getAllocationByPort(int port) {
        return getAllocations().stream().filter(allocation -> allocation.getPortInt() == port).findFirst();
    }

    default Optional<ClientAllocation> getAllocationById(long id) {
        return getAllocations().stream().filter(allocation -> allocation.getIdLong() == id).findFirst();
    }

    ClientAllocationManager getAllocationManager();

}
