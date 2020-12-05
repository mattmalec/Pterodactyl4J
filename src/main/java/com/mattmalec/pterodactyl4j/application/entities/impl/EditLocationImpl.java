package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.managers.abstracts.AbstractLocationAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

public class EditLocationImpl extends AbstractLocationAction {
    private Location location;

    public EditLocationImpl(PteroApplicationImpl impl, Location location) {
        super(impl);
        this.location = location;
    }

    @Override
    public PteroAction<Location> build() {
        JSONObject json = new JSONObject();

        String shortCodeToPut = (shortCode == null) ? location.getShortCode() : shortCode;
        String descToPut = (description == null) ? location.getDescription() : description;

        json.put("shortcode", shortCodeToPut);
        json.put("description", descToPut);

        return PteroActionImpl.onExecute(() -> requestEdit(json));
    }

    private LocationImpl requestEdit(JSONObject json) {
        Route.CompiledRoute route = Route.Locations.EDIT_LOCATION.compile(location.getId()).withJSONdata(json);
        JSONObject jsonObject = requester.request(route).toJSONObject();

        return new LocationImpl(jsonObject, impl);
    }
}
