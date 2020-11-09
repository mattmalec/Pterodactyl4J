package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;
import com.mattmalec.pterodactyl4j.entities.User;

public interface ApplicationUser extends User, ISnowflake {

	String getExternalId();
	String getUUID();
	boolean has2FA();
	UserAction edit();
	PteroAction<Void> delete();

	@Override
	String toString();



}
