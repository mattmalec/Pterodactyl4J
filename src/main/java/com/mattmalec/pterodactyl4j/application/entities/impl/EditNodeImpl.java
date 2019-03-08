package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

public class EditNodeImpl implements NodeAction {

	private Requester requester;
	private Node node;

	private String name;
	private Location location;
	private Boolean isPublic;
	private String fqdn;
	private Boolean isBehindProxy;
	private String daemonBase;
	private String memory;
	private String memoryOverallocate;
	private String diskSpace;
	private String diskSpaceOverallocate;
	private String daemonSFTPPort;
	private String daemonListenPort;
	private Boolean throttle;
	private Boolean secure;

	private PteroApplicationImpl impl;

	EditNodeImpl(PteroApplicationImpl impl, Node node) {
		this.requester = impl.getRequester();
		this.impl = impl;
		this.node = node;
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
		JSONObject json = new JSONObject();
		if(this.name == null)
			json.put("name", this.node.getName());
		else
			json.put("name", this.name);
		if(this.location == null)
			json.put("location_id", this.node.retrieveLocation().execute().getId());
		else
			json.put("location_id", this.location.getId());
		if(this.isPublic == null)
			json.put("public", this.node.isPublic() ? "1" : "0");
		else
			json.put("public", this.isPublic ? "1" : "0");
		if(this.fqdn == null)
			json.put("fqdn", this.node.getFQDN());
		else
			json.put("fqdn", this.fqdn);
		if(this.secure == null)
			json.put("scheme", this.node.getScheme());
		else
			json.put("scheme", this.secure ? "https" : "http");
		if(this.isBehindProxy == null)
			json.put("behind_proxy", this.node.isBehindProxy() ? "1" : "0");
		else
			json.put("behind_proxy", this.isBehindProxy ? "1" : "0");
		if(this.daemonBase == null)
			json.put("daemon_base", this.node.getDaemonBase());
		else
			json.put("daemon_base", this.daemonBase);
		if(this.memory == null)
			json.put("memory", this.node.getMemoryLong());
		else
			json.put("memory", Long.parseLong(this.memory));
		if(this.memoryOverallocate == null)
			json.put("memory_overallocate", this.node.getMemoryOverallocateLong());
		else
			json.put("memory_overallocate", Long.parseLong(this.memoryOverallocate));
		if(this.diskSpace == null)
			json.put("disk", this.node.getDiskLong());
		else
			json.put("disk", Long.parseLong(this.diskSpace));
		if(this.diskSpaceOverallocate == null)
			json.put("disk_overallocate", this.node.getDiskOverallocateLong());
		else
			json.put("disk_overallocate", Long.parseLong(this.diskSpaceOverallocate));
		if(this.daemonListenPort == null)
			json.put("daemon_listen", this.node.getDaemonListenPort());
		else
			json.put("daemon_listen", this.daemonListenPort);
		if(this.daemonSFTPPort == null)
			json.put("daemon_sftp", this.node.getDaemonSFTPPort());
		else
			json.put("daemon_sftp", this.daemonSFTPPort);
		if(this.throttle == null)
			json.put("throttle", new JSONObject().put("enabled", false));
		else
			json.put("throttle", new JSONObject().put("enabled", this.throttle));

		return new PteroAction<Node>() {
			Route.CompiledRoute route = Route.Nodes.CREATE_NODE.compile().withJSONdata(json);
			@Override
			public Node execute() {
				JSONObject jsonObject = requester.request(route).toJSONObject();
				return new NodeImpl(jsonObject, impl);
			}
		};
	}
}
