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
 * {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} extension designed for the creation and modification
 * of {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUsers}
 *
 * @see UserManager#createUser()
 * @see UserManager#editUser(ApplicationUser) 
 */
public interface UserAction extends PteroAction<ApplicationUser> {

	/**
	 * Sets the username for this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUser}.
	 *
	 * <br>The panel requires this value to be <b>unique</b> across the entire panel instance
	 *
	 * @param  userName
	 *         The username for the ApplicationUser
	 *
	 * @throws IllegalArgumentException
	 *         If the provided username is {@code null} or not between 1-191 characters long
	 *
	 * @return The {@link com.mattmalec.pterodactyl4j.application.managers.UserAction UserAction}
	 * instance, useful for chaining
	 */
	UserAction setUserName(String userName);

	/**
	 * Sets the email for this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUser}.
	 *
	 * <br>The panel requires this value to be <b>unique</b> across the entire panel instance
	 *
	 * <br>The panel also validates this value using the
	 * <a href=https://github.com/egulias/EmailValidator>egulias/email-validator</a> package using
	 * the <code>RFCValidation</code> validator.
	 *
	 * @param  email
	 *         The email for the ApplicationUser
	 *
	 * @throws IllegalArgumentException
	 *         If the provided email is {@code null} or not between 1-191 characters long
	 *
	 * @return The {@link com.mattmalec.pterodactyl4j.application.managers.UserAction UserAction}
	 * instance, useful for chaining
	 */
	UserAction setEmail(String email);

	/**
	 * Sets the first name for this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUser}.
	 *
	 * @param  firstName
	 *         The first name for the ApplicationUser
	 *
	 * @throws IllegalArgumentException
	 *         If the provided name is {@code null} or not between 1-191 characters long
	 *
	 * @return The {@link com.mattmalec.pterodactyl4j.application.managers.UserAction UserAction}
	 * instance, useful for chaining
	 */
	UserAction setFirstName(String firstName);

	/**
	 * Sets the last name for this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUser}.
	 *
	 * @param  lastName
	 *         The last name for the ApplicationUser
	 *
	 * @throws IllegalArgumentException
	 *         If the provided name is {@code null} or not between 1-191 characters long
	 *
	 * @return The {@link com.mattmalec.pterodactyl4j.application.managers.UserAction UserAction}
	 * instance, useful for chaining
	 */
	UserAction setLastName(String lastName);

	/**
	 * Sets the password for this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationUser ApplicationUser}.
	 *
	 * <br>If no password is provided, and SMTP on the panel is configured, the panel's queue worker will attempt to send
	 * a registration email to the address set in {@link #setEmail(String)}
	 *
	 * @param  password
	 *         The password for the ApplicationUser
	 *
	 * @return The {@link com.mattmalec.pterodactyl4j.application.managers.UserAction UserAction}
	 * instance, useful for chaining
	 */
	UserAction setPassword(String password);

}
