package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.abstracts.AbstractNodeAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import org.json.JSONObject;

public class CreateNodeImpl extends AbstractNodeAction {

	public CreateNodeImpl(PteroApplicationImpl impl) {
		super(impl);
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

		return PteroActionImpl.onExecute(() -> requestCreation(json));
	}

	private NodeImpl requestCreation(JSONObject json) {
		Route.CompiledRoute route = Route.Nodes.CREATE_NODE.compile().withJSONdata(json);

		JSONObject jsonObject = requester.request(route).toJSONObject();
		return new NodeImpl(jsonObject, impl);
	}
}
