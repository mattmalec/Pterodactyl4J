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

package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.entities.Egg;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents an {@link Egg} from an {@link ApplicationServer}.
 */
public interface ApplicationEgg extends Egg, ISnowflake {

	/**
	 * Retrieve the {@link Nest} associated with this {@link ApplicationEgg}.
	 *
	 * @return Nest
	 */
	PteroAction<Nest> retrieveNest();

	/**
	 * Retrieve an {@link Optional} {@link List} of {@link EggVariable}s associated with this {@link ApplicationEgg}.
	 *
	 * @return Optional list of egg variables
	 */
	Optional<List<EggVariable>> getVariables();

	/**
	 * Retrieve an {@link Optional} {@link Map} of default variables associated with this {@link ApplicationEgg}.
	 *
	 * @return Optional map of variables
	 */
	Optional<Map<String, EnvironmentValue<?>>> getDefaultVariableMap();

	/**
	 * Retrieve the author of this {@link ApplicationEgg}.
	 *
	 * @return author
	 */
	String getAuthor();

	/**
	 * Retrieve the description of this {@link ApplicationEgg}.
	 *
	 * @return description
	 */
	String getDescription();

	/**
	 * Retrieve the docker image of this {@link ApplicationEgg}.
	 * @return docker image
	 */
	String getDockerImage();

	/**
	 * Retrieve the stop command of this {@link ApplicationEgg}.
	 *
	 * @return stop command
	 */
	String getStopCommand();

	/**
	 * Retrieve the start-up command of this {@link ApplicationEgg}.
	 *
	 * @return start-up command
	 */
	String getStartupCommand();

	/**
	 * Retrieve the installation script of this {@link ApplicationEgg}.
	 *
	 * @return installation script
	 */
	Script getInstallScript();

	/**
	 * Represents an {@link EggVariable} for an {@link ApplicationEgg}.
	 */
	interface EggVariable extends Egg.EggVariable, ISnowflake {

		/**
		 * Retrieve whether the {@link EggVariable} is viewable by the user.
		 *
		 * @return Whether the variable is viewable by the user.
		 */
		boolean isUserViewable();

		/**
		 * Retrieve whether the {@link EggVariable} is editable by the user.
		 *
		 * @return Whether the variable is editable by the user.
		 */
		boolean isUserEditable();
	}

}
