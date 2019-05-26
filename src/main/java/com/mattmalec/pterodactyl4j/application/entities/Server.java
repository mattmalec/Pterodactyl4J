package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.entities.FeatureLimit;
import com.mattmalec.pterodactyl4j.entities.Limit;

public interface Server {

	String getUUID();
	String getIdentifier();
	String getName();
	String getDescription();
	Limit getLimits();
	FeatureLimit getFeatureLimits();

}
