/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
