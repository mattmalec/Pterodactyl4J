package com.mattmalec.pterodactyl4j.application.entities;

public interface Allocation extends ISnowflake {

	String getIP();
	String getFullAddress();
	String getAlias();
	String getPort();
	default long getPortLong() { return Long.parseLong(getPort()); }
	boolean isAssigned();

}
