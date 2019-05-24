package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;

public interface Server extends ISnowflake {

	String getExternalId();
	String getUUID();
	String getIdentifier();
	String getName();
	String getDescription();
	boolean isSuspended();
	Limit getLimits();
	FeatureLimit getFeatureLimits();
	PteroAction<User> retrieveOwner();
	PteroAction<Node> retrieveNode();
	PteroAction<Allocation> retrieveAllocation();
	PteroAction<Nest> retrieveNest();
	PteroAction<Egg> retrieveEgg();
	String getPack();
	Container getContainer();

}
