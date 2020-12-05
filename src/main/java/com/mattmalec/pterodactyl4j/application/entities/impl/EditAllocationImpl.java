package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.abstracts.AbstractAllocationAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.Route.CompiledRoute;
import org.json.JSONObject;

public class EditAllocationImpl extends AbstractAllocationAction {
	private final Allocation allocation;

	public EditAllocationImpl(PteroApplicationImpl app, Node node, Allocation allocation) {
		super(node, app);
		this.allocation = allocation;
	}

	@Override
	public PteroAction<Void> build() {
		JSONObject jsonOut = new JSONObject();

		String ipToPut = (ip == null) ? allocation.getIP() : ip;
		String aliasToPut = (alias == null) ? allocation.getAlias() : alias;

		jsonOut.put("ip", ipToPut);
		jsonOut.put("alias", aliasToPut);
		jsonOut.put("ports", portSet);

		return PteroActionImpl.onExecute(() -> {
			CompiledRoute deleteAllocation = Route.Nodes.DELETE_ALLOCATION.compile(allocation.getId());
			requester.request(deleteAllocation);
			CompiledRoute createAllocation = Route.Nodes.CREATE_ALLOCATION.compile(node.getId()).withJSONdata(jsonOut);
			requester.request(createAllocation);

			return null;
		});
	}
}
