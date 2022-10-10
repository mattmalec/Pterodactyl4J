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

package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;

/**
 * Manager providing functionality to create, modify details for, and delete an
 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUser}.
 *
 * <p><b>Example</b>
 * <pre>{@code
 * manager.createUser()
 *     .setUserName("coolguy") // set the username
 *     .setEmail("coolguy@example.com") // set the email
 *     .setFirstName("Cool") // set the first name
 *     .setLastName("Guy") // set the last name
 *     .setPassword("coolguy123") // set the password
 *     .executeAsync();
 * }</pre>
 *
 * @see PteroApplication#getUserManager()
 */
public interface UserManager {

	/**
	 * Creates a new {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUser}.
	 * <br>For this to be successful, the <b>Application API key</b> requires <b>Users</b> permission with <b>Read &amp; Write</b> access.
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions upon PteroAction execution
	 *
	 * @return A specific {@link com.mattmalec.pterodactyl4j.application.managers.UserAction UserAction}
	 *         allowing to set fields for the new ApplicationUser before creating it
	 */
	UserAction createUser();

	/**
	 * Modifies an existing {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUser}.
	 * <br>For this to be successful, the <b>Application API key</b> requires <b>Users</b> permission with <b>Read &amp; Write</b> access.
	 *
	 * @param user
	 *        The target user of which to update
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions upon PteroAction execution
	 *
	 * @return A specific {@link com.mattmalec.pterodactyl4j.application.managers.UserAction UserAction}
	 *         allowing to set fields for the existing ApplicationUser before updating it
	 */
	UserAction editUser(ApplicationUser user);

	/**
	 * Attempts to delete an existing {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUser}.
	 * <br>For this to be successful, the <b>Application API key</b> requires <b>Users</b> permission with <b>Write</b> access.
	 *
	 * @param user
	 *        The target user of which to delete
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.LoginException
	 *         If the API key is incorrect or doesn't have the required permissions upon PteroAction execution
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction}
	 */
	PteroAction<Void> deleteUser(ApplicationUser user);
}
