package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.Backup;

import java.util.List;

public interface BackupAction extends PteroAction<Backup> {

    BackupAction setName(String name);
    BackupAction setIgnoredFiles(List<String> files);

}
