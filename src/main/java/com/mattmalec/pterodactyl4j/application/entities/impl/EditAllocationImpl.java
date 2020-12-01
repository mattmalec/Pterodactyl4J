package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.AllocationAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EditAllocationImpl implements AllocationAction {

	private String ip;
	private String alias;
	private final Set<String> portSet = new HashSet<>();
	private final Node node;
	private final PteroApplicationImpl impl;
	private final Allocation allocation;

	public EditAllocationImpl(PteroApplicationImpl impl, Node node, Allocation allocation) {
		this.impl = impl;
		this.node = node;
		this.allocation = allocation;
	}

	@Override
	public AllocationAction setIP(String ip) {
		this.ip = ip;
		return this;
	}

	@Override
	public AllocationAction setAlias(String alias) {
		this.alias = alias;
		return this;
	}

	@Override
	public AllocationAction setPorts(String... ports) {
		portSet.clear();
		portSet.addAll(Arrays.asList(ports));
		return this;
	}

	@Override
	public AllocationAction addPorts(String... ports) {
		portSet.addAll(Arrays.asList(ports));
		return this;
	}

	@Override
	public AllocationAction addPort(String port) {
		portSet.add(port);
		return this;
	}

	@Override
	public AllocationAction removePort(String port) {
		portSet.remove(port);
		return this;
	}

	@Override
	public AllocationAction removePorts(String... ports) {
		portSet.removeAll(Arrays.asList(ports));
		return this;
	}

	@Override
	public PteroAction<Void> build() {
		JSONObject json = new JSONObject();
		if(this.ip == null)
			json.put("ip", this.allocation.getIP());
		else
			json.put("ip", this.ip);
		if (this.alias == null)
			json.put("alias", allocation.getAlias());
		else
			json.put("alias", this.alias);
		JSONArray ports = new JSONArray();
		for (String s : portSet) ports.put(s);
		json.put("ports", ports);
		return PteroActionImpl.onExecute(() -> {
			Requester requester = impl.getRequester();
			Route.CompiledRoute deleteAllocation = Route.Nodes.DELETE_ALLOCATION.compile(allocation.getId());
			requester.request(deleteAllocation);
			Route.CompiledRoute createAllocation = Route.Nodes.CREATE_ALLOCATION.compile(node.getId()).withJSONdata(json);
			requester.request(createAllocation);
			return null;
		});
	}
}
