package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.Account;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.client.entities.Utilization;
import com.mattmalec.pterodactyl4j.entities.PteroAPI;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PteroClientImpl implements PteroClient {

    private PteroAPI api;

    public PteroClientImpl(PteroAPI api) {
        this.api = api;
    }

    public PteroAPI getPteroApi() {
        return api;
    }

    @Override
    public PteroAction<Account> retrieveAccount() {
        return PteroActionImpl.onRequestExecute(api, Route.Accounts.GET_ACCOUNT.compile(), (
                response, request) -> new AccountImpl(response.getObject(), this));
    }

    @Override
    public PteroAction<Void> setPower(ClientServer server, PowerAction powerAction) {
        JSONObject obj = new JSONObject().put("signal", powerAction.name().toLowerCase());
        return PteroActionImpl.onRequestExecute(api,
                Route.Client.SET_POWER.compile(server.getIdentifier()), PteroActionImpl.getRequestBody(obj));
    }

    @Override
    public PteroAction<Void> sendCommand(ClientServer server, String command) {
        JSONObject obj = new JSONObject().put("command", command);
        return PteroActionImpl.onRequestExecute(api,
                Route.Client.SEND_COMMAND.compile(server.getIdentifier()), PteroActionImpl.getRequestBody(obj));
    }

    @Override
    public PteroAction<Utilization> retrieveUtilization(ClientServer server) {
        return PteroActionImpl.onRequestExecute(api,Route.Client.GET_UTILIZATION.compile(server.getIdentifier()),
                (response, request) -> new UtilizationImpl(response.getObject()));
    }

    @Override
    public PteroAction<List<ClientServer>> retrieveServers() {
        return PteroActionImpl.onExecute(() -> {
            List<ClientServer> servers = new ArrayList<>();
            JSONObject json = new PteroActionImpl<JSONObject>(api, Route.Client.LIST_SERVERS.compile("1"),
                    (response, request) -> response.getObject()).execute();
            long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
            for (Object o : json.getJSONArray("data")) {
                JSONObject server = new JSONObject(o.toString());
                servers.add(new ClientServerImpl(server, this));
            }
            for (int i = 2; i <= pages; i++) {
                JSONObject nextJson = new PteroActionImpl<JSONObject>(api, Route.Client.LIST_SERVERS.compile(Long.toUnsignedString(i)),
                        (response, request) -> response.getObject()).execute();
                for (Object o : nextJson.getJSONArray("data")) {
                    JSONObject server = new JSONObject(o.toString());
                    servers.add(new ClientServerImpl(server, this));
                }
            }
            return Collections.unmodifiableList(servers);
        });
    }

    @Override
    public PteroAction<ClientServer> retrieveServerByIdentifier(String identifier) {
        return PteroActionImpl.onRequestExecute(api, Route.Client.GET_SERVER.compile(identifier),
                (response, request) -> new ClientServerImpl(response.getObject(), this));
    }

    @Override
    public PteroAction<List<ClientServer>> retrieveServersByName(String name, boolean caseSensetive) {
        return PteroActionImpl.onExecute(() ->
        {
            List<ClientServer> servers = retrieveServers().execute();
            Stream<ClientServer> newServers = servers.stream();

            if(caseSensetive) {
                newServers = newServers.filter(s -> s.getName().contains(name));
            } else {
                newServers = newServers.filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()));
            }

            return Collections.unmodifiableList(newServers.collect(Collectors.toList()));
        });
    }
}
