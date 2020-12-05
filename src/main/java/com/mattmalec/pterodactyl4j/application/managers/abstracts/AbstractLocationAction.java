package com.mattmalec.pterodactyl4j.application.managers.abstracts;

import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.requests.Requester;

public abstract class AbstractLocationAction implements LocationAction {
    protected String shortCode;
    protected String description;
    protected Requester requester;
    protected PteroApplicationImpl impl;

    public AbstractLocationAction(PteroApplicationImpl impl) {
        this.impl = impl;
        this.requester = impl.getRequester();
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
