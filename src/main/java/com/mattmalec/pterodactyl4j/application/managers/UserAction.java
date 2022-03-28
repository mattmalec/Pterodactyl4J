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
 * Represents a {@link PteroAction} that can be used for managing an {@link ApplicationUser}.
 *
 * @see PteroAction
 */
// TODO: Add #setLanguage option
public interface UserAction extends PteroAction<ApplicationUser> {

	/**
	 * Sets the username of the {@link ApplicationUser}.
	 *
	 * @param userName The new username
	 * @return The same action instance with the applied username
	 * @see ApplicationUser#getUserName()
	 */
	UserAction setUserName(String userName);

	/**
	 * Sets the email of the {@link ApplicationUser}
	 *
	 * @param email The new email
	 * @return The same action instance with the applied email
	 * @see ApplicationUser#getEmail()
	 */
	UserAction setEmail(String email);

	/**
	 * Sets the first name of the {@link ApplicationUser}.
	 *
	 * @param firstName The new first name
	 * @return The same action instance with the applied first name
	 * @see ApplicationUser#getFirstName()
	 */
	UserAction setFirstName(String firstName);

	/**
	 * Sets the last name of the {@link ApplicationUser}.
	 *
	 * @param lastName The new last name
	 * @return The same action instance with the applied last name
	 * @see ApplicationUser#getLastName()
	 */
	UserAction setLastName(String lastName);

	/**
	 * Sets the password of the {@link ApplicationUser}.
	 *
	 * @param password The new password
	 * @return The same action instance with the applied password
	 */
	UserAction setPassword(String password);

}
