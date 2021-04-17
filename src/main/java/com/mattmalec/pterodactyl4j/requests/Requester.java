package com.mattmalec.pterodactyl4j.requests;

import com.mattmalec.pterodactyl4j.entities.PteroAPI;
import com.mattmalec.pterodactyl4j.exceptions.*;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class Requester {

	private PteroAPI api;
	private Response response;
	private OkHttpClient okHttpClient = new OkHttpClient();
	private static final String PTERODACTYL_API_PREFIX = "%s/api/";
	private String responseBody;

	public Requester(PteroAPI api) {
        this.api = api;
    }

	public Requester request(Route.CompiledRoute compiledRoute) {
		if(api.getApplicationUrl() == null || api.getApplicationUrl().isEmpty())
			throw new HttpException("No Pterodactyl URL was defined.");
		String applicationUrl = api.getApplicationUrl();
		if(applicationUrl.endsWith("/"))
			applicationUrl = applicationUrl.substring(0, applicationUrl.length() - 1);
		String url = String.format(PTERODACTYL_API_PREFIX, applicationUrl) + compiledRoute.getCompiledRoute();
		Request.Builder builder = new Request.Builder();
		builder.header("Content-Type", "application/json");
		Method method = compiledRoute.getMethod();
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), compiledRoute.getJSONData() != null ? compiledRoute.getJSONData().toString().getBytes() : "".getBytes());
		switch(method) {
			case GET: builder.get();
				break;
			case POST: builder.post(requestBody);
				break;
			case PATCH: builder.patch(requestBody);
				break;
			case PUT: builder.put(requestBody);
				break;
			case DELETE: builder.delete(requestBody);
				break;
		}
		builder.addHeader("Accept", "Application/vnd.pterodactyl.v1+json");
		if(api.getToken() == null || api.getToken().isEmpty())
			throw new LoginException("No authorization token was defined.");
		builder.header("Authorization", "Bearer " + api.getToken());
		builder.url(url);
		try {
			this.response = okHttpClient.newCall(builder.build()).execute();
			this.responseBody = this.response.body().string();
		} catch (IOException ex) {
			throw new HttpException("Could not successfully execute a request.", ex.getCause());
		}
		if(this.response.isSuccessful()) {
			return this;
		} else {
			int responseCode = this.response.code();
			switch (responseCode) {
				case 403:
					throw new LoginException("The provided token is either incorrect or does not have access to process this request.");
				case 404:
					throw new NotFoundException("The requested entity was not found.");
				case 422:
					throw new MissingActionException("The request is missing required fields.", toJSONObject());
				case 429:
					throw new RateLimitedException("The request was rate limited.", toJSONObject());
				case 500:
					throw new ServerException("The server has encountered an Internal Server Error. Route: " + url);
				default:
					throw new HttpException("Pterodactyl4J has encountered a " + response.code() + " error.", toJSONObject());

			}
		}
	}
	public JSONObject toJSONObject() {
		return new JSONObject(this.responseBody);
	}
}
