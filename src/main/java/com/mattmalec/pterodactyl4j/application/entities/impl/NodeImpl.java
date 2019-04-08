package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.AllocationManager;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NodeImpl implements Node {

	private JSONObject json;
	private PteroApplicationImpl impl;

	public NodeImpl(JSONObject json, PteroApplicationImpl impl) {
		this.json = json.getJSONObject("attributes");
		this.impl = impl;
	}

	@Override
	public boolean isPublic() {
		return json.getBoolean("public");
	}

	@Override
	public String getName() {
		return json.getString("name");
	}

	@Override
	public String getDescription() {
		return json.getString("description");
	}

	@Override
	public AllocationManager getAllocationManager() {
		return new AllocationManagerImpl(this, impl);
	}

	@Override
	public PteroAction<Location> retrieveLocation() {
		String locationId = Long.toUnsignedString(json.getLong("location_id"));
		return new PteroAction<Location>() {
			@Override
			public Location execute() {
				List<Location> locations = impl.retrieveLocations().execute();
				for(Location l : locations) {
					if(l.getId().equals(locationId)) {
						return l;
					}
				}
				return null;
			}
		};
	}

	@Override
	public String getFQDN() {
		return json.getString("fqdn");
	}

	@Override
	public String getScheme() {
		return json.getString("scheme");
	}

	@Override
	public boolean isBehindProxy() {
		return json.getBoolean("behind_proxy");
	}

	@Override
	public boolean hasMaintanceMode() {
		return json.getBoolean("maintenance_mode");
	}

	@Override
	public String getMemory() {
		return Long.toUnsignedString(json.getLong("memory"));
	}

	@Override
	public String getMemoryOverallocate() {
		return Long.toUnsignedString(json.getLong("memory_overallocate"));
	}

	@Override
	public String getDisk() {
		return Long.toUnsignedString(json.getLong("disk"));
	}

	@Override
	public String getDiskOverallocate() {
		return Long.toUnsignedString(json.getLong("disk_overallocate"));
	}

	@Override
	public String getUploadLimit() {
		return Long.toUnsignedString(json.getLong("upload_size"));
	}

	@Override
	public String getDaemonListenPort() {
		return Long.toUnsignedString(json.getLong("daemon_listen"));
	}

	@Override
	public String getDaemonSFTPPort() {
		return Long.toUnsignedString(json.getLong("daemon_sftp"));
	}

	@Override
	public String getDaemonBase() {
		return json.getString("daemon_base");
	}

	@Override
	public long getIdLong() {
		return json.getLong("id");
	}

	@Override
	public OffsetDateTime getCreationDate() {
		return LocalDateTime.parse(json.optString("created_at"), DateTimeFormatter.ISO_LOCAL_DATE_TIME).atOffset(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
	}

	@Override
	public OffsetDateTime getUpdatedDate() {
		return LocalDateTime.parse(json.optString("updated_at"), DateTimeFormatter.ISO_LOCAL_DATE_TIME).atOffset(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
	}

	@Override
	public String toString() {
		return json.toString(4);
	}

	@Override
	public NodeAction edit() {
		return new EditNodeImpl(impl, this);
	}

	@Override
	public PteroAction<Void> delete() {
		return new PteroAction<Void>() {
			Route.CompiledRoute route = Route.Nodes.DELETE_NODE.compile(getId());
			@Override
			public Void execute() {
				impl.getRequester().request(route);
				return null;
			}
		};
	}
}
