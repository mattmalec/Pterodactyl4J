package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.Backup;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.managers.BackupAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.util.List;

public class CreateBackupImpl implements BackupAction {

    private ClientServer server;
    private PteroClientImpl impl;

    private String name;
    private List<String> files;

    public CreateBackupImpl(ClientServer server, PteroClientImpl impl) {
        this.server = server;
        this.impl = impl;
    }

    @Override
    public BackupAction setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public BackupAction setIgnoredFiles(List<String> files) {
        this.files = files;
        return this;
    }

    @Override
    public PteroAction<Backup> build() {
        if(name != null && name.length() > 191) {
            throw new IllegalArgumentException("The name cannot be over 191 characters");
        }
        JSONObject json = new JSONObject()
                .put("name", name)
                .put("ignored", files);
        return PteroActionImpl.onExecute(() -> {
            Route.CompiledRoute route = Route.Backups.CREATE_BACKUP.compile(server.getIdentifier()).withJSONdata(json);
            JSONObject obj = impl.getRequester().request(route).toJSONObject();
            return new BackupImpl(obj, server);
        });
    }
}
