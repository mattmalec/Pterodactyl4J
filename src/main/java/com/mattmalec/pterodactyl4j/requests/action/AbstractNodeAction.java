/*
 *    Copyright 2021-2024 Matt Malec, and the Pterodactyl4J contributors
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

import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.entities.impl.NodeImpl;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;

public abstract class AbstractNodeAction extends PteroActionImpl<Node> implements NodeAction {

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
	protected Boolean maintenanceMode;

	public AbstractNodeAction(PteroApplicationImpl impl, Route.CompiledRoute route) {
		super(impl.getP4J(), route, (response, request) -> new NodeImpl(response.getObject(), impl));
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

	@Override
	public NodeAction setMaintenanceMode(boolean maintenanceMode) {
		this.maintenanceMode = maintenanceMode;
		return this;
	}
}
