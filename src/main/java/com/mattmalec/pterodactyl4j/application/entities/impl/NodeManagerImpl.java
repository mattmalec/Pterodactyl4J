package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;
import com.mattmalec.pterodactyl4j.application.managers.NodeManager;
import com.mattmalec.pterodactyl4j.requests.Route;

public class NodeManagerImpl implements NodeManager {

    private PteroApplicationImpl impl;

    public NodeManagerImpl(PteroApplicationImpl impl) {
        this.impl = impl;
    }

    @Override
    public NodeAction createNode() {
        return new CreateNodeImpl(impl);
    }

    @Override
    public NodeAction editNode(Node node) {
        return new EditNodeImpl(impl, node);
    }

    @Override
    public PteroAction<Void> deleteNode(Node node) {
        return PteroActionImpl.onRequestExecute(impl.getPteroApi(), Route.Nodes.DELETE_NODE.compile(node.getId()));
    }
}
