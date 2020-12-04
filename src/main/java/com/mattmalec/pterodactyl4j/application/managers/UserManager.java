package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

public interface UserManager {

	UserAction createUser();
	UserAction editUser(ApplicationUser user);
	PteroAction<Void> deleteUser(ApplicationUser user);

}
