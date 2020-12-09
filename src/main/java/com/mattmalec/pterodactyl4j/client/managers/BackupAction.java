package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.client.entities.Backup;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

import java.util.List;

public interface BackupAction {

    BackupAction setName(String name);
    BackupAction setIgnoredFiles(List<String> files);
    PteroAction<Backup> build();

}
