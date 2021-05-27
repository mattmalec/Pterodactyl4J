package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.NodeActionImpl;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class CreateNodeImpl extends NodeActionImpl {

	public CreateNodeImpl(PteroApplicationImpl impl) {
		super(impl, Route.Nodes.CREATE_NODE.compile());
	}

	@Override
	protected RequestBody finalizeData() {
		Checks.notBlank(name, "Name");
		Checks.notNull(location, "Location");
		Checks.notBlank(fqdn, "FQDN");
		Checks.notBlank(daemonBase, "Daemon Base");
		Checks.notNumeric(memory, "Memory");
		Checks.notNumeric(memoryOverallocate, "Memory Overallocate");
		Checks.notNumeric(diskSpace, "Disk Space");
		Checks.notNumeric(diskSpaceOverallocate, "Disk Space Overallocate");
		Checks.notNumeric(daemonSFTPPort, "Daemon SFTP Port");
		Checks.notNumeric(daemonListenPort, "Daemon Listen Port");
		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("location_id", location.getId());
		json.put("public", isPublic ? "1" : "0");
		json.put("fqdn", fqdn);
		json.put("scheme", secure ? "https" : "http");
		json.put("behind_proxy", isBehindProxy ? "1" : "0");
		json.put("daemon_base", daemonBase);
		json.put("memory", Long.parseUnsignedLong(memory));
		json.put("memory_overallocate", Long.parseUnsignedLong(memoryOverallocate));
		json.put("disk", Long.parseUnsignedLong(diskSpace));
		json.put("disk_overallocate", Long.parseUnsignedLong(diskSpaceOverallocate));
		json.put("daemon_listen", daemonListenPort);
		json.put("daemon_sftp", daemonSFTPPort);
		json.put("throttle", new JSONObject().put("enabled", throttle));
		return getRequestBody(json);
	}
}
