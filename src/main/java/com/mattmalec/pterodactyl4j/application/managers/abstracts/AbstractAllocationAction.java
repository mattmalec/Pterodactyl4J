package com.mattmalec.pterodactyl4j.application.managers.abstracts;

import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.application.managers.AllocationAction;
import com.mattmalec.pterodactyl4j.requests.Requester;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractAllocationAction implements AllocationAction {

    protected String ip;
    protected String alias;
    protected final Set<String> portSet = new HashSet<>();
    protected final Node node;
    protected final Requester requester;

    public AbstractAllocationAction(Node node, PteroApplicationImpl app) {
        this.node = node;
        requester = app.getRequester();
    }

    @Override
    public AllocationAction setIP(String ip) {
        this.ip = ip;
        return this;
    }

    @Override
    public AllocationAction setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    @Override
    public AllocationAction setPorts(String... ports) {
        portSet.clear();
        portSet.addAll(Arrays.asList(ports));
        return this;
    }

    @Override
    public AllocationAction addPorts(String... ports) {
        portSet.addAll(Arrays.asList(ports));
        return this;
    }

    @Override
    public AllocationAction addPort(String port) {
        portSet.add(port);
        return this;
    }

    @Override
    public AllocationAction removePort(String port) {
        portSet.remove(port);
        return this;
    }

    @Override
    public AllocationAction removePorts(String... ports) {
        portSet.removeAll(Arrays.asList(ports));
        return this;
    }
}
