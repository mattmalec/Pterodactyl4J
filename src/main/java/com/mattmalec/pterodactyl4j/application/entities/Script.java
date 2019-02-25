package com.mattmalec.pterodactyl4j.application.entities;

public interface Script {

	boolean isPrivileged();
	String getInstall();
	String getEntry();
	String getContainer();
	String getExtends();
}
