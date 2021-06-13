package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.AllocationManager;
import com.mattmalec.pterodactyl4j.application.managers.NodeAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Relationed;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NodeImpl implements Node {

	private JSONObject json;
	private JSONObject relationships;
	private PteroApplicationImpl impl;

	public NodeImpl(JSONObject json, PteroApplicationImpl impl) {
		this.json = json.getJSONObject("attributes");
		this.relationships = json.getJSONObject("attributes").optJSONObject("relationships");
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
	public Relationed<Location> getLocation() {
		return new Relationed<Location>() {
			@Override
			public PteroAction<Location> retrieve() {
				return impl.retrieveLocationById(json.getLong("location_id"));
			}

			@Override
			public Optional<Location> get() {
				if(!json.has("relationships")) return Optional.empty();
				return Optional.of(new LocationImpl(relationships.getJSONObject("location"), impl));
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
	public long getMemoryLong() {
		return json.getLong("memory");
	}

	@Override
	public long getMemoryOverallocateLong() {
		return json.getLong("memory_overallocate");
	}

	@Override
	public long getDiskLong() {
		return json.getLong("disk");
	}

	@Override
	public long getDiskOverallocateLong() {
		return json.getLong("disk_overallocate");
	}

	@Override
	public long getUploadLimitLong() {
		return json.getLong("upload_size");
	}

	@Override
	public long getAllocatedMemoryLong() {
		return json.getJSONObject("allocated_resources").getLong("memory");
	}

	@Override
	public long getAllocatedDiskLong() {
		return json.getJSONObject("allocated_resources").getLong("disk");
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
	public Relationed<List<ApplicationServer>> getServers() {
		NodeImpl node = this;
		return new Relationed<List<ApplicationServer>>() {
			@Override
			public PteroAction<List<ApplicationServer>> retrieve() {
				return impl.retrieveServersByNode(node);
			}

			@Override
			public Optional<List<ApplicationServer>> get() {
				if(!json.has("relationships")) return Optional.empty();
				List<ApplicationServer> servers = new ArrayList<>();
				JSONObject json = relationships.getJSONObject("servers");
				for(Object o : json.getJSONArray("data")) {
					JSONObject server = new JSONObject(o.toString());
					servers.add(new ApplicationServerImpl(impl, server));
				}
				return Optional.of(Collections.unmodifiableList(servers));
			}
		};
	}

	@Override
	public Relationed<List<Allocation>> getAllocations() {
		NodeImpl node = this;
		return new Relationed<List<Allocation>>() {
			@Override
			public PteroAction<List<Allocation>> retrieve() {
				return impl.retrieveAllocationsByNode(node);
			}

			@Override
			public Optional<List<Allocation>> get() {
				if(!json.has("relationships")) return Optional.empty();
				List<Allocation> allocations = new ArrayList<>();
				JSONObject json = relationships.getJSONObject("allocations");
				for(Object o : json.getJSONArray("data")) {
					JSONObject allocation = new JSONObject(o.toString());
					allocations.add(new AllocationImpl(allocation, impl));
				}
				return Optional.of(Collections.unmodifiableList(allocations));
			}
		};
	}

	@Override
	public Relationed<List<Allocation>> getAllocationsByPort(int port) {
		return new Relationed<List<Allocation>>() {
			@Override
			public PteroAction<List<Allocation>> retrieve() {
				return PteroActionImpl.onExecute(impl.getPteroApi(), () -> getAllocations().retrieve().execute().stream().filter(a -> a.getPortInt() == port).collect(Collectors.toList()));
			}

			@Override
			public Optional<List<Allocation>> get() {
				if(!json.has("relationships")) return Optional.empty();
				List<Allocation> allocations = getAllocations().get().get();
				return Optional.of(Collections.unmodifiableList(allocations.stream().filter(a -> a.getPortInt() == port).collect(Collectors.toList())));
			}
		};
	}

	@Override
	public PteroAction<Configuration> retrieveConfiguration() {
		return PteroActionImpl.onRequestExecute(impl.getPteroApi(), Route.Nodes.GET_CONFIGURATION.compile(getId()),
				(response, request) -> new NodeConfigurationImpl(response.getObject()));
	}

	@Override
	public long getIdLong() {
		return json.getLong("id");
	}

	@Override
	public OffsetDateTime getCreationDate() {
		return OffsetDateTime.parse(json.optString("created_at"));
	}

	@Override
	public OffsetDateTime getUpdatedDate() {
		return OffsetDateTime.parse(json.optString("updated_at"));
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
		return PteroActionImpl.onRequestExecute(impl.getPteroApi(), Route.Nodes.DELETE_NODE.compile(getId()));
	}
}
