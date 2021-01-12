package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.*;
import com.mattmalec.pterodactyl4j.client.managers.*;
import com.mattmalec.pterodactyl4j.entities.FeatureLimit;
import com.mattmalec.pterodactyl4j.entities.Limit;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.entities.impl.FeatureLimitImpl;
import com.mattmalec.pterodactyl4j.entities.impl.LimitImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Relationed;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class ClientServerImpl implements ClientServer {

	private JSONObject json;
	private JSONObject relationships;
	private PteroClientImpl impl;

	public ClientServerImpl(JSONObject json, PteroClientImpl impl) {
		this.json = json.getJSONObject("attributes");
		this.relationships = json.getJSONObject("attributes").getJSONObject("relationships");
		this.impl = impl;
	}

	@Override
	public boolean isServerOwner() {
		return json.getBoolean("server_owner");
	}

	@Override
	public UUID getUUID() {
		return UUID.fromString(json.getString("uuid"));
	}

	@Override
	public long getInternalIdLong() {
		return json.getLong("internal_id");
	}

	@Override
	public String getIdentifier() {
		return json.getString("identifier");
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
	public Limit getLimits() {
		return new LimitImpl(json.getJSONObject("limits"));
	}

	@Override
	public FeatureLimit getFeatureLimits() {
		return new FeatureLimitImpl(json.getJSONObject("feature_limits"));
	}

	@Override
	public SFTP getSFTPDetails() {
		return new SFTPImpl(json.getJSONObject("sftp_details"));
	}

	@Override
	public String getInvocation() {
		return json.getString("invocation");
	}

	@Override
	public Set<String> getEggFeatures() {
		JSONArray features = json.getJSONArray("egg_features");
		HashSet<String> eggFeatures = new HashSet<>();
		features.forEach(o -> eggFeatures.add(o.toString()));
		return Collections.unmodifiableSet(eggFeatures);
	}

	@Override
	public String getNode() {
		return json.getString("node");
	}

	@Override
	public boolean isSuspended() {
		return json.getBoolean("is_suspended");
	}

	@Override
	public boolean isInstalling() {
		return json.getBoolean("is_installing");
	}

	@Override
	public WebSocketBuilder getWebSocketBuilder() {
		return new WebSocketBuilder(impl, this);
	}


	@Override
	public List<ClientSubuser> getSubusers() {
		List<ClientSubuser> subusers = new ArrayList<>();
		JSONObject json = relationships.getJSONObject("subusers");
		for(Object o : json.getJSONArray("data")) {
			JSONObject subuser = new JSONObject(o.toString());
			subusers.add(new ClientSubuserImpl(subuser));
		}
		return Collections.unmodifiableList(subusers);
	}

	@Override
	public Relationed<ClientSubuser> getSubuser(UUID uuid) {
		return new Relationed<ClientSubuser>() {
			@Override
			public PteroAction<ClientSubuser> retrieve() {
				return PteroActionImpl.onExecute(() -> {
					Route.CompiledRoute route = Route.Subusers.GET_SUBUSER.compile(getIdentifier(), uuid.toString());
					JSONObject json = impl.getRequester().request(route).toJSONObject();
					return new ClientSubuserImpl(json);
				});
			}

			@Override
			public Optional<ClientSubuser> get() {
				if(getSubusers().isEmpty()) return Optional.empty();
				return getSubusers().stream().filter(u -> u.getUUID().equals(uuid)).findFirst();
			}
		};
	}

	@Override
	public ClientEgg getEgg() {
		return new ClientEggImpl(relationships.getJSONObject("egg"), relationships.getJSONObject("variables"));
	}

	@Override
	public SubuserManager getSubuserManager() {
		return new SubuserManagerImpl(this, impl);
	}

	@Override
	public ClientServerManager getManager() {
		return new ClientServerManager(this, impl);
	}

	@Override
	public PteroAction<List<Backup>> retrieveBackups() {
		return PteroActionImpl.onExecute(() -> {
			Route.CompiledRoute route = Route.Backups.LIST_BACKUPS.compile(getIdentifier(), "1");
			JSONObject json = impl.getRequester().request(route).toJSONObject();
			long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
			List<Backup> backups = new ArrayList<>();
			for (Object o : json.getJSONArray("data")) {
				JSONObject backup = new JSONObject(o.toString());
				backups.add(new BackupImpl(backup, this));
			}
			for (int i = 1; i < pages; i++) {
				Route.CompiledRoute nextRoute = Route.Backups.LIST_BACKUPS.compile(getIdentifier(), Long.toUnsignedString(i));
				JSONObject nextJson = impl.getRequester().request(nextRoute).toJSONObject();
				for (Object o : nextJson.getJSONArray("data")) {
					JSONObject backup = new JSONObject(o.toString());
					backups.add(new BackupImpl(backup, this));
				}
			}
			return Collections.unmodifiableList(backups);
		});
	}

	@Override
	public PteroAction<Backup> retrieveBackup(UUID uuid) {
		return PteroActionImpl.onExecute(() -> {
			Route.CompiledRoute route = Route.Backups.GET_BACKUP.compile(getIdentifier(), uuid.toString());
			JSONObject json = impl.getRequester().request(route).toJSONObject();
			return new BackupImpl(json, this);
		});
	}

	@Override
	public BackupManager getBackupManager() {
		return new BackupManagerImpl(this, impl);
	}

	@Override
	public PteroAction<List<Schedule>> retrieveSchedules() {
		return PteroActionImpl.onExecute(() -> {
			Route.CompiledRoute route = Route.Schedules.LIST_SCHEDULES.compile(getIdentifier(), "1");
			JSONObject json = impl.getRequester().request(route).toJSONObject();
			long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
			List<Schedule> schedules = new ArrayList<>();
			for (Object o : json.getJSONArray("data")) {
				JSONObject schedule = new JSONObject(o.toString());
				schedules.add(new ScheduleImpl(schedule, this, impl));
			}
			for (int i = 1; i < pages; i++) {
				Route.CompiledRoute nextRoute = Route.Schedules.LIST_SCHEDULES.compile(getIdentifier(), Long.toUnsignedString(i));
				JSONObject nextJson = impl.getRequester().request(nextRoute).toJSONObject();
				for (Object o : nextJson.getJSONArray("data")) {
					JSONObject schedule = new JSONObject(o.toString());
					schedules.add(new ScheduleImpl(schedule, this, impl));
				}
			}
			return Collections.unmodifiableList(schedules);
		});
	}

	@Override
	public PteroAction<Schedule> retrieveSchedule(long id) {
		return PteroActionImpl.onExecute(() -> {
			Route.CompiledRoute route = Route.Schedules.GET_SCHEDULE.compile(getIdentifier(), Long.toUnsignedString(id));
			JSONObject json = impl.getRequester().request(route).toJSONObject();
			return new ScheduleImpl(json, this, impl);
		});
	}

	@Override
	public ScheduleManager getScheduleManager() {
		return new ScheduleManagerImpl(this, impl);
	}


	@Override
	public String toString() {
		return json.toString(4);
	}
}
