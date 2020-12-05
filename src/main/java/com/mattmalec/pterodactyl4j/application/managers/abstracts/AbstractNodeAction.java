package com.mattmalec.pterodactyl4j.application.managers.abstracts;

import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;
import com.mattmalec.pterodactyl4j.requests.Requester;

public abstract class AbstractNodeAction implements NodeAction {
    protected String name;
    protected Location location;
    protected Boolean isPublic;
    protected String fqdn;
    protected Boolean isBehindProxy;
    protected String daemonBase;
    protected String memory;
    protected String memoryOverallocate;
    protected String diskSpace;
    protected String diskSpaceOverallocate;
    protected String daemonSFTPPort;
    protected String daemonListenPort;
    protected Boolean throttle;
    protected Boolean secure;

    protected PteroApplicationImpl impl;
    protected Requester requester;

    public AbstractNodeAction(PteroApplicationImpl impl) {
        this.impl = impl;
        requester = impl.getRequester();
    }

    @Override
    public NodeAction setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public NodeAction setLocation(Location location) {
        this.location = location;
        return this;
    }

    @Override
    public NodeAction setPublic(boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    @Override
    public NodeAction setFQDN(String fqdn) {
        this.fqdn = fqdn;
        return this;
    }

    @Override
    public NodeAction setBehindProxy(boolean isBehindProxy) {
        this.isBehindProxy = isBehindProxy;
        return this;
    }

    @Override
    public NodeAction setDaemonBase(String daemonBase) {
        this.daemonBase = daemonBase;
        return this;
    }

    @Override
    public NodeAction setMemory(String memory) {
        this.memory = memory;
        return this;
    }

    @Override
    public NodeAction setMemoryOverallocate(String memoryOverallocate) {
        this.memoryOverallocate = memoryOverallocate;
        return this;
    }

    @Override
    public NodeAction setDiskSpace(String diskSpace) {
        this.diskSpace = diskSpace;
        return this;
    }

    @Override
    public NodeAction setDiskSpaceOverallocate(String diskSpaceOverallocate) {
        this.diskSpaceOverallocate = diskSpaceOverallocate;
        return this;
    }

    @Override
    public NodeAction setDaemonSFTPPort(String port) {
        this.daemonSFTPPort = port;
        return this;
    }

    @Override
    public NodeAction setDaemonListenPort(String port) {
        this.daemonListenPort = port;
        return this;
    }

    @Override
    public NodeAction setThrottle(boolean throttle) {
        this.throttle = throttle;
        return this;
    }

    @Override
    public NodeAction setScheme(boolean secure) {
        this.secure = secure;
        return this;
    }
}
