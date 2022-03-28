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

package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;

/**
 * Manager providing functionality for modifying an {@link ApplicationUser}.
 *
 * <p><b>Example</b>
 * <pre>{@code
 * manager.createUser()
 *        .setFirstName("Matt")
 *        .setLastName("Malec")
 *        .setUserName("Matty")
 *        .setEmail("example@gmail.com")
 *        .executeAsync();
 * }</pre>
 *
 * @see com.mattmalec.pterodactyl4j.application.entities.PteroApplication#getUserManager()
 */
public interface UserManager {

	/**
	 * Obtain a new instance of {@link UserAction} for CREATING a new {@link ApplicationUser}.
	 *
	 * @return User action instance
	 * @see UserAction
	 */
	UserAction createUser();

	/**
	 * Obtain a new instance of {@link UserAction} for EDITING an {@link ApplicationUser}s information.
	 *
	 * @param user The {@link ApplicationUser} that will be edited
	 * @return User action instance
	 */
	UserAction editUser(ApplicationUser user);

	/**
	 * Deletes the {@link ApplicationUser}.
	 *
	 * @param user The {@link ApplicationUser} to be deleted
	 * @return Void
	 */
	PteroAction<Void> deleteUser(ApplicationUser user);

}
