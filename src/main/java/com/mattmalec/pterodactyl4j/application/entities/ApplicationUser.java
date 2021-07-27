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

public interface ApplicationUser extends User, ISnowflake {

	String getFirstName();
	String getLastName();
	default String getFullName() {
		return String.format("%s %s", getFirstName(), getLastName());
	}
	String getExternalId();
	UUID getUUID();
	boolean has2FA();
	String getLanguage();
	default Locale getLocale() {
		return Locale.forLanguageTag(getLanguage());
	}
	boolean isRootAdmin();
	Relationed<List<ApplicationServer>> getServers();
	UserAction edit();
	PteroAction<Void> delete();

	@Override
	String toString();



}
