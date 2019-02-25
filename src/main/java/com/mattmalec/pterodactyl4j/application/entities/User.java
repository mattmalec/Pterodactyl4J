package com.mattmalec.pterodactyl4j.application.entities;

import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.UUID;

public interface User extends ISnowflake {

	String getExternalId();
	String getUUID();
	String getUserName();
	String getEmail();
	String getFirstName();
	String getLastName();
	String getLanguage();
	Locale getLocale();
	boolean isRootAdmin();
	boolean has2FA();


}
