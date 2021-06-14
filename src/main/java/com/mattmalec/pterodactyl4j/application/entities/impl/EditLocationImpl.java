package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractLocationAction;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class EditLocationImpl extends AbstractLocationAction {

    private Location location;

    EditLocationImpl(Location location, PteroApplicationImpl impl) {
        super(impl, Route.Locations.EDIT_LOCATION.compile(location.getId()));
        this.location = location;
    }

    @Override
    protected RequestBody finalizeData() {
        JSONObject json = new JSONObject();
        json.put("short", shortCode == null ? location.getShortCode() : shortCode);
        json.put("long", description == null ? location.getDescription() : description);
        return getRequestBody(json);
    }
}
