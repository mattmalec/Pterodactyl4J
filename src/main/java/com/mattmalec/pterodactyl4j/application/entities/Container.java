package com.mattmalec.pterodactyl4j.application.entities;

import java.util.Map;

public interface Container {

	String getStartupCommand();
	String getImage();
	boolean isInstalled();
	Map<String, Object> getEnvironment();

}
