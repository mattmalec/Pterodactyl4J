package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.managers.AllocationManager;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;

public interface Node extends ISnowflake {

	boolean isPublic();
	String getName();
	String getDescription();
	PteroAction<Location> retrieveLocation();
	AllocationManager getAllocationManager();
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
	String getDaemonListenPort();
	String getDaemonSFTPPort();
	String getDaemonBase();

	NodeAction edit();
	PteroAction<Void> delete();

}
