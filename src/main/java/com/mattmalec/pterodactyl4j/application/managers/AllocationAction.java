package com.mattmalec.pterodactyl4j.application.managers;

public interface AllocationAction {

    AllocationAction setIP(String ip);
    AllocationAction setAlias(String alias);
    AllocationAction setPorts(String... ports);
    AllocationAction addPorts(String... ports);
    AllocationAction addPort(String port);
    AllocationAction removePort(String port);

}
