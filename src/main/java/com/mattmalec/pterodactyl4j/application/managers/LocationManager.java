package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Location;

public interface LocationManager {

	LocationAction createLocation();
	LocationAction editLocation(Location location);
	PteroAction<Void> deleteLocation(Location location);

}
