package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Server;
import com.mattmalec.pterodactyl4j.client.PteroClient;
import com.mattmalec.pterodactyl4j.client.entities.Utilization;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

public class PteroClientImpl implements PteroClient {

    private Requester requester;

    public PteroClientImpl(Requester requester) {
        this.requester = requester;
    }

    @Override
    public PteroAction<Void> setPower(Server server, PowerAction powerAction) {
        return new PteroAction<Void>() {
            @Override
            public Void execute() {
                JSONObject obj = new JSONObject().put("signal", powerAction.name().toLowerCase());
                Route.CompiledRoute route = Route.Client.SET_POWER.compile(server.getId()).withJSONdata(obj);
                requester.request(route);
                return null;
            }
        };
    }

    @Override
    public PteroAction<Void> sendCommand(Server server, String command) {
        return new PteroAction<Void>() {
            @Override
            public Void execute() {
                JSONObject obj = new JSONObject().put("command", command);
                Route.CompiledRoute route = Route.Client.SEND_COMMAND.compile(server.getId()).withJSONdata(obj);
                requester.request(route);
                return null;
            }
        };
    }

    @Override
    public PteroAction<Utilization> retrieveUtilization(Server server) {
        return new PteroAction<Utilization>() {
            @Override
            public Utilization execute() {
                Route.CompiledRoute route = Route.Client.GET_UTILIZATION.compile(server.getId());
                JSONObject json = requester.request(route).toJSONObject();
                return new UtilizationImpl(json);
            }
        };
    }
}
