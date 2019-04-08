package com.mattmalec.pterodactyl4j.application.entities;

public interface FeatureLimit {

	String getDatabases();
	default long getDatabasesLong() { return Long.parseLong(getDatabases()); }
	String getAllocations();
	default long getAllocationsLong() { return Long.parseLong(getAllocations()); }


}
