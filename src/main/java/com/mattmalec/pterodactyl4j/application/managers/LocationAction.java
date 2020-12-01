package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

public interface LocationAction {

	LocationAction setShortCode(String shortCode);
	LocationAction setDescription(String description);
	PteroAction<Location> build();

}
