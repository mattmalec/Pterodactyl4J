package com.mattmalec.pterodactyl4j.entities;

public interface Server {

	String getUUID();
	String getIdentifier();
	String getName();
	String getDescription();
	Limit getLimits();
	FeatureLimit getFeatureLimits();

}
