package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Egg;
import com.mattmalec.pterodactyl4j.application.entities.Nest;
import com.mattmalec.pterodactyl4j.application.entities.Script;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class EggImpl implements Egg {

    private JSONObject json;
    private PteroApplicationImpl impl;

    public EggImpl(JSONObject json, PteroApplicationImpl impl) {
        this.json = json.getJSONObject("attributes");
        this.impl = impl;
    }

    @Override
    public PteroAction<Nest> retrieveNest() {
        return impl.retrieveNestById(getId());
    }

    @Override
    public String getAuthor() {
        return json.getString("author");
    }

    @Override
    public String getDescription() {
        return json.getString("description");
    }

    @Override
    public String getDockerImage() {
        return json.getString("docker_image");
    }

    @Override
    public String getStopCommand() {
        return json.getString("stop");
    }

    @Override
    public String getStartupCommand() {
        return json.getString("startup");
    }

    @Override
    public Script getInstallScript() {
        return new ScriptImpl(json.getJSONObject("script"));
    }

    @Override
    public long getIdLong() {
        return json.getLong("id");
    }

    @Override
    public OffsetDateTime getCreationDate() {
        return LocalDateTime.parse(json.optString("created_at"), DateTimeFormatter.ISO_LOCAL_DATE_TIME).atOffset(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
    }

    @Override
    public OffsetDateTime getUpdatedDate() {
        return LocalDateTime.parse(json.optString("updated_at"), DateTimeFormatter.ISO_LOCAL_DATE_TIME).atOffset(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
    }
}
