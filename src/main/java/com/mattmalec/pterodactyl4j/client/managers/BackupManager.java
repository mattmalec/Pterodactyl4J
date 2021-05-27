package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.Backup;

public interface BackupManager {

    BackupAction createBackup();
    PteroAction<String> retrieveDownloadUrl(Backup backup);
    PteroAction<Void> restoreBackup(Backup backup);
    PteroAction<Void> deleteBackup(Backup backup);

}
