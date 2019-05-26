package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;

import java.util.Locale;

public interface User extends ISnowflake {

	String getExternalId();
	String getUUID();
	String getUserName();
	String getEmail();
	String getFirstName();
	String getLastName();
	String getFullName();
	String getLanguage();
	Locale getLocale();
	boolean isRootAdmin();
	boolean has2FA();
	UserAction edit();
	PteroAction<Void> delete();

	@Override
	String toString();



}
