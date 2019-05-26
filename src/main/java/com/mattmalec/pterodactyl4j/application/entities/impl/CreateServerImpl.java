package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.Egg;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.User;
import com.mattmalec.pterodactyl4j.application.managers.ServerAction;
import com.mattmalec.pterodactyl4j.exceptions.IllegalActionException;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CreateServerImpl implements ServerAction {

	private String name;
	private String description;
	private User owner;
	private Egg egg;
	private String dockerImage;
	private String startupCommand;
	private long memory;
	private long swap;
	private long disk;
	private long cpu = 0L;
	private long io = 500L;
	private long databases = 0L;
	private long allocations = 0L;
	private Map<String, String> environment;
	private Set<Location> locations;
	private Set<String> portRange;
	private boolean useDedicatedIP;
	private boolean startOnCompletion;

	private PteroApplicationImpl impl;

	public CreateServerImpl(PteroApplicationImpl impl) {
		this.impl = impl;
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
	public ServerAction setOwner(User owner) {
		this.owner = owner;
		return this;
	}

	@Override
	public ServerAction setEgg(Egg egg) {
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
	public ServerAction setPortRange(Set<String> ports) {
		this.portRange = ports;
		return this;
	}

	@Override
	public ServerAction startOnCompletion(boolean start) {
		this.startOnCompletion = start;
		return this;
	}

	@Override
	public PteroAction<ApplicationServer> build() {
		return new PteroAction<ApplicationServer>() {
			@Override
			public ApplicationServer execute() {
				if(memory < 4) {
					throw new IllegalActionException("The minimum memory limit is 4 MB.");
				}
				JSONObject featureLimits = new JSONObject()
						.put("databases", databases)
						.put("allocations", allocations);
				JSONObject limits = new JSONObject()
						.put("memory", memory)
						.put("swap", swap)
						.put("disk", disk)
						.put("io", io)
						.put("cpu", cpu);
				JSONObject env = new JSONObject();
				if(environment != null) environment.forEach((key, value) -> env.put(key, value));
				List<Long> locationIds = new ArrayList<>();
				locations.forEach(l -> locationIds.add(l.getIdLong()));
				JSONObject deploy = new JSONObject()
						.put("locations", locationIds)
						.put("dedicated_ip", useDedicatedIP)
						.put("port_range", portRange);
				JSONObject obj = new JSONObject()
						.put("name", name)
						.put("description", description)
						.put("user", owner.getId())
						.put("nest", egg.retrieveNest().execute().getId())
						.put("egg", egg.getId())
						.put("docker_image", dockerImage)
						.put("startup", startupCommand != null ? startupCommand : egg.getStartupCommand())
						.put("limits", limits)
						.put("feature_limits", featureLimits)
						.put("environment", env)
						.put("deploy", deploy)
						.put("start_on_completion", startOnCompletion);
				Route.CompiledRoute route = Route.Servers.CREATE_SERVER.compile().withJSONdata(obj);
				JSONObject json = impl.getRequester().request(route).toJSONObject();
				return new ApplicationServerImpl(impl, json);
			}
		};
	}
}
