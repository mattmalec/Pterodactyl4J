package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.utils.Relationed;
import org.json.JSONObject;

import java.util.Optional;

public class AllocationImpl implements Allocation {

	private final JSONObject json;
	private final JSONObject relationships;
	private final PteroApplicationImpl impl;

	public AllocationImpl(JSONObject json, PteroApplicationImpl impl) {
		this.json = json.getJSONObject("attributes");
		this.relationships = json.getJSONObject("attributes").optJSONObject("relationships");
		this.impl = impl;
	}

	@Override
	public String getIP() {
		return json.getString("ip");
	}

	@Override
	public String getAlias() {
		return json.getString("alias");
	}

	@Override
	public String getPort() {
		return Long.toUnsignedString(json.getLong("port"));
	}

	@Override
	public boolean isAssigned() {
		return json.getBoolean("assigned");
	}

	@Override
	public String getNotes() {
		return json.getString("notes");
	}

	@Override
	public long getIdLong() {
		return json.getLong("id");
	}

	@Override
	public String getFullAddress() {
		return getIP() + ":" + getPort();
	}

	@Override
	public Optional<Relationed<ApplicationServer>> getServer() {
		if(!isAssigned() || !json.has("relationships")) return Optional.empty();
		return Optional.of(new Relationed<ApplicationServer>() {
			@Override
			public PteroAction<ApplicationServer> retrieve() {
				return impl.retrieveServerById(get().get().getIdLong());
			}

			@Override
			public Optional<ApplicationServer> get() {
				return Optional.of(new ApplicationServerImpl(impl, relationships.getJSONObject("server")));
			}
		});
	}

	@Override
	public Optional<Relationed<Node>> getNode() {
		if (!json.has("relationships")) return Optional.empty();
		return Optional.of(new Relationed<Node>() {
			@Override
			public PteroAction<Node> retrieve() {
				return impl.retrieveNodeById(get().get().getIdLong());
			}

			@Override
			public Optional<Node> get() {
				return Optional.of(new NodeImpl(relationships.getJSONObject("node"), impl));
			}
		});
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
