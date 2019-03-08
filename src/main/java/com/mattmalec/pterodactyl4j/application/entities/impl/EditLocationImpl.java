package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

public class EditLocationImpl implements LocationAction {

    private Requester requester;

    private String shortCode;
    private String description;
    private PteroApplicationImpl impl;

    private Location location;

    public EditLocationImpl(Location location, PteroApplicationImpl impl) {
        this.location = location;
        this.requester = impl.getRequester();
        this.impl = impl;
    }

    @Override
    public LocationAction setShortCode(String shortCode) {
        this.shortCode = shortCode;
        return this;
    }

    @Override
    public LocationAction setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public PteroAction<Location> build() {
        JSONObject json = new JSONObject();
        if(this.shortCode == null)
            json.put("shortcode", this.location.getShortCode());
        else
            json.put("shortcode", this.shortCode);
        if(this.description == null)
            json.put("description", this.location.getDescription());
        else
            json.put("description", this.description);
        return new PteroAction<Location>() {
            Route.CompiledRoute route = Route.Locations.EDIT_LOCATION.compile(location.getId()).withJSONdata(json);
            JSONObject jsonObject = requester.request(route).toJSONObject();
            @Override
            public Location execute() {
                return new LocationImpl(jsonObject, impl);
            }
        };
    }
}
