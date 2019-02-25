package com.mattmalec.pterodactyl4j.application.entities;

import java.util.HashMap;

public interface Container {

	String getStartupCommand();
	String getImage();
	boolean isInstalled();
	HashMap<String, String> getEnvironment();

}
