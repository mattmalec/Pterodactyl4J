package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.AllocationAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CreateAllocationImpl implements AllocationAction {

	private String ip;
	private String alias;
	private final Set<String> portSet = new HashSet<>();
	private final Node node;
	private final PteroApplicationImpl impl;

	public CreateAllocationImpl(PteroApplicationImpl impl, Node node) {
		this.impl = impl;
		this.node = node;
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
		Checks.notBlank(this.ip, "IP");
		json.put("ip", this.ip);
		json.put("alias", (this.alias == null) ? "" : this.alias);
		JSONArray ports = new JSONArray();
		for (String s : portSet) ports.put(s);
		json.put("ports", ports);

		return PteroActionImpl.onExecute(() -> {
			Route.CompiledRoute route = Route.Nodes.CREATE_ALLOCATION.compile(node.getId()).withJSONdata(json);
			Requester requester = impl.getRequester();
			requester.request(route);
			return null;
		});
	}
}
