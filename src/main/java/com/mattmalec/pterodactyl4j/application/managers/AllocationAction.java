package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;

public interface AllocationAction {

    AllocationAction setIP(String ip);
    AllocationAction setAlias(String alias);
    AllocationAction setPorts(String... ports);
    AllocationAction addPorts(String... ports);
    AllocationAction addPort(String port);
    AllocationAction removePort(String port);
    AllocationAction removePorts(String... ports);
    PteroAction<Void> build();

}
