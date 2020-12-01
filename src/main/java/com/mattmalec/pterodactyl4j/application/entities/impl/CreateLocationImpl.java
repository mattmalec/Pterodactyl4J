package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import org.json.JSONObject;

public class CreateLocationImpl implements LocationAction {

	private final Requester requester;

	private String shortCode;
	private String description;

	private final PteroApplicationImpl impl;

	CreateLocationImpl(PteroApplicationImpl impl, Requester requester) {
		this.requester = requester;
		this.impl = impl;
	}

	@Override
	public LocationAction setShortCode(String shortCode) {
		this.shortCode = shortCode;
		return this;
	}

	@Override
	public LocationAction setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public PteroAction<Location> build() {
		Checks.notBlank(this.shortCode, "Shortcode");
		Checks.notBlank(this.description, "Description");
		JSONObject json = new JSONObject();
		json.put("short", this.shortCode);
		json.put("long", this.description);

		return PteroActionImpl.onExecute(() -> {
			Route.CompiledRoute route = Route.Locations.CREATE_LOCATION.compile().withJSONdata(json);
			JSONObject jsonObject = requester.request(route).toJSONObject();

			return new LocationImpl(jsonObject, impl);
		});
	}
}
