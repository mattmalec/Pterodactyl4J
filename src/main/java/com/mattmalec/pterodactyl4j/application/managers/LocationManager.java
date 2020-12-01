package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

public interface LocationManager {

	LocationAction createLocation();
	LocationAction editLocation(Location location);
	PteroAction<Void> deleteLocation(Location location);

}
