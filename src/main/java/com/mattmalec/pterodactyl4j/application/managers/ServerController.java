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

package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.requests.Route;

public class ServerController {

	private final ApplicationServer server;
	private final PteroApplicationImpl impl;

	public ServerController(ApplicationServer server, PteroApplicationImpl impl) {
		this.server = server;
		this.impl = impl;
	}

	public PteroAction<Void> suspend() {
		return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Servers.SUSPEND_SERVER.compile(server.getId()));
	}

	public PteroAction<Void> unsuspend() {
		return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Servers.UNSUSPEND_SERVER.compile(server.getId()));
	}

	public PteroAction<Void> reinstall() {
		return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Servers.REINSTALL_SERVER.compile(server.getId()));
	}

	public PteroAction<Void> delete(boolean withForce) {
		return PteroActionImpl.onRequestExecute(impl.getP4J(), withForce ?
				Route.Servers.FORCE_DELETE_SERVER.compile(server.getId()) :
				Route.Servers.SAFE_DELETE_SERVER.compile(server.getId()));
	}
}
