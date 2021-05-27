package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.EnvironmentValue;

import java.util.Map;

public interface Container {

	/**
	 * The start up command of the ApplicationServer
	 *
	 * @return Never-null String containing the Servers's start up command.
	 */
	String getStartupCommand();

	/**
	 * The Docker image used to run the ApplicationServer
	 *
	 * @return Never-null String containing the Servers's Docker image.
	 */
	String getImage();

	/**
	 * Returns whether the ApplicationServer is currently in an installing state
	 *
	 * @return True - if the Server has finished installing.
	 */
	boolean isInstalled();

	/**
	 * The map of environment variables for the ApplicationServer
	 *
	 * @return The map of environment variables
	 */
	Map<String, EnvironmentValue<?>> getEnvironment();

}
