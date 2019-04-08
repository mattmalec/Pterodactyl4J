package com.mattmalec.pterodactyl4j.application.managers;


import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.User;

public interface UserAction {

	UserAction setUserName(String userName);
	UserAction setEmail(String email);
	UserAction setFirstName(String firstName);
	UserAction setLastName(String lastName);
	UserAction setPassword(String password);
	PteroAction<User> build();

}
