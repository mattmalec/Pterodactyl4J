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

package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Directory;
import com.mattmalec.pterodactyl4j.client.entities.File;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;
import com.mattmalec.pterodactyl4j.client.managers.ArchiveAction;
import com.mattmalec.pterodactyl4j.client.managers.CreateDirectoryAction;
import com.mattmalec.pterodactyl4j.client.managers.FileManager;
import com.mattmalec.pterodactyl4j.client.managers.RenameAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import okhttp3.RequestBody;

public class FileManagerImpl implements FileManager {

    private final ClientServer server;
    private final PteroClientImpl impl;

    public FileManagerImpl(ClientServer server, PteroClientImpl impl) {
        this.server = server;
        this.impl = impl;
    }

    @Override
    public CreateDirectoryAction createDirectory() {
        return new CreateDirectoryActionImpl(server, impl);
    }

    @Override
    public PteroAction<Void> createFile(Directory directory, String name, String content) {
        return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Files.WRITE_FILE.compile(server.getIdentifier(), directory.getPath() + "/" + name),
                RequestBody.create(Requester.MEDIA_TYPE_PLAIN, content));
    }

    @Override
    public RenameAction rename() {
        return null;
    }

    @Override
    public ArchiveAction compress() {
        return null;
    }

    @Override
    public PteroAction<Void> decompress(File compressedFile) {
        return null;
    }

    @Override
    public PteroAction<Void> rename(GenericFile file, String name) {
        return null;
    }

    @Override
    public PteroAction<Void> delete(GenericFile file) {
        return null;
    }

    @Override
    public PteroAction<String> retrieveContent(File file) {
        return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Files.GET_CONTENTS.compile(server.getIdentifier(), file.getPath()),
                (response, request) -> response.getRawObject());
    }

    @Override
    public PteroAction<String> retrieveDownloadUrl(File file) {
        return null;
    }

    @Override
    public PteroAction<Void> write(File file, String content) {
        return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Files.WRITE_FILE.compile(server.getIdentifier(), file.getPath()),
                RequestBody.create(Requester.MEDIA_TYPE_PLAIN, content));
    }

    @Override
    public PteroAction<Void> copy(File file, String newName) {
        return null;
    }
}
