package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Relationed;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LocationImpl implements Location {

	private JSONObject json;
	private JSONObject relationships;
	private PteroApplicationImpl impl;

	public LocationImpl(JSONObject json, PteroApplicationImpl impl) {
		this.json = json.getJSONObject("attributes");
		this.relationships = json.getJSONObject("attributes").optJSONObject("relationships");
		this.impl = impl;
	}

	@Override
	public String getShortCode() {
		return json.getString("short");
	}

	@Override
	public String getDescription() {
		return json.getString("long");
	}

	@Override
	public Relationed<List<Node>> getNodes() {
		LocationImpl location = this;
		return new Relationed<List<Node>>() {
			@Override
			public PteroAction<List<Node>> retrieve() {
				return impl.retrieveNodesByLocation(location);
			}

			@Override
			public Optional<List<Node>> get() {
				if(!json.has("relationships")) return Optional.empty();
				List<Node> nodes = new ArrayList<>();
				JSONObject json = relationships.getJSONObject("nodes");
				for(Object o : json.getJSONArray("data")) {
					JSONObject node = new JSONObject(o.toString());
					nodes.add(new NodeImpl(node, impl));
				}
				return Optional.of(Collections.unmodifiableList(nodes));
			}
		};
	}

	@Override
	public Relationed<List<ApplicationServer>> getServers() {
		LocationImpl location = this;
		return new Relationed<List<ApplicationServer>>() {
			@Override
			public PteroAction<List<ApplicationServer>> retrieve() {
				return impl.retrieveServersByLocation(location);
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
	public LocationAction edit() {
		return new EditLocationImpl(impl, this);
	}

	@Override
	public PteroAction<Void> delete() {
		return PteroActionImpl.onExecute(() -> {
			Route.CompiledRoute route = Route.Locations.DELETE_LOCATION.compile(getId());
			impl.getRequester().request(route);
			return null;
		});
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
