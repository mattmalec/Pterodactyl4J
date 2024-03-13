/*
 *    Copyright 2021-2022 Matt Malec, and the Pterodactyl4J contributors
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

package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.managers.ServerController;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.managers.ClientServerManager;
import com.mattmalec.pterodactyl4j.entities.Egg;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a Pterodactyl {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg ApplicationEgg}.
 * This should contain all information provided from the Pterodactyl instance about an ApplicationEgg.
 */
public interface ApplicationEgg extends Egg, ISnowflake {

	/**
	 * The Nest the ApplicationEgg is associated with
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type {@link com.mattmalec.pterodactyl4j.application.entities.Nest Nest}
	 */
	PteroAction<Nest> retrieveNest();

	/**
	 * The egg variables assigned to the ApplicationEgg
	 *
	 * @return {@link java.util.Optional Optional} - Type {@link java.util.List List} of {@link EggVariable EggVariables}
	 */
	Optional<List<EggVariable>> getVariables();

	/**
	 * The default variables for this ApplicationEgg
	 *
	 * @return {@link java.util.Optional Optional} - Type {@link java.util.Map Map} of {@link EnvironmentValue EnvironmentVariables}
	 */
	Optional<Map<String, EnvironmentValue<?>>> getDefaultVariableMap();

	/**
	 * The immutable author of the ApplicationEgg formatted as an email address
	 *
	 * @return Never-null String containing the egg author's email
	 */
	String getAuthor();

	/**
	 * The description of the ApplicationEgg
	 *
	 * @return Never-null String containing the egg's description
	 */
	String getDescription();

	/**
	 * The Docker image associated with the ApplicationEgg
	 *
	 * @return Never-null String containing the egg's Docker image.
	 */
	String getDockerImage();

	List<DockerImage> getDockerImages();

	/**
	 * The stop command for the ApplicationEgg
	 * <br>This is ran when a user executes {@link ClientServer#stop()} or hits the <code>Stop</code> button on the panel
	 *
	 * @return Never-null String containing the egg's stop command
	 */
	String getStopCommand();

	/**
	 * The start command for the ApplicationEgg
	 * <br>This is ran when a user executes {@link ClientServer#start()} ()} or hits the <code>Start</code> button on the panel
	 *
	 * @return Never-null String containing the egg's start command
	 */
	String getStartupCommand();

	/**
	 * The installation script for this ApplicationEgg.
	 * <br>This script is ran when a user installs a {@link com.mattmalec.pterodactyl4j.entities.Server Server} for the first time,
	 * or triggers a reinstall
	 *
	 * @return Never-null installation script
	 *
	 * @see ClientServerManager#reinstall()
	 * @see ServerController#reinstall()
	 */
	Script getInstallScript();

	/**
	 * Represents an {@link EggVariable EggVariable} associated with an {@link ApplicationEgg}.
	 */
	interface EggVariable extends Egg.EggVariable, ISnowflake {

		/**
		 * Returns whether the {@link EggVariable EggVariable} is viewable by the end-user.
		 *
		 * @return True - if the variable is viewable by the end-user.
		 */
		boolean isUserViewable();

		/**
		 * Returns whether the {@link EggVariable EggVariable} is editable by the end-user.
		 *
		 * @return True - if the variable is editable by the end-user.
		 */
		boolean isUserEditable();
	}
}
