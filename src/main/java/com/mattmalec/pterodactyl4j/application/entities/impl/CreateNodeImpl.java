package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import org.json.JSONObject;

public class CreateNodeImpl implements NodeAction {

	private Requester requester;

	private String name;
	private Location location;
	private boolean isPublic;
	private String fqdn;
	private boolean isBehindProxy;
	private String daemonBase;
	private String memory;
	private String memoryOverallocate;
	private String diskSpace;
	private String diskSpaceOverallocate;
	private String daemonSFTPPort;
	private String daemonListenPort;
	private boolean throttle;
	private boolean secure;

	private PteroApplicationImpl impl;

	CreateNodeImpl(PteroApplicationImpl impl, Requester requester) {
		this.requester = requester;
		this.impl = impl;
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
	public PteroAction<Node> build() {
		Checks.notBlank(this.name, "Name");
		Checks.notNull(this.location, "Location");
		Checks.notBlank(this.fqdn, "FQDN");
		Checks.notBlank(this.daemonBase, "Daemon Base");
		Checks.notNumeric(this.memory, "Memory");
		Checks.notNumeric(this.memoryOverallocate, "Memory Overallocate");
		Checks.notNumeric(this.diskSpace, "Disk Space");
		Checks.notNumeric(this.diskSpaceOverallocate, "Disk Space Overallocate");
		Checks.notNumeric(this.daemonSFTPPort, "Daemon SFTP Port");
		Checks.notNumeric(this.daemonListenPort, "Daemon Listen Port");
		JSONObject json = new JSONObject();
		json.put("name", this.name);
		json.put("location_id", this.location.getId());
		json.put("public", this.isPublic ? "1" : "0");
		json.put("fqdn", this.fqdn);
		json.put("scheme", this.secure ? "https" : "http");
		json.put("behind_proxy", this.isBehindProxy ? "1" : "0");
		json.put("daemon_base", this.daemonBase);
		json.put("memory", Long.parseLong(this.memory));
		json.put("memory_overallocate", Long.parseLong(this.memoryOverallocate));
		json.put("disk", Long.parseLong(this.diskSpace));
		json.put("disk_overallocate", Long.parseLong(this.diskSpaceOverallocate));
		json.put("daemon_listen", this.daemonListenPort);
		json.put("daemon_sftp", this.daemonSFTPPort);
		json.put("throttle", new JSONObject().put("enabled", this.throttle));

		return PteroActionImpl.onExecute(() -> {
			Route.CompiledRoute route = Route.Nodes.CREATE_NODE.compile().withJSONdata(json);

			JSONObject jsonObject = requester.request(route).toJSONObject();
			return new NodeImpl(jsonObject, impl);
		});
	}
}
