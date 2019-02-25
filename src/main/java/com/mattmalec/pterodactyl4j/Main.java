package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.application.PteroApplicationBuilder;
import com.mattmalec.pterodactyl4j.application.entities.User;
import com.mattmalec.pterodactyl4j.application.managers.UserAction;


public class Main {

	public static void main(String[] args)  {
		PteroApplication application = new PteroApplicationBuilder()
				.setApplicationUrl("https://panel.explodingbush.net")
				.setToken("wPdYseNq2hc3okrMx7eomgSAjXV117ozGnt8VJYTImDFqy23").build();
		UserAction userAction = application.getUserManager().createUser();
		User user = userAction.setUserName("mattisfat").setEmail("mmalec@4dproductions.org").setFirstName("Matt").setLastName("Is Fat").build().execute();
		System.out.println(user.toString());
	}
}
