package com.mattmalec.pterodactyl4j.entities;

public interface FeatureLimit {

	String getDatabases();
	default long getDatabasesLong() { return Long.parseLong(getDatabases()); }
	String getAllocations();
	default long getAllocationsLong() { return Long.parseLong(getAllocations()); }


}
