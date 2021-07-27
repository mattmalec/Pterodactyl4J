package com.mattmalec.pterodactyl4j.requests.action;

import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.impl.LocationImpl;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.requests.Route;

public abstract class AbstractLocationAction extends PteroActionImpl<Location> implements LocationAction {

    protected String shortCode;
    protected String description;

    public AbstractLocationAction(PteroApplicationImpl impl, Route.CompiledRoute route) {
        super(impl.getP4J(), route, (response, request) -> new LocationImpl(response.getObject(), impl));
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
}
