package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.requests.Requester;

public interface PteroAPI {

	String getToken();
	Requester getRequester();
	String getApplicationUrl();
	PteroAPI setToken(String token);
	PteroAPI setApplicationUrl(String applicationUrl);

}
