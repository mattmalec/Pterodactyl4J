package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Egg;
import com.mattmalec.pterodactyl4j.application.entities.Nest;
import com.mattmalec.pterodactyl4j.application.entities.Script;
import com.mattmalec.pterodactyl4j.utils.Relationed;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EggImpl implements Egg {

    private final JSONObject json;
    private final JSONObject relationships;
    private final PteroApplicationImpl impl;

    public EggImpl(JSONObject json, PteroApplicationImpl impl) {
        this.json = json.getJSONObject("attributes");
        this.relationships = json.getJSONObject("attributes").optJSONObject("relationships");
        this.impl = impl;
    }

    @Override
    public Relationed<Nest> getNest() {
        return new Relationed<Nest>() {
            @Override
            public PteroAction<Nest> retrieve() {
                return impl.retrieveNestById(json.getLong("nest"));
            }

            @Override
            public Optional<Nest> get() {
                if(!json.has("relationships")) return Optional.empty();
                return Optional.of(new NestImpl(relationships.getJSONObject("nest"), impl));
            }
        };
    }

    @Override
    public Optional<List<EggVariable>> getVariables() {
        if(!json.has("relationships")) return Optional.empty();
        List<EggVariable> variables = new ArrayList<>();
        JSONObject json = relationships.getJSONObject("variables");
        for(Object o : json.getJSONArray("data")) {
            JSONObject variable = new JSONObject(o.toString());
            variables.add(new EggVariableImpl(variable));
        }
        return Optional.of(Collections.unmodifiableList(variables));
    }

    @Override
    public String getAuthor() {
        return json.getString("author");
    }

    @Override
    public String getName() {
        return json.getString("name");
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
