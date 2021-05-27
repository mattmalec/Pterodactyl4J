package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.LocationActionImpl;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class CreateLocationImpl extends LocationActionImpl {

	CreateLocationImpl(PteroApplicationImpl impl) {
		super(impl, Route.Locations.CREATE_LOCATION.compile());
	}

	@Override
	protected RequestBody finalizeData() {
		Checks.notBlank(this.shortCode, "Shortcode");
		Checks.notBlank(this.description, "Description");
		JSONObject json = new JSONObject();
		json.put("short", this.shortCode);
		json.put("long", this.description);
		return getRequestBody(json);
	}
}
