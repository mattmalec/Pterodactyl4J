package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.requests.Requester;

public interface PteroBuilder {

	String getToken();
	String getApplicationUrl();
	PteroBuilder setToken(String token);
	PteroBuilder setApplicationUrl(String applicationUrl);

}
