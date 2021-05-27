package com.mattmalec.pterodactyl4j.requests.action;

import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.application.managers.AllocationAction;
import com.mattmalec.pterodactyl4j.requests.Route;

import java.util.Arrays;
import java.util.Set;

public class AllocationActionImpl extends PteroActionImpl<Void> implements AllocationAction {

    protected String ip;
    protected String alias;
    protected Set<String> portSet;

    public AllocationActionImpl(PteroApplicationImpl impl, Route.CompiledRoute route) {
        super(impl.getPteroApi(), route);
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
