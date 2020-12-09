package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.Backup;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.managers.BackupAction;
import com.mattmalec.pterodactyl4j.client.managers.BackupManager;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

public class BackupManagerImpl implements BackupManager {

    private ClientServer server;
    private PteroClientImpl impl;

    public BackupManagerImpl(ClientServer server, PteroClientImpl impl) {
        this.server = server;
        this.impl = impl;
    }

    @Override
    public BackupAction createBackup() {
        return new CreateBackupImpl(server, impl);
    }

    @Override
    public PteroAction<String> getDownloadUrl(Backup backup) {
        return PteroActionImpl.onExecute(() -> {
            Route.CompiledRoute route = Route.Backups.DOWNLOAD_BACKUP.compile(server.getIdentifier(), backup.getUUID().toString());
            JSONObject json = impl.getRequester().request(route).toJSONObject();
            return json.getJSONObject("attributes").getString("url");
        });
    }

    @Override
    public PteroAction<Void> deleteBackup(Backup backup) {
        return PteroActionImpl.onExecute(() -> {
            Route.CompiledRoute route = Route.Backups.DELETE_BACKUP.compile(server.getIdentifier(), backup.getUUID().toString());
            impl.getRequester().request(route);
            return null;
        });
    }
}
