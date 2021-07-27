/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.*;
import com.mattmalec.pterodactyl4j.application.managers.ServerCreationAction;
import com.mattmalec.pterodactyl4j.exceptions.IllegalActionException;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateServerImpl extends PteroActionImpl<ApplicationServer> implements ServerCreationAction {

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
	private Map<String, EnvironmentValue<?>> environment;
	private Set<Location> locations;
	private Set<Integer> portRange;
	private boolean useDedicatedIP;
	private boolean startOnCompletion;
	private boolean skipScripts;
	private Allocation defaultAllocation;
	private Collection<Allocation> additionalAllocations;

	private final PteroApplicationImpl impl;

	public CreateServerImpl(PteroApplicationImpl impl) {
		super(impl.getP4J(), Route.Servers.CREATE_SERVER.compile(),
				(response, request) -> new ApplicationServerImpl(impl, response.getObject()));
		this.environment = new HashMap<>();
		this.impl = impl;
	}

	@Override
	public ServerCreationAction setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public ServerCreationAction setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public ServerCreationAction setOwner(ApplicationUser owner) {
		this.owner = owner;
		return this;
	}

	@Override
	public ServerCreationAction setEgg(ApplicationEgg egg) {
		this.egg = egg;
		return this;
	}

	@Override
	public ServerCreationAction setDockerImage(String dockerImage) {
		this.dockerImage = dockerImage;
		return this;
	}

	@Override
	public ServerCreationAction setStartupCommand(String command) {
		this.startupCommand = command;
		return this;
	}

	@Override
	public ServerCreationAction setMemory(long amount, DataType dataType) {
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
	public ServerCreationAction setSwap(long amount, DataType dataType) {
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
	public ServerCreationAction setDisk(long amount, DataType dataType) {
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
	public ServerCreationAction setIO(long amount) {
		this.io = amount;
		return this;
	}

	@Override
	public ServerCreationAction setThreads(String cores) {
		this.threads = cores;
		return this;
	}

	@Override
	public ServerCreationAction setCPU(long amount) {
		this.cpu = amount;
		return this;
	}

	@Override
	public ServerCreationAction setDatabases(long amount) {
		this.databases = amount;
		return this;
	}

	@Override
	public ServerCreationAction setAllocations(long amount) {
		this.allocations = amount;
		return this;
	}

	@Override
	public ServerCreationAction setBackups(long amount) {
		this.backups = amount;
		return this;
	}

	@Override
	public ServerCreationAction setEnvironment(Map<String, EnvironmentValue<?>> environment) {
		this.environment = environment;
		return this;
	}

	@Override
	public ServerCreationAction setLocations(Set<Location> locations) {
		this.locations = locations;
		return this;
	}

	@Override
	public ServerCreationAction setDedicatedIP(boolean dedicatedIP) {
		this.useDedicatedIP = dedicatedIP;
		return this;
	}

	@Override
	public ServerCreationAction setPortRange(Set<Integer> ports) {
		this.portRange = ports;
		return this;
	}

	@Override
	public ServerCreationAction startOnCompletion(boolean start) {
		this.startOnCompletion = start;
		return this;
	}

	@Override
	public ServerCreationAction skipScripts(boolean skip) {
		this.skipScripts = skip;
		return this;
	}

	@Override
	public ServerCreationAction setAllocations(Allocation defaultAllocation, Collection<Allocation> additionalAllocations) {
		this.defaultAllocation = defaultAllocation;
		this.additionalAllocations = additionalAllocations;
		return this;
	}

	@Override
	protected RequestBody finalizeData() {
		if (memory < 4) {
			throw new IllegalActionException("The minimum memory limit is 4 MB.");
		}
		Checks.notNull(owner, "Owner");
		Checks.notNull(egg, "Egg and Nest");
		Nest nest = egg.getNest().get().orElseGet(() -> egg.getNest().retrieve().execute());
		Map<String, Object> env = new HashMap<>();
		environment.forEach((k, v) -> env.put(k, v.get().orElse(null)));
		egg.getDefaultVariableMap().orElseGet(() ->
				impl.retrieveEggById(nest, egg.getId()).execute().getDefaultVariableMap().get())
				.forEach((k, v) -> env.putIfAbsent(k, v.get().orElse(null)));
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
				.put("additional", (additionalAllocations != null && additionalAllocations.size() != 0) ?
						additionalAllocations.stream().map(Allocation::getIdLong).collect(Collectors.toList()) : null);
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
				.put("environment", env)
				.put("deploy", (locations != null || portRange != null) ? deploy : null)
				.put("allocation", allocation)
				.put("start_on_completion", startOnCompletion)
				.put("skip_scripts", skipScripts);
		return getRequestBody(obj);
	}
}
