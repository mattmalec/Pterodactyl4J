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
		return new PteroAction<Node>() {
			@Override
			public Node execute() {
				JSONObject json = new JSONObject();
				if(name == null)
					json.put("name", node.getName());
				else
					json.put("name", name);
				if(location == null)
					json.put("location_id", node.getLocation().getId());
				else
					json.put("location_id", location.getId());
				if(isPublic == null)
					json.put("public", node.isPublic() ? "1" : "0");
				else
					json.put("public", isPublic ? "1" : "0");
				if(fqdn == null)
					json.put("fqdn", node.getFQDN());
				else
					json.put("fqdn", fqdn);
				if(secure == null)
					json.put("scheme", node.getScheme());
				else
					json.put("scheme", secure ? "https" : "http");
				if(isBehindProxy == null)
					json.put("behind_proxy", node.isBehindProxy() ? "1" : "0");
				else
					json.put("behind_proxy", isBehindProxy ? "1" : "0");
				if(daemonBase == null)
					json.put("daemon_base", node.getDaemonBase());
				else
					json.put("daemon_base", daemonBase);
				if(memory == null)
					json.put("memory", node.getMemoryLong());
				else
					json.put("memory", Long.parseLong(memory));
				if(memoryOverallocate == null)
					json.put("memory_overallocate", node.getMemoryOverallocateLong());
				else
					json.put("memory_overallocate", Long.parseLong(memoryOverallocate));
				if(diskSpace == null)
					json.put("disk", node.getDiskLong());
				else
					json.put("disk", Long.parseLong(diskSpace));
				if(diskSpaceOverallocate == null)
					json.put("disk_overallocate", node.getDiskOverallocateLong());
				else
					json.put("disk_overallocate", Long.parseLong(diskSpaceOverallocate));
				if(daemonListenPort == null)
					json.put("daemon_listen", node.getDaemonListenPort());
				else
					json.put("daemon_listen", daemonListenPort);
				if(daemonSFTPPort == null)
					json.put("daemon_sftp", node.getDaemonSFTPPort());
				else
					json.put("daemon_sftp", daemonSFTPPort);
				if(throttle == null)
					json.put("throttle", new JSONObject().put("enabled", false));
				else
					json.put("throttle", new JSONObject().put("enabled", throttle));
				Route.CompiledRoute route = Route.Nodes.EDIT_NODE.compile(node.getId()).withJSONdata(json);
				JSONObject jsonObject = requester.request(route).toJSONObject();
				return new NodeImpl(jsonObject, impl);
			}
		};
	}
}
