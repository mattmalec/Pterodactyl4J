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
import com.mattmalec.pterodactyl4j.application.entities.ApplicationAllocation;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.AllocationAction;
import com.mattmalec.pterodactyl4j.application.managers.ApplicationAllocationManager;
import com.mattmalec.pterodactyl4j.requests.Route;

public class ApplicationAllocationManagerImpl implements ApplicationAllocationManager {

	private final PteroApplicationImpl impl;
	private Node node;

	public ApplicationAllocationManagerImpl(Node node, PteroApplicationImpl impl) {
		this.impl = impl;
		this.node = node;
	}

	public AllocationAction createAllocation() {
		return new CreateAllocationImpl(impl, node);
	}

	public AllocationAction editAllocation(ApplicationAllocation allocation) {
		return new EditAllocationImpl(impl, node, allocation);
	}

	public PteroAction<Void> deleteAllocation(ApplicationAllocation allocation) {
		return PteroActionImpl.onRequestExecute(impl.getP4J(), Route.Nodes.DELETE_ALLOCATION.compile(node.getId(), allocation.getId()));
	}

}
