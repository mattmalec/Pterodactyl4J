package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.EnvironmentValue;

import java.util.Map;

public interface Container {

	String getStartupCommand();
	String getImage();
	boolean isInstalled();
	Map<String, EnvironmentValue<?>> getEnvironment();

}
