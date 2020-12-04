package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.application.managers.AllocationManager;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.utils.Relationed;

import java.util.List;

public interface Node extends ISnowflake {

	boolean isPublic();
	String getName();
	String getDescription();
	Relationed<Location> getLocation();
	AllocationManager getAllocationManager();
	String getFQDN();
	String getScheme();
	boolean isBehindProxy();
	boolean hasMaintanceMode();
	default String getMemory() { return Long.toUnsignedString(getMemoryLong()); }
	long getMemoryLong();
	default String getMemoryOverallocate() { return Long.toUnsignedString(getMemoryOverallocateLong()); }
	long getMemoryOverallocateLong();
	default String getDisk() { return Long.toUnsignedString(getDiskLong()); }
	long getDiskLong();
	default String getDiskOverallocate() { return  Long.toUnsignedString(getDiskOverallocateLong()); }
	long getDiskOverallocateLong();
	default String getUploadLimit() { return Long.toUnsignedString(getUploadLimitLong()); }
	long getUploadLimitLong();
	long getAllocatedMemoryLong();
	default String getAllocatedMemory() { return Long.toUnsignedString(getAllocatedMemoryLong()); }
	long getAllocatedDiskLong();
	default String getAllocatedDisk() { return Long.toUnsignedString(getAllocatedDiskLong()); }
	String getDaemonListenPort();
	String getDaemonSFTPPort();
	String getDaemonBase();
	Relationed<List<ApplicationServer>> getServers();
	Relationed<List<Allocation>> getAllocations();

	NodeAction edit();
	PteroAction<Void> delete();

}
