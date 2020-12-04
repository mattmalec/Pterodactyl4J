package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.Egg;
import com.mattmalec.pterodactyl4j.application.entities.Nest;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.utils.Relationed;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NestImpl implements Nest {

    private JSONObject json;
    private JSONObject relationships;
    private PteroApplicationImpl impl;

    public NestImpl(JSONObject json, PteroApplicationImpl impl) {
        this.json = json.getJSONObject("attributes");
        this.relationships = json.getJSONObject("attributes").optJSONObject("relationships");
        this.impl = impl;
    }

    @Override
    public String getUUID() {
        return json.getString("uuid");
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
    public Relationed<List<Egg>> getEggs() {
        NestImpl nest = this;
        return new Relationed<List<Egg>>() {
            @Override
            public PteroAction<List<Egg>> retrieve() {
                return impl.retrieveEggsByNest(nest);
            }

            @Override
            public Optional<List<Egg>> get() {
                if(!json.has("relationships")) return Optional.empty();
                List<Egg> eggs = new ArrayList<>();
                JSONObject json = relationships.getJSONObject("eggs");
                for(Object o : json.getJSONArray("data")) {
                    JSONObject egg = new JSONObject(o.toString());
                    eggs.add(new EggImpl(egg, impl));
                }
                return Optional.of(Collections.unmodifiableList(eggs));
            }
        };
    }

    @Override
    public Optional<List<ApplicationServer>> getServers() {
        if(!json.has("relationships")) return Optional.empty();
        List<ApplicationServer> servers = new ArrayList<>();
        JSONObject json = relationships.getJSONObject("servers");
        for(Object o : json.getJSONArray("data")) {
            JSONObject server = new JSONObject(o.toString());
            servers.add(new ApplicationServerImpl(impl, server));
        }
        return Optional.of(Collections.unmodifiableList(servers));
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
