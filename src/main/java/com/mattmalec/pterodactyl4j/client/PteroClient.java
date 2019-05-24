package com.mattmalec.pterodactyl4j.client;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Server;
import com.mattmalec.pterodactyl4j.client.entities.Utilization;

public interface PteroClient {

    PteroAction<Void> setPower(Server server, PowerAction powerAction);
    PteroAction<Void> sendCommand(Server server, String command);
    PteroAction<Utilization> retrieveUtilization(Server server);

}
