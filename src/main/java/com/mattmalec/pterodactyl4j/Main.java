package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.application.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.application.entities.User;

public class Main {

	public static void main(String[] args)  {
		PteroApplicationImpl application = new PteroApplicationImpl()
				.setApplicationUrl("https://panel.explodingbush.net")
				.setToken("wPdYseNq2hc3okrMx7eomgSAjXV117ozGnt8VJYTImDFqy23");
		PteroAction<User> action = application.retrieveUserById("1");

	}
}
