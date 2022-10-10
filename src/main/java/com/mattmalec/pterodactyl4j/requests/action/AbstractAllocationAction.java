/*
 *    Copyright 2021-2022 Matt Malec, and the Pterodactyl4J contributors
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

package com.mattmalec.pterodactyl4j.requests.action;

import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.application.managers.AllocationAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractAllocationAction extends PteroActionImpl<Void> implements AllocationAction {

	protected String ip;
	protected String alias;
	protected Set<String> portSet;

	public AbstractAllocationAction(PteroApplicationImpl impl, Route.CompiledRoute route) {
		super(impl.getP4J(), route);
		this.portSet = new HashSet<>();
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
		this.portSet = new HashSet<>(Arrays.asList(ports));
		return this;
	}
}
