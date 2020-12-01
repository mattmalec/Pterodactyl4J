package com.mattmalec.pterodactyl4j.builder;

import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.entities.impl.PteroAPIImpl;

class PteroClientBuilder
{
	private String url;
	private String token;

	PteroClientBuilder(String url, String token)
	{
		this.url = url;
		this.token = token;
	}

	public String getClientUrl()
	{
		return url;
	}

	public void setClientUrl(String url)
	{
		this.url = url;
	}

	public String getClientToken()
	{
		return token;
	}

	public void setClientToken(String token)
	{
		this.token = token;
	}

	public PteroClient build()
	{
		return new PteroAPIImpl(this.url, this.token).asClient();
	}
}
