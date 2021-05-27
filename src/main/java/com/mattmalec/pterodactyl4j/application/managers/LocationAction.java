package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Location;

public interface LocationAction extends PteroAction<Location> {

	LocationAction setShortCode(String shortCode);
	LocationAction setDescription(String description);

}
