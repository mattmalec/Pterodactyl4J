package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.AllocationAction;
import com.mattmalec.pterodactyl4j.application.managers.AllocationManager;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;

public class AllocationManagerImpl implements AllocationManager {

	private PteroApplicationImpl impl;
	private Node node;

	public AllocationManagerImpl(Node node, PteroApplicationImpl impl) {
		this.impl = impl;
		this.node = node;
	}

	public AllocationAction createAllocation() {
		return new CreateAllocationImpl(this.node, this.impl);
	}

	public AllocationAction editAllocation(Allocation allocation) {
		return new EditAllocationImpl(this.impl, this.node, allocation);
	}

	public PteroAction<Void> deleteAllocation(Allocation allocation) {
		return PteroActionImpl.onExecute(() ->
		{
			Route.CompiledRoute route = Route.Nodes.DELETE_ALLOCATION.compile(node.getId(), allocation.getId());
			Requester requester = impl.getRequester();
			requester.request(route);
			return null;
		});
	}

}
