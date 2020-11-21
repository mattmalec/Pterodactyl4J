package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.Account;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.client.entities.Utilization;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PteroClientImpl implements PteroClient {

    private Requester requester;

    public PteroClientImpl(Requester requester) {
        this.requester = requester;
    }

    public Requester getRequester() {
        return requester;
    }

    @Override
    public PteroAction<Account> retrieveAccount() {
        PteroClientImpl impl = this;
        return new PteroAction<Account>() {
            @Override
            public Account execute() {
                Route.CompiledRoute route = Route.Accounts.GET_ACCOUNT.compile();
                JSONObject json = requester.request(route).toJSONObject();
                return new AccountImpl(json, impl);
            }
        };
    }

    @Override
    public PteroAction<Void> setPower(ClientServer server, PowerAction powerAction) {
        return new PteroAction<Void>() {
            @Override
            public Void execute() {
                JSONObject obj = new JSONObject().put("signal", powerAction.name().toLowerCase());
                Route.CompiledRoute route = Route.Client.SET_POWER.compile(server.getIdentifier()).withJSONdata(obj);
                requester.request(route);
                return null;
            }
        };
    }

    @Override
    public PteroAction<Void> sendCommand(ClientServer server, String command) {
        return new PteroAction<Void>() {
            @Override
            public Void execute() {
                JSONObject obj = new JSONObject().put("command", command);
                Route.CompiledRoute route = Route.Client.SEND_COMMAND.compile(server.getIdentifier()).withJSONdata(obj);
                requester.request(route);
                return null;
            }
        };
    }

    @Override
    public PteroAction<Utilization> retrieveUtilization(ClientServer server) {
        return new PteroAction<Utilization>() {
            @Override
            public Utilization execute() {
                Route.CompiledRoute route = Route.Client.GET_UTILIZATION.compile(server.getIdentifier());
                JSONObject json = requester.request(route).toJSONObject();
                return new UtilizationImpl(json);
            }
        };
    }

    @Override
    public PteroAction<List<ClientServer>> retrieveServers() {
        PteroClientImpl impl = this;
        return new PteroAction<List<ClientServer>>() {
            @Override
            public List<ClientServer> execute() {
                Route.CompiledRoute route = Route.Client.LIST_SERVERS.compile("1");
                JSONObject json = requester.request(route).toJSONObject();
                long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
                List<ClientServer> servers = new ArrayList<>();
                for (Object o : json.getJSONArray("data")) {
                    JSONObject server = new JSONObject(o.toString());
                    servers.add(new ClientServerImpl(server, impl));
                }
                for (int i = 1; i < pages; i++) {
                    Route.CompiledRoute nextRoute = Route.Client.LIST_SERVERS.compile(Long.toUnsignedString(pages));
                    JSONObject nextJson = requester.request(nextRoute).toJSONObject();
                    for (Object o : nextJson.getJSONArray("data")) {
                        JSONObject server = new JSONObject(o.toString());
                        servers.add(new ClientServerImpl(server, impl));
                    }
                }
                return Collections.unmodifiableList(servers);
            }
        };
    }

    @Override
    public PteroAction<ClientServer> retrieveServerByIdentifier(String identifier) {
        PteroClientImpl impl = this;
        return new PteroAction<ClientServer>() {
            Route.CompiledRoute route = Route.Client.GET_SERVER.compile(identifier);
            @Override
            public ClientServer execute() {
                JSONObject json = requester.request(route).toJSONObject();
                return new ClientServerImpl(json, impl);
            }
        };
    }

    @Override
    public PteroAction<List<ClientServer>> retrieveServersByName(String name, boolean caseSensetive) {
        return new PteroAction<List<ClientServer>>() {
            @Override
            public List<ClientServer> execute() {
                List<ClientServer> servers = retrieveServers().execute();
                List<ClientServer> newServers = new ArrayList<>();
                for (ClientServer s : servers) {
                    if (caseSensetive) {
                        if (s.getName().contains(name))
                            newServers.add(s);
                    } else {
                        if (s.getName().toLowerCase().contains(name.toLowerCase()))
                            newServers.add(s);
                    }
                }
                return Collections.unmodifiableList(newServers);
            }
        };
    }
}
