package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.application.managers.LocationManager;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;

public class LocationManagerImpl implements LocationManager {

	private Requester requester;
	private PteroApplicationImpl impl;

	public LocationManagerImpl(PteroApplicationImpl impl, Requester requester) {
		this.requester = requester;
		this.impl = impl;
	}

	@Override
	public LocationAction createLocation() {
		return new CreateLocationImpl(this.impl, this.requester);
	}

	@Override
	public LocationAction editLocation(Location location) {
		return new EditLocationImpl(location, this.impl);
	}

	@Override
	public PteroAction<Void> deleteLocation(Location location) {
		return PteroActionImpl.onExecute(() -> {
			Route.CompiledRoute route = Route.Locations.DELETE_LOCATION.compile(location.getId());
			requester.request(route);
			return null;
		});
	}
}
