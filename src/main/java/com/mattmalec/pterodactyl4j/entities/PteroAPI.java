package com.mattmalec.pterodactyl4j.entities;

import com.mattmalec.pterodactyl4j.requests.Requester;

public interface PteroAPI {

	String getToken();
	Requester getRequester();
	String getApplicationUrl();

}
