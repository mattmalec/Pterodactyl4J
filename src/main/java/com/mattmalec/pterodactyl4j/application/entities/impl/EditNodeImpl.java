package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.NodeActionImpl;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.util.function.Consumer;

public class EditNodeImpl extends NodeActionImpl {

	private Node node;

	EditNodeImpl(PteroApplicationImpl impl, Node node) {
		super(impl, Route.Nodes.EDIT_NODE.compile(node.getId()));
		this.node = node;
	}

	@Override
	protected RequestBody finalizeData() {
		JSONObject json = new JSONObject();
		json.put("name", name == null ? node.getName() : name);
		json.put("location_id", location == null ? node.getLocation().retrieve().execute().getId() : location.getId());
		json.put("public", isPublic == null ? (node.isPublic() ? "1" : "0") : (isPublic ? "1" : "0"));
		json.put("fqdn", fqdn == null ? node.getFQDN() : fqdn);
		json.put("scheme", secure == null ? node.getScheme() : (secure ? "https" : "http"));
		json.put("behind_proxy", isBehindProxy == null ? (node.isBehindProxy() ? "1" : "0") : (isBehindProxy ? "1" : "0"));
		json.put("daemon_base", daemonBase == null ? node.getDaemonBase() : daemonBase);
		json.put("memory", memory == null ? node.getMemoryLong() : Long.parseUnsignedLong(memory));
		json.put("memory_overallocate", memoryOverallocate == null ? node.getMemoryOverallocateLong() : Long.parseUnsignedLong(memoryOverallocate));
		json.put("disk", diskSpace == null ? node.getDiskLong() : Long.parseUnsignedLong(diskSpace));
		json.put("disk_overallocate", diskSpaceOverallocate == null ? node.getDiskOverallocateLong() : Long.parseUnsignedLong(diskSpaceOverallocate));
		json.put("daemon_listen", daemonListenPort == null ? node.getDaemonListenPort() : daemonListenPort);
		json.put("daemon_sftp", daemonSFTPPort == null ? node.getDaemonSFTPPort() : daemonSFTPPort);
		json.put("throttle", throttle == null ? new JSONObject().put("enabled", false) : new JSONObject().put("enabled", throttle));
		return getRequestBody(json);
	}
}
