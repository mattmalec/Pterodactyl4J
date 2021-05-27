package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;

public interface AllocationAction extends PteroAction<Void> {

    AllocationAction setIP(String ip);
    AllocationAction setAlias(String alias);
    AllocationAction setPorts(String... ports);
    AllocationAction addPorts(String... ports);
    AllocationAction addPort(String port);
    AllocationAction removePort(String port);
    AllocationAction removePorts(String... ports);

}
