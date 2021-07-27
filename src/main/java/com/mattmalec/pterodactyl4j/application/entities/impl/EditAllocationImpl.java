package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractAllocationAction;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.util.function.Consumer;

public class EditAllocationImpl extends AbstractAllocationAction {

	private final PteroApplicationImpl impl;
	private final Allocation allocation;

	public EditAllocationImpl(PteroApplicationImpl impl, Node node, Allocation allocation) {
		super(impl, Route.Nodes.CREATE_ALLOCATION.compile(node.getId()));
		this.impl = impl;
		this.allocation = allocation;
	}

	@Override
	public Void execute() {
		PteroActionImpl.onRequestExecute(impl.getP4J(),
				Route.Nodes.DELETE_ALLOCATION.compile(allocation.getId())).execute();
		return super.execute();
	}

	@Override
	public void executeAsync(Consumer<? super Void> success, Consumer<? super Throwable> failure) {
		PteroActionImpl.onRequestExecute(impl.getP4J(),
				Route.Nodes.DELETE_ALLOCATION.compile(allocation.getId())).executeAsync();
		super.executeAsync(success, failure);
	}

	@Override
	protected RequestBody finalizeData() {
		JSONObject json = new JSONObject();
		json.put("ip", ip == null ? allocation.getIP() : ip);
		json.put("alias", alias == null ? allocation.getAlias() : alias);
		json.put("ports", portSet);
		return getRequestBody(json);
	}
}
