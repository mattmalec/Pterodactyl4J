package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.client.entities.Backup;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

public interface BackupManager {

    BackupAction createBackup();
    PteroAction<String> getDownloadUrl(Backup backup);
    PteroAction<Void> deleteBackup(Backup backup);

}
