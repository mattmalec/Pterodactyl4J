package com.mattmalec.pterodactyl4j.application.managers;


import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

public interface UserAction {

	UserAction setUserName(String userName);
	UserAction setEmail(String email);
	UserAction setFirstName(String firstName);
	UserAction setLastName(String lastName);
	UserAction setPassword(String password);
	PteroAction<ApplicationUser> build();

}
