package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Egg;
import com.mattmalec.pterodactyl4j.application.entities.Nest;
import com.mattmalec.pterodactyl4j.application.entities.Script;
import org.json.JSONObject;

import java.time.OffsetDateTime;

public class EggImpl implements Egg {

    private final JSONObject json;
    private final PteroApplicationImpl impl;

    public EggImpl(JSONObject json, PteroApplicationImpl impl) {
        this.json = json.getJSONObject("attributes");
        this.impl = impl;
    }

    @Override
    public PteroAction<Nest> retrieveNest() {
        return impl.retrieveNestById(json.getLong("nest"));
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
        return OffsetDateTime.parse(json.optString("created_at"));
    }

    @Override
    public OffsetDateTime getUpdatedDate() {
        return OffsetDateTime.parse(json.optString("updated_at"));
    }

    @Override
    public String toString() {
        return json.toString(4);
    }
}
