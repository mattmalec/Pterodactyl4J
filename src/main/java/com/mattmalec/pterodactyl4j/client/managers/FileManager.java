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

package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.Directory;
import com.mattmalec.pterodactyl4j.client.entities.DownloadableFile;
import com.mattmalec.pterodactyl4j.client.entities.File;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;

public interface FileManager {

    CreateDirectoryAction createDirectory();
    PteroAction<Void> createFile(Directory directory, String name, String content);

    UploadFileAction upload(Directory directory);
    RenameAction rename();
    ArchiveAction compress();
    PteroAction<Void> decompress(File compressedFile);
    DeleteAction delete();

    PteroAction<String> retrieveContent(File file);
    PteroAction<DownloadableFile> retrieveDownload(File file);
    PteroAction<Void> write(File file, String content);
    PteroAction<Void> copy(File file);

}
