package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.Backup;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.managers.BackupAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.util.List;

public class CreateBackupImpl extends PteroActionImpl<Backup> implements BackupAction {

    private String name;
    private List<String> files;

    public CreateBackupImpl(ClientServer server, PteroClientImpl impl) {
        super(impl.getPteroApi(), Route.Backups.CREATE_BACKUP.compile(server.getIdentifier()),
                (response, request) -> new BackupImpl(response.getObject(), server));
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
    protected RequestBody finalizeData() {
        if(name != null && name.length() > 191) {
            throw new IllegalArgumentException("The name cannot be over 191 characters");
        }
        JSONObject json = new JSONObject()
                .put("name", name)
                .put("ignored", files == null ? "" : files);
        return getRequestBody(json);
    }
}
