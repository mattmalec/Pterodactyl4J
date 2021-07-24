package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.PteroAction;

import java.util.List;

public interface PteroClient {

    PteroAction<Account> retrieveAccount();

    @Deprecated
    PteroAction<Void> setPower(ClientServer server, PowerAction powerAction);

    @Deprecated
    PteroAction<Void> sendCommand(ClientServer server, String command);

    @Deprecated
    PteroAction<Utilization> retrieveUtilization(ClientServer server);

    PteroAction<List<ClientServer>> retrieveServers();
    PteroAction<ClientServer> retrieveServerByIdentifier(String identifier);
    PteroAction<List<ClientServer>> retrieveServersByName(String name, boolean caseSensetive);

}
