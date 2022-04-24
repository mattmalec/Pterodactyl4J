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

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.PteroAction;

import java.nio.file.attribute.PosixFilePermission;
import java.time.OffsetDateTime;
import java.util.EnumSet;

public interface GenericFile {

    String getName();
    String getPath();

    long getSizeBytes();
    default double getSize(DataType dataType) {
        return getSizeBytes() / (double) dataType.getByteValue();
    }

    GenericFile.Permission getPermissions();
    boolean isFile();
    boolean isSymlink();
    String getMimeType();
    OffsetDateTime getCreationDate();
    OffsetDateTime getModifedDate();

    PteroAction<Void> rename(String name);
    PteroAction<Void> delete();


    interface Permission {
        int getBits();
        EnumSet<PosixFilePermission> getPosixPermissions();
    }

}
