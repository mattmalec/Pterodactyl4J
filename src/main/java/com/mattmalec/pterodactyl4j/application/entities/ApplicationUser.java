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

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;
import com.mattmalec.pterodactyl4j.entities.User;
import com.mattmalec.pterodactyl4j.utils.Relationed;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Represents a pterodactyl {@link User}.
 *
 * @see PteroApplication#retrieveUserById(long)
 * @see PteroApplication#retrieveUserById(String)
 * @see PteroApplication#retrieveUsers()
 * @see PteroApplication#retrieveUsersByEmail(String, boolean)
 * @see PteroApplication#retrieveUsersByUsername(String, boolean)
 */
public interface ApplicationUser extends User, ISnowflake {

	/**
	 * Retrieve the first name of the {@link ApplicationUser}.
	 *
	 * @return First name
	 */
	String getFirstName();

	/**
	 * Retrieve the last name of the {@link ApplicationUser}.
	 *
	 * @return Last name
	 */
	String getLastName();

	/**
	 * Retrieve the FULL name of the {@link ApplicationUser}.
	 * Formatted: FirstName LastName
	 *
	 * @return First and last name
	 */
	default String getFullName() {
		return String.format("%s %s", getFirstName(), getLastName());
	}

	/**
	 * Retrieve the external id of the {@link ApplicationUser}.
	 *
	 * @return External id
	 */
	String getExternalId();

	/**
	 * Retrieve the {@link UUID} of the {@link ApplicationUser}.
	 *
	 * @return Unique id
	 */
	UUID getUUID();

	/**
	 * Retrieve whether the {@link ApplicationUser} has two-factory authentication enabled.
	 *
	 * @return Whether 2fa is enabled
	 */
	boolean has2FA();

	/**
	 * Retrieve the language of the {@link ApplicationUser}.
	 *
	 * @return Language
	 */
	String getLanguage();

	/**
	 * Retrieve the {@link Locale} of the {@link ApplicationUser}.
	 * This is derived from the {@link #getLanguage()}.
	 *
	 * @return Locale
	 */
	default Locale getLocale() {
		return Locale.forLanguageTag(getLanguage());
	}

	/**
	 * Retrieve whether this {@link ApplicationUser} is a root admin.
	 *
	 * @return Root admin status
	 */
	boolean isRootAdmin();

	/**
	 * Retrieve a {@link List} of {@link ApplicationServer}s associated with this {@link ApplicationUser}.
	 *
	 * @return List of servers
	 */
	PteroAction<List<ApplicationServer>> retrieveServers();

	/**
	 * Edit the {@link ApplicationUser}.
	 *
	 * @return Edit information on the user
	 */
	UserAction edit();

	/**
	 * Delete the {@link ApplicationUser}.
	 *
	 * @return Delete the user.
	 */
	PteroAction<Void> delete();

	@Override
	String toString();


}
