package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;

public class ServerController {

	private ApplicationServer server;
	private PteroApplicationImpl impl;

	public ServerController(ApplicationServer server, PteroApplicationImpl impl) {
		this.server = server;
		this.impl = impl;
	}

	public PteroAction<Void> suspend() {
		return PteroActionImpl.onExecute(() ->
        {
				Route.CompiledRoute route = Route.Servers.SUSPEND_SERVER.compile(server.getId());
				impl.getRequester().request(route);
				return null;
        });
	}

	public PteroAction<Void> unsuspend() {
		return PteroActionImpl.onExecute(() ->
        {
				Route.CompiledRoute route = Route.Servers.UNSUSPEND_SERVER.compile(server.getId());
				impl.getRequester().request(route);
				return null;
        });
	}

	public PteroAction<Void> reinstall() {
		return PteroActionImpl.onExecute(() ->
        {
				Route.CompiledRoute route = Route.Servers.REINSTALL_SERVER.compile(server.getId());
				impl.getRequester().request(route);
				return null;
        });
	}

	public PteroAction<Void> delete(boolean withForce) {
		return PteroActionImpl.onExecute(() ->
        {
				if(withForce) {
					Route.CompiledRoute forceRoute = Route.Servers.FORCE_DELETE_SERVER.compile(server.getId());
					impl.getRequester().request(forceRoute);
				} else {
					Route.CompiledRoute safeRoute = Route.Servers.SAFE_DELETE_SERVER.compile(server.getId());
					impl.getRequester().request(safeRoute);
				}
				return null;
        });
	}
}
