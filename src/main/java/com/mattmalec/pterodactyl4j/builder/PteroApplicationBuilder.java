package com.mattmalec.pterodactyl4j.builder;

import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.entities.impl.PteroAPIImpl;

class PteroApplicationBuilder {
	private String url;
	private String token;

	PteroApplicationBuilder(String url, String token) {
		this.url = url;
		this.token = token;
	}

	String getApplicationUrl() {
		return url;
	}

	void setApplicationUrl(String url) {
		this.url = url;
	}

	String getApplicationToken() {
		return token;
	}

	void setApplicationToken(String token) {
		this.token = token;
	}

	public PteroApplication build() {
		return new PteroAPIImpl(this.url, this.token).asApplication();
	}
}
