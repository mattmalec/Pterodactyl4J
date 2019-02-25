package com.mattmalec.pterodactyl4j.application.managers;


public interface UserAction {

	UserAction setUsername(String username);
	UserAction setEmail(String email);
	UserAction setFirstName(String firstName);
	UserAction setLastName(String lastName);
	UserAction setPassword(String password);

}
