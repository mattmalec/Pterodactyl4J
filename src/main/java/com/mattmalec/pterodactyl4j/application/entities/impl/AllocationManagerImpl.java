package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.AllocationAction;
import com.mattmalec.pterodactyl4j.application.managers.AllocationManager;
import com.mattmalec.pterodactyl4j.requests.Route;

public class AllocationManagerImpl implements AllocationManager {

	private final PteroApplicationImpl impl;
	private Node node;

	public AllocationManagerImpl(Node node, PteroApplicationImpl impl) {
		this.impl = impl;
		this.node = node;
	}

	public AllocationAction createAllocation() {
		return new CreateAllocationImpl(this.impl, this.node);
	}

	public AllocationAction editAllocation(Allocation allocation) {
		return new EditAllocationImpl(this.impl, this.node, allocation);
	}

	public PteroAction<Void> deleteAllocation(Allocation allocation) {
		return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Nodes.DELETE_ALLOCATION.compile(node.getId(), allocation.getId()));
	}

}
