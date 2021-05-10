package com.mattmalec.pterodactyl4j.entities;

import java.util.UUID;

/**
 * Represents a Pterodactyl {@link com.mattmalec.pterodactyl4j.entities.Server Server}.
 * This should contain all information provided from the Pterodactyl instance about a Server.
 */
public interface Server {

	/**
	 * The UUID of the {@link com.mattmalec.pterodactyl4j.entities.Server Server}.
	 *
	 * @return Never-null {@link java.util.UUID} containing the Servers's UUID.
	 */
	UUID getUUID();

	/**
	 * The short identifier of the {@link com.mattmalec.pterodactyl4j.entities.Server Server}.
	 *
	 * @return Never-null String containing the Servers's identifier.
	 */
	String getIdentifier();

	/**
	 * The human readable name of the {@link com.mattmalec.pterodactyl4j.entities.Server Server}.
	 * <p>
	 * This value can be modified using {@link com.mattmalec.pterodactyl4j.application.managers.ServerManager#setName(String) ServerManager.setName(String)}.
	 *
	 * @return Never-null String containing the Servers's name.
	 */
	String getName();

	/**
	 * The description of the {@link com.mattmalec.pterodactyl4j.entities.Server Server}.
	 * <p>
	 * This value can be modified using {@link com.mattmalec.pterodactyl4j.application.managers.ServerManager#setDescription(String)}.
	 *
	 * @return Never-null String containing the Servers's description.
	 */
	String getDescription();

	/**
	 * The server resource limits of the {@link com.mattmalec.pterodactyl4j.entities.Server Server}.
	 * <p>
	 * These values can be modified using the {@link com.mattmalec.pterodactyl4j.application.managers.ServerManager ServerManager}.
	 *
	 * @return Never-null {@link com.mattmalec.pterodactyl4j.entities.Limit} containing the Servers's resource limits.
	 */
	Limit getLimits();

	/**
	 * The feature limits of the {@link com.mattmalec.pterodactyl4j.entities.Server Server}.
	 * <p>
	 * These values can be modified using the {@link com.mattmalec.pterodactyl4j.application.managers.ServerManager ServerManager}.
	 *
	 * @return Never-null {@link com.mattmalec.pterodactyl4j.entities.FeatureLimit} containing the Servers's feature limits.
	 */
	FeatureLimit getFeatureLimits();

}
