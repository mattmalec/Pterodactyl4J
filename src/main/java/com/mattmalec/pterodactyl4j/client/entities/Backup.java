package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface Backup {

    UUID getUUID();
    String getName();
    String getChecksum();
    boolean isSuccessful();
    long getSize();
    default String getSizeFormatted(DataType dataType) {
        return String.format("%.2f %s", getSize() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
    }
    List<String> getIgnoredFiles();
    PteroAction<String> retrieveDownloadUrl();
    OffsetDateTime getTimeCompleted();
    OffsetDateTime getTimeCreated();
    PteroAction<Void> restore();
    PteroAction<Void> delete();

}
