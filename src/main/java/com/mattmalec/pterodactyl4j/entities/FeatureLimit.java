package com.mattmalec.pterodactyl4j.entities;

public interface FeatureLimit {

	long getDatabasesLong();
	default String getDatabases() { return Long.toUnsignedString(getDatabasesLong()); }

	long getAllocationsLong();
	default String getAllocations() { return Long.toUnsignedString(getAllocationsLong()); }

	long getBackupsLong();
	default String getBackups() { return Long.toUnsignedString(getBackupsLong()); }

}
