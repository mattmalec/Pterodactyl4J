package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;
import com.mattmalec.pterodactyl4j.application.managers.NodeManager;

public class NodeManagerImpl implements NodeManager {

    private PteroApplicationImpl impl;

    public NodeManagerImpl(PteroApplicationImpl impl) {
        this.impl = impl;
    }

    @Override
    public NodeAction createNode() {
        return new CreateNodeImpl(impl, impl.getRequester());
    }

    @Override
    public NodeAction editNode(Node node) {
        return null;
    }

    @Override
    public PteroAction<Void> deleteNode(Node node) {
        return null;
    }
}
