package com.mattmalec.pterodactyl4j.entities;

import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.requests.Requester;

public interface PteroAPI {

	String getToken();
	Requester getRequester();
	String getApplicationUrl();
	PteroClient asClient();
	PteroApplication asApplication();

}
