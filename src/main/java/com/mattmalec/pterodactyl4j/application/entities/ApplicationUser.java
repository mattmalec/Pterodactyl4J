package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;
import com.mattmalec.pterodactyl4j.entities.User;

import java.util.Locale;

public interface ApplicationUser extends User, ISnowflake {

	String getFirstName();
	String getLastName();
	default String getFullName() {
		return String.format("%s %s", getFirstName(), getLastName());
	}
	String getExternalId();
	String getUUID();
	boolean has2FA();
	String getLanguage();
	default Locale getLocale() {
		return Locale.forLanguageTag(getLanguage());
	}
	boolean isRootAdmin();
	UserAction edit();
	PteroAction<Void> delete();

	@Override
	String toString();



}
