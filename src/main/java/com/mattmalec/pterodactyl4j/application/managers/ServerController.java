package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
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
		return new PteroAction<Void>() {
			@Override
			public Void execute() {
				Route.CompiledRoute route = Route.Servers.SUSPEND_SERVER.compile(server.getId());
				impl.getRequester().request(route);
				return null;
			}
		};
	}

	public PteroAction<Void> unsuspend() {
		return new PteroAction<Void>() {
			@Override
			public Void execute() {
				Route.CompiledRoute route = Route.Servers.UNSUSPEND_SERVER.compile(server.getId());
				impl.getRequester().request(route);
				return null;
			}
		};
	}

	public PteroAction<Void> reinstall() {
		return new PteroAction<Void>() {
			@Override
			public Void execute() {
				Route.CompiledRoute route = Route.Servers.REINSTALL_SERVER.compile(server.getId());
				impl.getRequester().request(route);
				return null;
			}
		};
	}

	public PteroAction<Void> rebuild() {
		return new PteroAction<Void>() {
			@Override
			public Void execute() {
				Route.CompiledRoute route = Route.Servers.REBUILD_SERVER.compile(server.getId());
				impl.getRequester().request(route);
				return null;
			}
		};
	}

	public PteroAction<Void> delete(boolean withForce) {
		return new PteroAction<Void>() {
			@Override
			public Void execute() {
				Route.CompiledRoute safeRoute = Route.Servers.SAFE_DELETE_SERVER.compile(server.getId());
				Route.CompiledRoute forceRoute = Route.Servers.FORCE_DELETE_SERVER.compile(server.getId());
				if(withForce) {
					impl.getRequester().request(forceRoute);
				} else {
					impl.getRequester().request(safeRoute);
				}

				return null;
			}
		};
	}
}
