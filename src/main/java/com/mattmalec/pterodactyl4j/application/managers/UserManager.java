package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;

public interface UserManager {

	UserAction createUser();
	UserAction editUser(ApplicationUser user);
	PteroAction<Void> deleteUser(ApplicationUser user);

}
