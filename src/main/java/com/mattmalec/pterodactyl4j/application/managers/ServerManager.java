package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg;
import com.mattmalec.pterodactyl4j.application.entities.impl.ApplicationServerImpl;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.util.Set;

public class ServerManager {

	private ApplicationServer server;
	private PteroApplicationImpl impl;

	public ServerManager(ApplicationServer server, PteroApplicationImpl impl) {
		this.server = server;
		this.impl = impl;
	}

	public PteroAction<ApplicationServer> setName(String name) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("name", name)
						.put("description", server.getDescription())
						.put("user", server.getOwner().get().orElseGet(() -> server.getOwner().retrieve().execute()).getId());
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
        });
	}

	public PteroAction<ApplicationServer> setOwner(ApplicationUser user) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("name", server.getName())
						.put("description", server.getDescription())
						.put("user", user.getId());
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
        });
	}

	public PteroAction<ApplicationServer> setDescription(String description) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("name", server.getName())
						.put("description", description)
						.put("user", server.getOwner().get().orElseGet(() -> server.getOwner().retrieve().execute()).getId());
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
        });
	}

	public PteroAction<ApplicationServer> setAllocation(Allocation allocation) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk())
						.put("threads", server.getLimits().getThreads());
				JSONObject allocations = new JSONObject()
						.put("default", allocation.getId());
				obj.put("allocation", allocations);
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations())
						.put("backups", server.getFeatureLimits().getBackups());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
        });
	}

	public PteroAction<ApplicationServer> setMemory(long amount, DataType dataType) {
		return PteroActionImpl.onExecute(() ->
        {
				long trueAmount;
				switch(dataType) {
					case MB: trueAmount = amount;
					break;
					default: trueAmount = amount * dataType.getMbValue();
				}
				JSONObject obj = new JSONObject()
						.put("memory", trueAmount)
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk())
						.put("threads", server.getLimits().getThreads());
				JSONObject allocations = new JSONObject()
						.put("default", server.getDefaultAllocation().get().orElseGet(() ->server.getDefaultAllocation().retrieve().execute()).getId());
				obj.put("allocation", allocations);
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations())
						.put("backups", server.getFeatureLimits().getBackups());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
        });
	}

	public PteroAction<ApplicationServer> setSwap(long amount, DataType dataType) {
		return PteroActionImpl.onExecute(() ->
        {
				long trueAmount;
				switch(dataType) {
					case MB: trueAmount = amount;
						break;
					default: trueAmount = amount * dataType.getMbValue();
				}
				JSONObject obj = new JSONObject()
						.put("memory", server.getLimits().getMemory())
						.put("swap", trueAmount)
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk())
						.put("threads", server.getLimits().getThreads());
				JSONObject allocations = new JSONObject()
						.put("default", server.getDefaultAllocation().get().orElseGet(() ->server.getDefaultAllocation().retrieve().execute()).getId());
				obj.put("allocation", allocations);
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations())
						.put("backups", server.getFeatureLimits().getBackups());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
        });
	}

	public PteroAction<ApplicationServer> setIO(long amount) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", amount)
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk())
						.put("threads", server.getLimits().getThreads());
				JSONObject allocations = new JSONObject()
						.put("default", server.getDefaultAllocation().get().orElseGet(() ->server.getDefaultAllocation().retrieve().execute()).getId());
				obj.put("allocation", allocations);
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations())
						.put("backups", server.getFeatureLimits().getBackups());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
        });
	}

	public PteroAction<ApplicationServer> setCPU(long amount) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", amount)
						.put("disk", server.getLimits().getDisk())
						.put("threads", server.getLimits().getThreads());
				JSONObject allocations = new JSONObject()
						.put("default", server.getDefaultAllocation().get().orElseGet(() ->server.getDefaultAllocation().retrieve().execute()).getId());
				obj.put("allocation", allocations);
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations())
						.put("backups", server.getFeatureLimits().getBackups());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
        });
	}

	public PteroAction<ApplicationServer> setThreads(String cores) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk())
						.put("threads", cores);
				JSONObject allocations = new JSONObject()
						.put("default", server.getDefaultAllocation().get().orElseGet(() ->server.getDefaultAllocation().retrieve().execute()).getId());
				obj.put("allocation", allocations);
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations())
						.put("backups", server.getFeatureLimits().getBackups());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
        });
	}

	public PteroAction<ApplicationServer> setDisk(long amount, DataType dataType) {
		return PteroActionImpl.onExecute(() ->
        {
				long trueAmount;
				switch(dataType) {
					case MB: trueAmount = amount;
						break;
					default: trueAmount = amount * dataType.getMbValue();
				}
				JSONObject obj = new JSONObject()
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", trueAmount)
						.put("threads", server.getLimits().getThreads());
				JSONObject allocations = new JSONObject()
						.put("default", server.getDefaultAllocation().get().orElseGet(() ->server.getDefaultAllocation().retrieve().execute()).getId());
				obj.put("allocation", allocations);
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations())
						.put("backups", server.getFeatureLimits().getBackups());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
        });
	}

	public PteroAction<ApplicationServer> setAllowedDatabases(int amount) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk())
						.put("threads", server.getLimits().getThreads());
				JSONObject allocations = new JSONObject()
						.put("default", server.getDefaultAllocation().get().orElseGet(() ->server.getDefaultAllocation().retrieve().execute()).getId());
				obj.put("allocation", allocations);
				JSONObject featureLimits = new JSONObject()
						.put("databases", amount)
						.put("allocations", server.getFeatureLimits().getAllocations())
						.put("backups", server.getFeatureLimits().getBackups());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
        });
	}

	public PteroAction<ApplicationServer> setAllowedAllocations(int amount) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk())
						.put("threads", server.getLimits().getThreads());
				JSONObject allocations = new JSONObject()
						.put("default", server.getDefaultAllocation().get().orElseGet(() ->server.getDefaultAllocation().retrieve().execute()).getId());
				obj.put("allocation", allocations);
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", amount)
						.put("backups", server.getFeatureLimits().getBackups());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
        });
	}

	public PteroAction<Void> setStartupCommand(String command) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("startup", command)
						.put("environment", server.getContainer().getEnvironment().keySet())
						.put("egg", server.getEgg().retrieve().execute().getId())
						.put("image", server.getContainer().getImage());
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()).withJSONdata(obj);
				impl.getRequester().request(route);
				return null;
        });
	}

	public PteroAction<Void> setEnvironment(Set<String> environment) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("startup", server.getContainer().getStartupCommand())
						.put("environment", environment)
						.put("egg", server.getEgg().retrieve().execute().getId())
						.put("image", server.getContainer().getImage());
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()).withJSONdata(obj);
				impl.getRequester().request(route);
				return null;
        });
	}

	public PteroAction<Void> setEgg(ApplicationEgg egg) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("startup", server.getContainer().getStartupCommand())
						.put("environment",  server.getContainer().getEnvironment().keySet())
						.put("egg", egg.getId())
						.put("image", server.getContainer().getImage());
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()).withJSONdata(obj);
				impl.getRequester().request(route);
				return null;
        });
	}

	public PteroAction<Void> setImage(String dockerImage) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("startup", server.getContainer().getStartupCommand())
						.put("environment",  server.getContainer().getEnvironment().keySet())
						.put("egg", server.getEgg().retrieve().execute().getId())
						.put("image", dockerImage);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()).withJSONdata(obj);
				impl.getRequester().request(route);
				return null;
        });
	}

	public PteroAction<Void> setSkipScripts(boolean skipScripts) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("startup", server.getContainer().getStartupCommand())
						.put("environment",  server.getContainer().getEnvironment().keySet())
						.put("egg", server.getEgg().retrieve().execute().getId())
						.put("image", server.getContainer().getImage())
						.put("skip_scripts", skipScripts);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()).withJSONdata(obj);
				impl.getRequester().request(route);
				return null;
        });
	}
}
