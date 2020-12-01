package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;
import com.mattmalec.pterodactyl4j.application.managers.NodeManager;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;

public class NodeManagerImpl implements NodeManager {

    private final PteroApplicationImpl impl;

    public NodeManagerImpl(PteroApplicationImpl impl) {
        this.impl = impl;
    }

    @Override
    public NodeAction createNode() {
        return new CreateNodeImpl(impl, impl.getRequester());
    }

    @Override
    public NodeAction editNode(Node node) {
        return new EditNodeImpl(impl, node);
    }

    @Override
    public PteroAction<Void> deleteNode(Node node) {
        return PteroActionImpl.onExecute(() -> {
            Route.CompiledRoute route = Route.Nodes.DELETE_NODE.compile(node.getId());
            impl.getRequester().request(route);
            return null;
        });
    }
}
