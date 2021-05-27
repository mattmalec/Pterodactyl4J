package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AllocationActionImpl;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;

public class CreateAllocationImpl extends AllocationActionImpl {

	public CreateAllocationImpl(PteroApplicationImpl impl, Node node) {
		super(impl, Route.Nodes.CREATE_ALLOCATION.compile(node.getId()));
		this.portSet = new HashSet<>();
	}

	@Override
	protected RequestBody finalizeData() {
		JSONObject json = new JSONObject();
		Checks.notBlank(this.ip, "IP");
		json.put("ip", this.ip);
		json.put("alias", (this.alias == null) ? "" : this.alias);
		JSONArray ports = new JSONArray();
		for(String s : portSet) ports.put(s);
		json.put("ports", ports);
		return getRequestBody(json);
	}
}
