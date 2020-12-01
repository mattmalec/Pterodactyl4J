package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationImpl implements Location {

	private final JSONObject json;
	private final PteroApplicationImpl impl;

	public LocationImpl(JSONObject json, PteroApplicationImpl impl) {
		this.json = json.getJSONObject("attributes");
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
	public PteroAction<List<Node>> retrieveNodes() {
		return new PteroAction<List<Node>>() {
			@Override
			public List<Node> execute() {
				List<Node> nodes = impl.retrieveNodes().execute();
				List<Node> newNodes = new ArrayList<>();
				for(Node n : nodes) {
					if(n.getLocation().getId().equals(getId())) {
						newNodes.add(n);
					}
				}
				return Collections.unmodifiableList(newNodes);
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
		return new EditLocationImpl(this, impl);
	}

	@Override
	public PteroAction<Void> delete() {
		return new PteroAction<Void>() {
			Route.CompiledRoute route = Route.Locations.DELETE_LOCATION.compile(getId());
			@Override
			public Void execute() {
				impl.getRequester().request(route);
				return null;
			}
		};
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
