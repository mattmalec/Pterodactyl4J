package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.*;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.application.entities.impl.ServerImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.util.Set;

public class ServerManager {

	private Server server;
	private PteroApplicationImpl impl;

	public ServerManager(Server server, PteroApplicationImpl impl) {
		this.server = server;
		this.impl = impl;
	}

	public PteroAction<Server> setName(String name) {
		return new PteroAction<Server>() {
			@Override
			public Server execute() {
				JSONObject obj = new JSONObject()
						.put("name", name)
						.put("description", server.getDescription())
						.put("user", server.retrieveOwner().execute().getId());
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ServerImpl(impl, json);
			}
		};
	}

	public PteroAction<Server> setOwner(User user) {
		return new PteroAction<Server>() {
			@Override
			public Server execute() {
				JSONObject obj = new JSONObject()
						.put("name", server.getName())
						.put("description", server.getDescription())
						.put("user", user.getId());
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ServerImpl(impl, json);
			}
		};
	}

	public PteroAction<Server> setDescription(String description) {
		return new PteroAction<Server>() {
			@Override
			public Server execute() {
				JSONObject obj = new JSONObject()
						.put("name", server.getName())
						.put("description", description)
						.put("user", server.retrieveOwner().execute().getId());
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ServerImpl(impl, json);
			}
		};
	}

	public PteroAction<Server> setAllocation(Allocation allocation) {
		return new PteroAction<Server>() {
			@Override
			public Server execute() {
				JSONObject obj = new JSONObject()
						.put("allocation", allocation.getId())
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk());
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ServerImpl(impl, json);
			}
		};
	}

	public PteroAction<Server> setMemory(long amount, DataType dataType) {
		return new PteroAction<Server>() {
			@Override
			public Server execute() {
				long trueAmount;
				switch(dataType) {
					case MB: trueAmount = amount;
					break;
					default: trueAmount = amount * dataType.getMbValue();
				}
				JSONObject obj = new JSONObject()
						.put("allocation", server.retrieveAllocation().execute().getId())
						.put("memory", trueAmount)
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk());
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ServerImpl(impl, json);
			}
		};
	}

	public PteroAction<Server> setSwap(long amount, DataType dataType) {
		return new PteroAction<Server>() {
			@Override
			public Server execute() {
				long trueAmount;
				switch(dataType) {
					case MB: trueAmount = amount;
						break;
					default: trueAmount = amount * dataType.getMbValue();
				}
				JSONObject obj = new JSONObject()
						.put("allocation", server.retrieveAllocation().execute().getId())
						.put("memory", server.getLimits().getMemory())
						.put("swap", trueAmount)
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk());
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ServerImpl(impl, json);
			}
		};
	}

	public PteroAction<Server> setIO(long amount) {
		return new PteroAction<Server>() {
			@Override
			public Server execute() {
				JSONObject obj = new JSONObject()
						.put("allocation", server.retrieveAllocation().execute().getId())
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", amount)
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk());
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ServerImpl(impl, json);
			}
		};
	}

	public PteroAction<Server> setCPU(long amount) {
		return new PteroAction<Server>() {
			@Override
			public Server execute() {
				JSONObject obj = new JSONObject()
						.put("allocation", server.retrieveAllocation().execute().getId())
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", amount)
						.put("disk", server.getLimits().getDisk());
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ServerImpl(impl, json);
			}
		};
	}

	public PteroAction<Server> setDisk(long amount, DataType dataType) {
		return new PteroAction<Server>() {
			@Override
			public Server execute() {
				long trueAmount;
				switch(dataType) {
					case MB: trueAmount = amount;
						break;
					default: trueAmount = amount * dataType.getMbValue();
				}
				JSONObject obj = new JSONObject()
						.put("allocation", server.retrieveAllocation().execute().getId())
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", trueAmount);
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", server.getFeatureLimits().getAllocations());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ServerImpl(impl, json);
			}
		};
	}

	public PteroAction<Server> setAllowedDatabases(int amount) {
		return new PteroAction<Server>() {
			@Override
			public Server execute() {
				JSONObject obj = new JSONObject()
						.put("allocation", server.retrieveAllocation().execute().getId())
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk());
				JSONObject featureLimits = new JSONObject()
						.put("databases", amount)
						.put("allocations", server.getFeatureLimits().getAllocations());
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ServerImpl(impl, json);
			}
		};
	}

	public PteroAction<Server> setAllowedAllocations(int amount) {
		return new PteroAction<Server>() {
			@Override
			public Server execute() {
				JSONObject obj = new JSONObject()
						.put("allocation", server.retrieveAllocation().execute().getId())
						.put("memory", server.getLimits().getMemory())
						.put("swap", server.getLimits().getSwap())
						.put("io", server.getLimits().getIO())
						.put("cpu", server.getLimits().getCPU())
						.put("disk", server.getLimits().getDisk());
				JSONObject featureLimits = new JSONObject()
						.put("databases", server.getFeatureLimits().getDatabases())
						.put("allocations", amount);
				obj.put("feature_limits", featureLimits);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()).withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ServerImpl(impl, json);
			}
		};
	}

	public PteroAction<Void> setStartupCommand(String command) {
		return new PteroAction<Void>() {
			@Override
			public Void execute() {
				JSONObject obj = new JSONObject()
						.put("startup", command)
						.put("environment", server.getContainer().getEnvironment().keySet())
						.put("egg", server.retrieveEgg().execute().getId())
						.put("image", server.getContainer().getImage());
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()).withJSONdata(obj);
				impl.getRequester().request(route);
				return null;
			}
		};
	}

	public PteroAction<Void> setEnvironment(Set<String> environment) {
		return new PteroAction<Void>() {
			@Override
			public Void execute() {
				JSONObject obj = new JSONObject()
						.put("startup", server.getContainer().getStartupCommand())
						.put("environment", environment)
						.put("egg", server.retrieveEgg().execute().getId())
						.put("image", server.getContainer().getImage());
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()).withJSONdata(obj);
				impl.getRequester().request(route);
				return null;
			}
		};
	}

	public PteroAction<Void> setEgg(Egg egg) {
		return new PteroAction<Void>() {
			@Override
			public Void execute() {
				JSONObject obj = new JSONObject()
						.put("startup", server.getContainer().getStartupCommand())
						.put("environment",  server.getContainer().getEnvironment().keySet())
						.put("egg", egg.getId())
						.put("image", server.getContainer().getImage());
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()).withJSONdata(obj);
				impl.getRequester().request(route);
				return null;
			}
		};
	}

	public PteroAction<Void> setImage(String dockerImage) {
		return new PteroAction<Void>() {
			@Override
			public Void execute() {
				JSONObject obj = new JSONObject()
						.put("startup", server.getContainer().getStartupCommand())
						.put("environment",  server.getContainer().getEnvironment().keySet())
						.put("egg", server.retrieveEgg().execute().getId())
						.put("image", dockerImage);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()).withJSONdata(obj);
				impl.getRequester().request(route);
				return null;
			}
		};
	}

	public PteroAction<Void> setSkipScripts(boolean skipScripts) {
		return new PteroAction<Void>() {
			@Override
			public Void execute() {
				JSONObject obj = new JSONObject()
						.put("startup", server.getContainer().getStartupCommand())
						.put("environment",  server.getContainer().getEnvironment().keySet())
						.put("egg", server.retrieveEgg().execute().getId())
						.put("image", server.getContainer().getImage())
						.put("skip_scripts", skipScripts);
				Route.CompiledRoute route = Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()).withJSONdata(obj);
				impl.getRequester().request(route);
				return null;
			}
		};
	}
}
