package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.*;
import com.mattmalec.pterodactyl4j.client.managers.*;
import com.mattmalec.pterodactyl4j.entities.FeatureLimit;
import com.mattmalec.pterodactyl4j.entities.Limit;
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
				return PteroActionImpl.onRequestExecute(impl.getPteroApi(),
						Route.Subusers.GET_SUBUSER.compile(getIdentifier(), uuid.toString()),
						(response, request) -> new ClientSubuserImpl(response.getObject()));
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
		return PteroActionImpl.onExecute(impl.getPteroApi(), () -> {
			List<Backup> backups = new ArrayList<>();
			JSONObject json = new PteroActionImpl<JSONObject>(impl.getPteroApi(),
					Route.Backups.LIST_BACKUPS.compile(getIdentifier(), "1"),
					(response, request) -> response.getObject()).execute();
			long pages = json.getJSONObject("meta").getJSONObject("pagination").getLong("total_pages");
			for (Object o : json.getJSONArray("data")) {
				JSONObject backup = new JSONObject(o.toString());
				backups.add(new BackupImpl(backup, this));
			}
			for (int i = 2; i <= pages; i++) {
				JSONObject nextJson = new PteroActionImpl<JSONObject>(impl.getPteroApi(),
						Route.Backups.LIST_BACKUPS.compile(getIdentifier(), Long.toUnsignedString(i)),
						(response, request) -> response.getObject()).execute();
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
		return PteroActionImpl.onRequestExecute(impl.getPteroApi(),
				Route.Backups.GET_BACKUP.compile(getIdentifier(), uuid.toString()),
				(response, request) -> new BackupImpl(response.getObject(), this));
	}

	@Override
	public BackupManager getBackupManager() {
		return new BackupManagerImpl(this, impl);
	}

	@Override
	public PteroAction<List<Schedule>> retrieveSchedules() {
		return PteroActionImpl.onRequestExecute(impl.getPteroApi(),
				Route.Schedules.LIST_SCHEDULES.compile(getIdentifier()), (response, request) -> {
					JSONObject json = response.getObject();
					List<Schedule> schedules = new ArrayList<>();
					for (Object o : json.getJSONArray("data")) {
						JSONObject schedule = new JSONObject(o.toString());
						schedules.add(new ScheduleImpl(schedule, this, impl));
					}
					return Collections.unmodifiableList(schedules);
				});
	}

	@Override
	public PteroAction<Schedule> retrieveSchedule(String id) {
		return PteroActionImpl.onRequestExecute(impl.getPteroApi(),
				Route.Schedules.GET_SCHEDULE.compile(getIdentifier(), id),
				(response, request) -> new ScheduleImpl(response.getObject(), this, impl));
	}

	@Override
	public ScheduleManager getScheduleManager() {
		return new ScheduleManagerImpl(this, impl);
	}

	@Override
	public FileManager getFileManager() {
		return new FileManagerImpl(this, impl);
	}

	@Override
	public String toString() {
		return json.toString(4);
	}
}
