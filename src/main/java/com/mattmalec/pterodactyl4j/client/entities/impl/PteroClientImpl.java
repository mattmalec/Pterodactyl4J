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

package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.ClientTypes;
import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.Account;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.client.entities.Utilization;
import com.mattmalec.pterodactyl4j.entities.P4J;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.PaginationAction;
import com.mattmalec.pterodactyl4j.requests.action.impl.PaginationResponseImpl;
import com.mattmalec.pterodactyl4j.utils.StreamUtils;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Stream;

public class PteroClientImpl implements PteroClient {

    private final P4J api;

    public PteroClientImpl(P4J api) {
        this.api = api;
    }

    public P4J getP4J() {
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
    
    public PaginationAction<ClientServer> retrieveServers() {
        return retrieveServers(ClientTypes.NONE);
    }
    @Override
    public PaginationAction<ClientServer> retrieveServers(ClientTypes type) {
        return PaginationResponseImpl.onPagination(api, Route.Client.LIST_SERVERS.compile(type.toString()),
                (object) -> new ClientServerImpl(object, this));
    }



    @Override
    public PteroAction<ClientServer> retrieveServerByIdentifier(String identifier) {
        return PteroActionImpl.onRequestExecute(api, Route.Client.GET_SERVER.compile(identifier),
                (response, request) -> new ClientServerImpl(response.getObject(), this));
    }

    @Override
    public PteroAction<List<ClientServer>> retrieveServersByName(String name, boolean caseSensitive) {
        return PteroActionImpl.onExecute(api, () -> {
            Stream<ClientServer> servers = retrieveServers().stream();

            if (caseSensitive) {
                servers = servers.filter(s -> s.getName().contains(name));
            } else {
                servers = servers.filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()));
            }

            return servers.collect(StreamUtils.toUnmodifiableList());
        });
    }
}
