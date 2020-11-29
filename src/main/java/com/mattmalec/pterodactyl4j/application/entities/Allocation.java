package com.mattmalec.pterodactyl4j.application.entities;

public interface Allocation {

	String getIP();
	String getFullAddress();
	String getAlias();
	String getPort();
	String getNotes();
	default long getPortLong() { return Long.parseLong(getPort()); }
	boolean isAssigned();
	long getIdLong();
	default String getId() { return Long.toUnsignedString(getIdLong()); }

}
