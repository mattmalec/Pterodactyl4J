package com.mattmalec.pterodactyl4j.application.entities;

public interface Server extends ISnowflake {

	String getExternalId();
	String getUUID();
	String getIdentifier();
	String getName();
	String getDescription();
	boolean isSuspended();
	Limit getLimits();
	FeatureLimit getFeatureLimits();
	User getOwner();
	Node getNode();
	Allocation getAllocation();
	Nest getNest();
	Egg getEgg();
	String getPack();
	Container getContainer();

}
