package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.Backup;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BackupImpl implements Backup {

    private JSONObject json;
    private ClientServer server;

    public BackupImpl(JSONObject json, ClientServer server) {
        this.json = json.getJSONObject("attributes");
        this.server = server;
    }

    @Override
    public UUID getUUID() {
        return UUID.fromString(json.getString("uuid"));
    }

    @Override
    public String getName() {
        return json.getString("name");
    }

    @Override
    public String getChecksum() {
        return json.optString("checksum", null);
    }

    @Override
    public boolean isSuccessful() {
        return json.getBoolean("is_successful");
    }

    @Override
    public long getSize() {
        return json.getLong("bytes");
    }

    @Override
    public List<String> getIgnoredFiles() {
        return json.getJSONArray("ignored_files").toList().stream().map(Object::toString).collect(Collectors.toList());
    }

    @Override
    public PteroAction<String> getDownloadUrl() {
        return server.getBackupManager().getDownloadUrl(this);
    }

    @Override
    public OffsetDateTime getTimeCompleted() {
        return json.isNull("completed_at") ? null : OffsetDateTime.parse(json.getString("completed_at"));
    }

    @Override
    public OffsetDateTime getTimeCreated() {
        return OffsetDateTime.parse(json.getString("created_at"));
    }

    @Override
    public PteroAction<Void> delete() {
        return server.getBackupManager().deleteBackup(this);
    }

    @Override
    public String toString() {
        return json.toString(4);
    }
}
