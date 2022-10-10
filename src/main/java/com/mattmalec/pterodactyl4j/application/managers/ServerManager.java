/*
 *    Copyright 2021-2022 Matt Malec, and the Pterodactyl4J contributors
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
import com.mattmalec.pterodactyl4j.application.entities.ApplicationAllocation;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
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

	public ServerManager(ApplicationServer server) {
		this.server = server;
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerDetailManager#setName(String)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setName(String name) {
		return server.getDetailManager().setName(name);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerDetailManager#setOwner(ApplicationUser)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setOwner(ApplicationUser user) {
		return server.getDetailManager().setOwner(user);
	}

	/**
	 * Sets the description of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
	 *
	 * @param  description
	 *         The new description for the server or {@code null} to remove
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type
	 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} - The updated server
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerDetailManager#setDescription(String)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setDescription(String description) {
		return server.getDetailManager().setDescription(description);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerDetailManager#setExternalId(String)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setExternalId(String id) {
		return server.getDetailManager().setExternalId(id);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerBuildManager#setAllocation(ApplicationAllocation)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setAllocation(ApplicationAllocation allocation) {
		return server.getBuildManager().setAllocation(allocation);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerBuildManager#setMemory(long, DataType)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setMemory(long amount, DataType dataType) {
		return server.getBuildManager().setMemory(amount, dataType);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerBuildManager#setSwap(long, DataType)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setSwap(long amount, DataType dataType) {
		return server.getBuildManager().setSwap(amount, dataType);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerBuildManager#setIO(long)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setIO(long amount) {
		return server.getBuildManager().setIO(amount);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerBuildManager#setCPU(long)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setCPU(long amount) {
		return server.getBuildManager().setCPU(amount);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerBuildManager#setThreads(String)} instead
	 *
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setThreads(String cores) {
		return server.getBuildManager().setThreads(cores);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerBuildManager#setDisk(long, DataType)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setDisk(long amount, DataType dataType) {
		return server.getBuildManager().setDisk(amount, dataType);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerBuildManager#setAllowedDatabases(int)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setAllowedDatabases(int amount) {
		return server.getBuildManager().setAllowedDatabases(amount);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerBuildManager#setAllowedAllocations(int)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setAllowedAllocations(int amount) {
		return server.getBuildManager().setAllowedAllocations(amount);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerBuildManager#setAllowedBackups(int)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setAllowedBackups(int amount) {
		return server.getBuildManager().setAllowedBackups(amount);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerBuildManager#setEnableOOMKiller(boolean)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setEnableOOMKiller(boolean enable) {
		return server.getBuildManager().setEnableOOMKiller(enable);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerStartupManager#setStartupCommand(String)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setStartupCommand(String command) {
		return server.getStartupManager().setStartupCommand(command);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerStartupManager#setEnvironment(Map)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setEnvironment(Map<String, EnvironmentValue<?>> environment) {
		return server.getStartupManager().setEnvironment(environment);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerStartupManager#setEgg(ApplicationEgg)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setEgg(ApplicationEgg egg) {
		return server.getStartupManager().setEgg(egg);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerStartupManager#setImage(String)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setImage(String dockerImage) {
		return server.getStartupManager().setImage(dockerImage);
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
	 *
	 * @deprecated This will be removed in the next major release (non-beta). Use {@link ServerStartupManager#setSkipScripts(boolean)} instead
	 */
	@Deprecated
	public PteroAction<ApplicationServer> setSkipScripts(boolean skipScripts) {
		return server.getStartupManager().setSkipScripts(skipScripts);
	}
}
