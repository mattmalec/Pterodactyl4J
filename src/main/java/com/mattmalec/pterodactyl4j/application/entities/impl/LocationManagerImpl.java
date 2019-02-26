package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.application.managers.LocationManager;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;

public class LocationManagerImpl implements LocationManager {

	private Requester requester;

	public LocationManagerImpl(Requester requester) {
		this.requester = requester;
	}

	@Override
	public LocationAction createLocation() {
		return new CreateLocationImpl(this.requester);
	}

	@Override
	public LocationAction editLocation(Location location) {
		return null;
	}

	@Override
	public PteroAction<Void> deleteLocation(Location location) {
		return new PteroAction<Void>() {
			Route.CompiledRoute route = Route.Locations.DELETE_LOCATION.compile(location.getId());
			@Override
			public Void execute() {
				requester.request(route);
				return null;
			}
		};
	}
}
