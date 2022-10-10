/*
 *    Copyright 2021-2022 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.application.managers.LocationManager;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
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
		return PteroActionImpl.onRequestExecute(
				impl.getP4J(), Route.Locations.DELETE_LOCATION.compile(location.getId()));
	}
}
