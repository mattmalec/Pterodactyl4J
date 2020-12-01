package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;
import com.mattmalec.pterodactyl4j.entities.User;
import com.mattmalec.pterodactyl4j.utils.Relationed;

import java.util.List;
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
	Relationed<List<ApplicationServer>> getServers();
	UserAction edit();
	PteroAction<Void> delete();

	@Override
	String toString();



}
