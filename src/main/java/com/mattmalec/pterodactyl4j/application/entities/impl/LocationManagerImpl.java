package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.application.managers.LocationManager;
import com.mattmalec.pterodactyl4j.requests.Route;

public class LocationManagerImpl implements LocationManager {

	private final PteroApplicationImpl impl;

	public LocationManagerImpl(PteroApplicationImpl impl) {
		this.impl = impl;
	}

	@Override
	public LocationAction createLocation() {
		return new CreateLocationImpl(impl);
	}

	@Override
	public LocationAction editLocation(Location location) {
		return new EditLocationImpl(location, impl);
	}

	@Override
	public PteroAction<Void> deleteLocation(Location location) {
		return PteroActionImpl.onRequestExecute(impl.getPteroApi(), Route.Locations.DELETE_LOCATION.compile(location.getId()));
	}
}
