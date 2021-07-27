package com.mattmalec.pterodactyl4j.entities;

import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.requests.Requester;
import okhttp3.OkHttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public interface P4J {

	String getToken();
	Requester getRequester();
	String getApplicationUrl();
	OkHttpClient getHttpClient();
	ExecutorService getCallbackPool();
	ExecutorService getActionPool();
	ScheduledExecutorService getRateLimitPool();
	ExecutorService getSupplierPool();
	OkHttpClient getWebSocketClient();
	PteroClient asClient();
	PteroApplication asApplication();

}
