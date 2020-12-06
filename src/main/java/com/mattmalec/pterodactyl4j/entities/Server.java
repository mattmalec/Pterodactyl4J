package com.mattmalec.pterodactyl4j.entities;

import java.util.UUID;

public interface Server {

	UUID getUUID();
	String getIdentifier();
	String getName();
	String getDescription();
	Limit getLimits();
	FeatureLimit getFeatureLimits();

}
