/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
