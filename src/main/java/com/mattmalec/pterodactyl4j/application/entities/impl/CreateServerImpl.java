package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.*;
import com.mattmalec.pterodactyl4j.application.managers.ServerAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.exceptions.IllegalActionException;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateServerImpl implements ServerAction {

	private String name;
	private String description;
	private ApplicationUser owner;
	private ApplicationEgg egg;
	private String dockerImage;
	private String startupCommand;
	private long memory;
	private long swap;
	private long disk;
	private long cpu = 0L;
	private long io = 500L;
	private String threads;
	private long databases = 0L;
	private long allocations = 0L;
	private long backups = 0L;
	private Map<String, String> environment;
	private Set<Location> locations;
	private Set<Integer> portRange;
	private boolean useDedicatedIP;
	private boolean startOnCompletion;
	private boolean skipScripts;
	private Allocation defaultAllocation;
	private Collection<Allocation> additionalAllocations;

	private PteroApplicationImpl impl;

	public CreateServerImpl(PteroApplicationImpl impl) {
		this.impl = impl;
		this.environment = new HashMap<>();
	}

	@Override
	public ServerAction setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public ServerAction setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public ServerAction setOwner(ApplicationUser owner) {
		this.owner = owner;
		return this;
	}

	@Override
	public ServerAction setEgg(ApplicationEgg egg) {
		this.egg = egg;
		return this;
	}

	@Override
	public ServerAction setDockerImage(String dockerImage) {
		this.dockerImage = dockerImage;
		return this;
	}

	@Override
	public ServerAction setStartupCommand(String command) {
		this.startupCommand = command;
		return this;
	}

	@Override
	public ServerAction setMemory(long amount, DataType dataType) {
		long trueAmount;
		switch(dataType) {
			case MB: trueAmount = amount;
				break;
			default: trueAmount = amount * dataType.getMbValue();
		}
		this.memory = trueAmount;
		return this;
	}

	@Override
	public ServerAction setSwap(long amount, DataType dataType) {
		long trueAmount;
		switch(dataType) {
			case MB: trueAmount = amount;
				break;
			default: trueAmount = amount * dataType.getMbValue();
		}
		this.swap = trueAmount;
		return this;
	}

	@Override
	public ServerAction setDisk(long amount, DataType dataType) {
		long trueAmount;
		switch(dataType) {
			case MB: trueAmount = amount;
				break;
			default: trueAmount = amount * dataType.getMbValue();
		}
		this.disk = trueAmount;
		return this;
	}

	@Override
	public ServerAction setIO(long amount) {
		this.io = amount;
		return this;
	}

	@Override
	public ServerAction setThreads(String cores) {
		this.threads = cores;
		return this;
	}

	@Override
	public ServerAction setCPU(long amount) {
		this.cpu = amount;
		return this;
	}

	@Override
	public ServerAction setDatabases(long amount) {
		this.databases = amount;
		return this;
	}

	@Override
	public ServerAction setAllocations(long amount) {
		this.allocations = amount;
		return this;
	}

	@Override
	public ServerAction setBackups(long amount) {
		this.backups = amount;
		return this;
	}

	@Override
	public ServerAction setEnvironment(Map<String, String> environment) {
		this.environment = environment;
		return this;
	}

	@Override
	public ServerAction setLocations(Set<Location> locations) {
		this.locations = locations;
		return this;
	}

	@Override
	public ServerAction setDedicatedIP(boolean dedicatedIP) {
		this.useDedicatedIP = dedicatedIP;
		return this;
	}

	@Override
	public ServerAction setPortRange(Set<Integer> ports) {
		this.portRange = ports;
		return this;
	}

	@Override
	public ServerAction startOnCompletion(boolean start) {
		this.startOnCompletion = start;
		return this;
	}

	@Override
	public ServerAction skipScripts(boolean skip) {
		this.skipScripts = skip;
		return this;
	}

	@Override
	public ServerAction setAllocations(Allocation defaultAllocation, Collection<Allocation> additionalAllocations) {
		this.defaultAllocation = defaultAllocation;
		this.additionalAllocations = additionalAllocations;
		return this;
	}

	@Override
	public PteroAction<ApplicationServer> build() {
		return PteroActionImpl.onExecute(() -> {
			if(memory < 4) {
				throw new IllegalActionException("The minimum memory limit is 4 MB.");
			}
			Checks.notNull(owner, "Owner");
			Checks.notNull(egg, "Egg and Nest");
			Nest nest = egg.getNest().get().orElseGet(() -> egg.getNest().retrieve().execute());
			egg.getDefaultVariableMap().orElseGet(() ->
					impl.retrieveEggById(nest, egg.getId()).execute().getDefaultVariableMap().get()).forEach((k, v) -> environment.putIfAbsent(k, v));
			JSONObject featureLimits = new JSONObject()
					.put("databases", databases)
					.put("allocations", allocations == 0 && additionalAllocations != null ? additionalAllocations.size() + 1 : allocations)
					.put("backups", backups);
			JSONObject limits = new JSONObject()
					.put("memory", memory)
					.put("swap", swap)
					.put("disk", disk)
					.put("io", io)
					.put("cpu", cpu)
					.put("threads", threads);
			JSONObject allocation = new JSONObject()
					.put("default", defaultAllocation != null ? defaultAllocation.getIdLong() : null)
					.put("additional", (additionalAllocations != null && additionalAllocations.size() != 0) ? additionalAllocations.stream().map(Allocation::getIdLong).collect(Collectors.toList()) : null);
			JSONObject deploy = new JSONObject()
					.put("locations", locations != null ? locations.stream().map(ISnowflake::getIdLong).collect(Collectors.toList()) : null)
					.put("dedicated_ip", useDedicatedIP)
					.put("port_range", portRange != null ? portRange.stream().map(Integer::toUnsignedString).collect(Collectors.toList()) : null);
			JSONObject obj = new JSONObject()
					.put("name", name)
					.put("description", description)
					.put("user", owner.getId())
					.put("nest", nest.getId())
					.put("egg", egg.getId())
					.put("docker_image", dockerImage != null ? dockerImage : egg.getDockerImage())
					.put("startup", startupCommand != null ? startupCommand : egg.getStartupCommand())
					.put("limits", limits)
					.put("feature_limits", featureLimits)
					.put("environment", environment)
					.put("deploy", (locations != null || portRange != null) ? deploy : null)
					.put("allocation", allocation)
					.put("start_on_completion", startOnCompletion)
					.put("skip_scripts", skipScripts);
			Route.CompiledRoute route = Route.Servers.CREATE_SERVER.compile().withJSONdata(obj);
			JSONObject json = impl.getRequester().request(route).toJSONObject();
			return new ApplicationServerImpl(impl, json);
		});
	}
}
