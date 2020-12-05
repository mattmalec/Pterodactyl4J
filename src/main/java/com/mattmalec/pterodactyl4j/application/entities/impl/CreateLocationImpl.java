package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.managers.abstracts.AbstractLocationAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import org.json.JSONObject;

public class CreateLocationImpl extends AbstractLocationAction {
	public CreateLocationImpl(PteroApplicationImpl impl) {
		super(impl);
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
