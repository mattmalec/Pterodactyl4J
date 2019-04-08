package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.User;

public interface UserManager {

	UserAction createUser();
	UserAction editUser(User user);
	PteroAction<Void> deleteUser(User user);

}
