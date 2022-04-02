/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.entities;

import java.util.UUID;

/**
 * Represents a Pterodactyl {@link com.mattmalec.pterodactyl4j.entities.Server Server}.
 * This should contain all information provided from the Pterodactyl instance about a Server.
 */
public interface Server {

	/**
	 * The UUID of the Server.
	 *
	 * @return Never-null {@link java.util.UUID} containing the Servers's UUID.
	 */
	UUID getUUID();

	/**
	 * The short identifier of the Server.
	 *
	 * @return Never-null String containing the Servers's identifier.
	 */
	String getIdentifier();

	/**
	 * The human readable name of the Server.
	 * <p>
	 * This value can be modified using {@link com.mattmalec.pterodactyl4j.application.managers.ServerDetailManager#setName(String) ServerDetailManager.setName(String)}.
	 *
	 * @return Never-null String containing the Servers's name.
	 */
	String getName();

	/**
	 * The description of the Server
	 * <p>
	 * This value can be modified using {@link com.mattmalec.pterodactyl4j.application.managers.ServerDetailManager#setDescription(String) ServerDetailManager#setDescription(String)}.
	 *
	 * @return Possibly-null String containing the Servers's description.
	 */
	String getDescription();

	/**
	 * The server resource limits of the Server.
	 * <p>
	 * These values can be modified using the {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}.
	 *
	 * @return Never-null {@link com.mattmalec.pterodactyl4j.entities.Limit} containing the Servers's resource limits.
	 */
	Limit getLimits();

	/**
	 * The feature limits of the Server.
	 * <p>
	 * These values can be modified using the {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}.
	 *
	 * @return Never-null {@link com.mattmalec.pterodactyl4j.entities.FeatureLimit} containing the Servers's feature limits.
	 */
	FeatureLimit getFeatureLimits();

}
