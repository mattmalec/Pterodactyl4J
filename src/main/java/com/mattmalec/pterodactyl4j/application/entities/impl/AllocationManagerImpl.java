package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.AllocationAction;
import com.mattmalec.pterodactyl4j.application.managers.AllocationManager;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;

public class AllocationManagerImpl implements AllocationManager {

	private PteroApplicationImpl impl;

	public AllocationManagerImpl(PteroApplicationImpl impl) {
		this.impl = impl;
	}

	public AllocationAction createAllocation(Node node) {
		return new CreateAllocationImpl(this.impl, node);
	}

	public AllocationAction editAllocation(Allocation allocation) {
		return null;
	}

	public PteroAction<Void> deleteAllocation(Node node, Allocation allocation) {
		return new PteroAction<Void>() {
			@Override
			public Void execute() {
				Route.CompiledRoute route = Route.Nodes.DELETE_ALLOCATION.compile(node.getId(), allocation.getId());
				Requester requester = impl.getRequester();
				requester.request(route);
				return null;
			}
		};
	}

}
