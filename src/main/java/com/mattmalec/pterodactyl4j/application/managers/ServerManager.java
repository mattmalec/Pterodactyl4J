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

package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.application.entities.impl.ApplicationServerImpl;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.Checks;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager providing functionality to update limits and features limits for an
 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
 *
 * <p><b>Example</b>
 * <pre>{@code
 * // this will set the server's memory limit to 4 GB
 * manager.setMemory(4, DataType.GB).executeAsync();
 * }</pre>
 *
 * @see ApplicationServer#getManager()
 */
public class ServerManager {

	private final ApplicationServer server;
	private final PteroApplicationImpl impl;

	public ServerManager(ApplicationServer server, PteroApplicationImpl impl) {
		this.server = server;
		this.impl = impl;
	}

	/**
	 * Sets the name of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * @param  name
	 *         The new name for the server
	 *
	 * @throws IllegalArgumentException
	 *         If the provided name is {@code null} or not between 1-191 characters long
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setName(String name) {
		Checks.notNull(name, "Name");
		Checks.check(name.length() >= 1 && name.length() <= 191, "Name must be between 1-191 characters long");
		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("name", name)
					.put("description", server.getDescription())
					.put("user", server.getOwner().get().orElseGet(() -> server.getOwner().retrieve().execute()).getId());
			return new PteroActionImpl<ApplicationServer>(impl.getP4J(),
					Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()), PteroActionImpl.getRequestBody(obj),
					(response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the owner of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * @param  user
	 *         The new owner for the server
	 *
	 * @throws IllegalArgumentException
	 *         If the provided user is {@code null}
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setOwner(ApplicationUser user) {
		Checks.notNull(user, "Owner");
		JSONObject obj = new JSONObject()
				.put("name", server.getName())
				.put("description", server.getDescription())
				.put("user", user.getId());
		return new PteroActionImpl<>(impl.getP4J(), Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()),
				PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject()));
	}

	/**
	 * Sets the description of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * @param  description
	 *         The new description for the server or {@code null} to remove
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setDescription(String description) {
		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("name", server.getName())
					.put("description", description)
					.put("user", server.getOwner().get().orElseGet(() -> server.getOwner().retrieve().execute()).getId());
			return new PteroActionImpl<ApplicationServer>(impl.getP4J(),
					Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()), PteroActionImpl.getRequestBody(obj),
					(response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the external id of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * @param  id
	 *         The new external for the server or {@code null} to remove
	 *
	 * @throws IllegalArgumentException
	 *         If the provided id is not between 1-191 characters long
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setExternalId(String id) {
		if (id != null)
			Checks.check(id.length() >= 1 && id.length() <= 191, "ID must be between 1-191 characters long");

		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("name", server.getName())
					.put("description", server.getDescription())
					.put("external_id", id)
					.put("user", server.getOwner().get().orElseGet(() -> server.getOwner().retrieve().execute()).getId());
			return new PteroActionImpl<ApplicationServer>(impl.getP4J(),
					Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()), PteroActionImpl.getRequestBody(obj),
					(response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the primary allocation for this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * @param  allocation
	 *         The new primary allocation for the server
	 *
	 * @throws IllegalArgumentException
	 *         If the provided allocation is {@code null}
	 *
	 * @throws com.mattmalec.pterodactyl4j.exceptions.MissingActionException
	 *         If the provided allocation is already assigned
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setAllocation(Allocation allocation) {
		Checks.notNull(allocation, "Allocation");
		JSONObject obj = new JSONObject()
				.put("allocation", allocation.getId());
		JSONObject limits = new JSONObject()
				.put("memory", server.getLimits().getMemory())
				.put("swap", server.getLimits().getSwap())
				.put("io", server.getLimits().getIO())
				.put("cpu", server.getLimits().getCPU())
				.put("disk", server.getLimits().getDisk())
				.put("threads", server.getLimits().getThreads());
		obj.put("limits", limits);
		JSONObject featureLimits = new JSONObject()
				.put("databases", server.getFeatureLimits().getDatabases())
				.put("allocations", server.getFeatureLimits().getAllocations())
				.put("backups", server.getFeatureLimits().getBackups());
		obj.put("feature_limits", featureLimits);
		return new PteroActionImpl<>(impl.getP4J(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
				PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject()));
	}

	/**
	 * Sets the memory limit of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * <br>Setting to {@code 0 MB} will allow unlimited memory in the container
	 *
	 * <p><b>Tip:</b> If you are setting the memory to 4 GB for example, setting the amount to {@code 4} and DataType to {@code GB} will convert the amount to {@code 4096} MB
	 * when submitting the data to the panel.
	 * <br>Amount of {@code 1024} and DataType {@code MB} is effectively the same as {@code 1} and {@code GB}
	 *
	 * @param  amount
	 *         The new amount of memory
	 *
	 * @param dataType
	 * 		  The unit of data pertaining to the amount
	 *
	 * @throws IllegalArgumentException
	 *         If the provided amount is less than 0 or provided DataType is {@code null}
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setMemory(long amount, DataType dataType) {
		Checks.notNull(dataType, "Data Type");
		Checks.check(amount > 0, "Memory cannot be less than 0");

		long trueAmount;
		if (dataType == DataType.MB)
			trueAmount = amount;
		else
			trueAmount = amount * dataType.getMbValue();

		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
				.put("allocation", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			JSONObject limits = new JSONObject()
					.put("memory", trueAmount)
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			obj.put("limits", limits);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getP4J(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the swap limit of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * <br>Setting to {@code -1 MB} will allow unlimited swap, setting to {@code 0 MB} will allow unlimited swap
	 *
	 * <p><b>Tip:</b> If you are setting the swap to 4 GB for example, setting the amount to {@code 4} and DataType to {@code GB} will convert the amount to {@code 4096} MB
	 * when submitting the data to the panel.
	 * <br>Amount of {@code 1024} and DataType {@code MB} is effectively the same as {@code 1} and {@code GB}
	 *
	 * @param  amount
	 *         The new amount of swap
	 *
	 * @param dataType
	 * 		  The unit of data pertaining to the amount
	 *
	 * @throws IllegalArgumentException
	 *         If the provided amount is less than -1 MB or provided DataType is {@code null}
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setSwap(long amount, DataType dataType) {
		long trueAmount;
		if (dataType == DataType.MB)
			trueAmount = amount;
		else
			trueAmount = amount * dataType.getMbValue();

		Checks.check(trueAmount >= -1, "Swap cannot be less than -1 MB");

		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("allocation", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			JSONObject limits = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", trueAmount)
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			obj.put("limits", limits);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getP4J(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the block io proportion of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * <p><b>This is an advanced feature and should be left at the default value</b>
	 *
	 * <p>Default: <b>500</b>
	 *
	 * @param  amount
	 *         The new block io proportion
	 *
	 * @throws IllegalArgumentException
	 *         If the provided amount is not between 10-1000
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setIO(long amount) {
		Checks.check(amount >= 10 && amount <= 1000, "Proportion must be between 10-1000");
		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("allocation", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			JSONObject limits = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", amount)
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			obj.put("limits", limits);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getP4J(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the cpu limit of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 * <br>Each physical core on the node is considered to be multiple of {@code 100}. Setting this value to {@code 0} will allow
	 * unlimited cpu performance for the container
	 *
	 * @param  amount
	 *         The new cpu limit
	 *
	 * @throws IllegalArgumentException
	 *         If the provided amount is less than 0
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setCPU(long amount) {
		Checks.check(amount > 0, "Amount must be greater than 0");
		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("allocation", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			JSONObject limits = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", amount)
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			obj.put("limits", limits);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getP4J(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the indivdual cores this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} can use.
	 * <br>This allows you to specify the individual cpu cores the container process can run on. You can specify a single number or a range of values
	 *
	 * <p><b>This is an advanced feature and should be left at the default value</b>
	 *
	 * <p>Default: <b>blank (all cores)</b>
	 *
	 * <p><b>Example:</b> {@code 0}, {@code 0-1,3}, or {@code 0,1,3,4}
	 * <br>The panel validates this value using the following regex: {@code /^[0-9-,]+$/}
	 *
	 * @param  cores
	 *         The cpu cores the container process can run on
	 *
	 * @throws IllegalArgumentException
	 *         If the provided amount is less than 0
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setThreads(String cores) {
		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("allocation", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			JSONObject limits = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", cores);
			obj.put("limits", limits);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getP4J(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the disk space limit of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * <br>Setting to {@code 0 MB} will allow unlimited disk space
	 *
	 * <p><b>Tip:</b> If you are setting the disk space to 4 GB for example, setting the amount to {@code 4} and DataType to {@code GB} will convert the amount to {@code 4096} MB
	 * when submitting the data to the panel.
	 * <br>Amount of {@code 1024} and DataType {@code MB} is effectively the same as {@code 1} and {@code GB}
	 *
	 * @param  amount
	 *         The new amount of disk space
	 *
	 * @param dataType
	 * 		  The unit of data pertaining to the amount
	 *
	 * @throws IllegalArgumentException
	 *         If the provided amount is less than 0 MB or provided DataType is {@code null}
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setDisk(long amount, DataType dataType) {
		Checks.check(amount > 0, "Amount must be greater than 0");

		long trueAmount;
		if (dataType == DataType.MB)
			trueAmount = amount;
		else
			trueAmount = amount * dataType.getMbValue();

		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("allocation", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			JSONObject limits = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", trueAmount)
					.put("threads", server.getLimits().getThreads());
			obj.put("limits", limits);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getP4J(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the maximum number of databases that can be created for this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * @param  amount
	 *         The new database limit
	 *
	 * @throws IllegalArgumentException
	 *         If the provided amount is less than 0
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setAllowedDatabases(int amount) {
		Checks.check(amount > 0, "Amount must be greater than 0");
		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("allocation", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			JSONObject limits = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			obj.put("limits", limits);
			JSONObject featureLimits = new JSONObject()
					.put("databases", amount)
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getP4J(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the maximum number of allocations that can be assigned to this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * @param  amount
	 *         The new allocation limit
	 *
	 * @throws IllegalArgumentException
	 *         If the provided amount is less than 0
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setAllowedAllocations(int amount) {
		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("allocation", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			JSONObject limits = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			obj.put("limits", limits);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", amount)
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getP4J(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the maximum number of backups that can be created for this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * @param  amount
	 *         The new backup limit
	 *
	 * @throws IllegalArgumentException
	 *         If the provided amount is less than 0
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setAllowedBackups(int amount) {
		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("allocation", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			JSONObject limits = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			obj.put("limits", limits);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", amount);
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getP4J(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Enables/Disables the out of memory killer for this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 * <br>Enabling the OOM killer will kill the container process if it runs out of memory.
	 * <p><b>If enabled, this may cause the server to exit unexpectedly</b>
	 *
	 * <p>Default: <b>disabled (false)</b>
	 *
	 * <p><b>Note:</b> You will need to restart the server for the changes to take effect.
	 *
	 * @param  enable
	 *         True - enable the oom killer
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setEnableOOMKiller(boolean enable) {
		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("allocation", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId())
					.put("oom_disabled", !enable);
			JSONObject limits = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			obj.put("limits", limits);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getP4J(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the start up command of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * @param  command
	 *         The new startup command for the server
	 *
	 * @throws IllegalArgumentException
	 *         If the provided command is {@code null}
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setStartupCommand(String command) {
		Checks.notNull(command, "Startup Command");
		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			JSONObject obj = new JSONObject()
					.put("startup", command)
					.put("environment", server.getContainer().getEnvironment().keySet())
					.put("egg", server.getEgg().retrieve().execute().getId())
					.put("image", server.getContainer().getImage())
					// this won't do anything since the server is installed, but pterodactyl requires it so here we are
					.put("skip_scripts", true);
			return new PteroActionImpl<ApplicationServer>(impl.getP4J(),
					Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the egg environment variables of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 * <br>This method will not remove any existing environment variables, it will only replace them.
	 *
	 * @param  environment
	 *         The updated map
	 *
	 * @throws IllegalArgumentException
	 *         If the provided environment map is {@code null}
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 *
	 * @see EnvironmentValue
	 */
	public PteroAction<ApplicationServer> setEnvironment(Map<String, EnvironmentValue<?>> environment) {
		Checks.notNull(environment, "Environment");
		return PteroActionImpl.onExecute(impl.getP4J(), () ->
		{
			Map<String, EnvironmentValue<?>> env = new HashMap<>(server.getContainer().getEnvironment());
			env.putAll(environment);
			JSONObject obj = new JSONObject()
					.put("startup", server.getContainer().getStartupCommand())
					.put("environment", env.entrySet().stream()
							.collect(EnvironmentValue.collector()))
					.put("egg", server.getEgg().retrieve().execute().getId())
					.put("image", server.getContainer().getImage())
					// this won't do anything since the server is installed, but pterodactyl requires it so here we are
					.put("skip_scripts", true);
			return new PteroActionImpl<ApplicationServer>(impl.getP4J(),
					Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Sets the egg to use with this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * <p><b>Note:</b> You will need to restart the server for the changes to take effect.
	 *
	 * @param  egg
	 *         The new egg for the server
	 *
	 * @throws IllegalArgumentException
	 *         If the provided egg is {@code null}
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 *
	 * @see ClientServer#restart()
	 */
	public PteroAction<ApplicationServer> setEgg(ApplicationEgg egg) {
		JSONObject obj = new JSONObject()
				.put("startup", server.getContainer().getStartupCommand())
				.put("environment", server.getContainer().getEnvironment().entrySet()
				.stream().collect(EnvironmentValue.collector()))
				.put("egg", egg.getId())
				.put("image", server.getContainer().getImage())
				// this won't do anything since the server is installed, but pterodactyl requires it so here we are
				.put("skip_scripts", true);
		return new PteroActionImpl<>(impl.getP4J(),
				Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()),
				PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject()));

	}

	/**
	 * Sets the Docker image for the {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} to use.
	 *
	 * <p><b>Note:</b> You will need to restart the server for the changes to take effect.
	 *
	 * @param  dockerImage
	 *         The new Docker image for the server
	 *
	 * @throws IllegalArgumentException
	 *         If the provided image is {@code null} or longer then 191 characters
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 *
	 * @see ClientServer#restart()
	 */
	public PteroAction<ApplicationServer> setImage(String dockerImage) {
		Checks.notNull(dockerImage, "Docker Image");
		Checks.check(dockerImage.length() <= 191, "Docker image cannot be longer than 191 characters");
		return PteroActionImpl.onExecute(impl.getP4J(), () -> {
			JSONObject obj = new JSONObject()
					.put("startup", server.getContainer().getStartupCommand())
					.put("environment", server.getContainer().getEnvironment().entrySet()
							.stream().collect(EnvironmentValue.collector()))
					.put("egg", server.getEgg().retrieve().execute().getId())
					.put("image", dockerImage)
					// this won't do anything since the server is installed, but pterodactyl requires it so here we are
					.put("skip_scripts", true);
			return new PteroActionImpl<ApplicationServer>(impl.getP4J(),
					Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	/**
	 * Enables/Disables Pterodactyl to run the egg install scripts for {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 * <p><b>Note:</b> This will have no effect on a server that's already installed, but it's here for consistancy.
	 *
	 * <p>Default: <b>disabled (false)</b>
	 *
	 * @param  skipScripts
	 *         True - will not run egg install scripts
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 */
	public PteroAction<ApplicationServer> setSkipScripts(boolean skipScripts) {
		return PteroActionImpl.onExecute(impl.getP4J(), () -> {
				JSONObject obj = new JSONObject()
						.put("startup", server.getContainer().getStartupCommand())
						.put("environment", server.getContainer().getEnvironment().entrySet()
								.stream().collect(EnvironmentValue.collector()))
						.put("egg", server.getEgg().retrieve().execute().getId())
						.put("image", server.getContainer().getImage())
						.put("skip_scripts", skipScripts);
			return new PteroActionImpl<ApplicationServer>(impl.getP4J(),
					Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
        });
	}
}
