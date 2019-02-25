package com.mattmalec.pterodactyl4j.application.entities;

public interface Node {

	boolean isPublic();
	String getName();
	String getDescription();
	Location getLocation();
	String getFQDN();
	String getScheme();
	boolean isBehindProxy();
	boolean hasMaintanceMode();
	String getMemory();
	default long getMemoryLong() { return Long.parseLong(getMemory()); }
	String getMemoryOverallocate();
	default long getMemoryOverallocateLong() { return Long.parseLong(getMemoryOverallocate()); }
	String getDisk();
	default long getDiskLong() { return Long.parseLong(getDisk()); }
	String getDiskOverallocate();
	default long getDiskOverallocateLong() { return Long.parseLong(getDiskOverallocate()); }
	String getUploadLimit();
	default long getUploadLimitLong() { return Long.parseLong(getUploadLimit()); }

}
