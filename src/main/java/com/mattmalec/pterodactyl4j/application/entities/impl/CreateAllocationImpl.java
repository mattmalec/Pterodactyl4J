package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.abstracts.AbstractAllocationAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.Route.CompiledRoute;
import com.mattmalec.pterodactyl4j.utils.Checks;
import org.json.JSONObject;

public class CreateAllocationImpl extends AbstractAllocationAction {

	public CreateAllocationImpl(Node node, PteroApplicationImpl app) {
		super(node, app);
	}

	@Override
	public PteroAction<Void> build() {
		JSONObject jsonOut = new JSONObject();

		Checks.notBlank(ip, "IP");

		jsonOut.put("ip", ip);
		jsonOut.put("alias", (alias == null) ? "" : alias);
		jsonOut.put("ports", portSet);

		return PteroActionImpl.onExecute(() -> requestCreation(jsonOut));
	}

	private Void requestCreation(JSONObject jsonOut) {
		CompiledRoute route = Route.Nodes.CREATE_ALLOCATION.compile(node.getId()).withJSONdata(jsonOut);
		requester.request(route);

		return null;
	}
}
