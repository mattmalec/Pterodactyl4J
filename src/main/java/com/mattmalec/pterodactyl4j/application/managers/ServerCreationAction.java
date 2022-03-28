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
import com.mattmalec.pterodactyl4j.application.entities.*;

import java.util.*;

/**
 * Represents a {@link PteroAction} that can be used while creating an {@link ApplicationServer}.
 *
 * @see PteroAction
 */
public interface ServerCreationAction extends PteroAction<ApplicationServer> {

	/**
	 * Sets the name of the {@link ApplicationServer}.
	 *
	 * @param name The servers name
	 * @return The same creation action with the applied name
	 */
	ServerCreationAction setName(String name);

	/**
	 * Sets the description of the {@link ApplicationServer}.
	 *
	 * @param description The servers description
	 * @return The same creation action with the applied description
	 */
	ServerCreationAction setDescription(String description);

	/**
	 * Sets the owner of the {@link ApplicationServer}.
	 *
	 * @param owner The servers owner
	 * @return The same creation action with the applied owner
	 * @see ApplicationUser
	 */
	ServerCreationAction setOwner(ApplicationUser owner);

	/**
	 * Sets the {@link ApplicationEgg egg} of the {@link ApplicationServer}.
	 *
	 * @param egg The egg to use for the server
	 * @return The same creation action with the applied egg
	 * @see ApplicationEgg
	 */
	ServerCreationAction setEgg(ApplicationEgg egg);

	/**
	 * Sets the docker image of the {@link ApplicationServer}.
	 *
	 * @param dockerImage The docker image for the server
	 * @return The same creation action with the applied docker image
	 */
	ServerCreationAction setDockerImage(String dockerImage);

	/**
	 * Sets the start-up command of the {@link ApplicationServer}.
	 *
	 * @param command The start-up command for the server
	 * @return The same creation action with the applied start-up command
	 */
	ServerCreationAction setStartupCommand(String command);

	/**
	 * Sets the <solid>maximum</solid> memory of the {@link ApplicationServer}.
	 * Setting this to 0 will allow unlimited memory in a container.
	 *
	 * @param amount Maximum amount of memory
	 * @param dataType The type of data for the amount
	 * @return The same creation action with the applied memory
	 * @see DataType
	 */
	ServerCreationAction setMemory(long amount, DataType dataType);

	/**
	 * Sets the swap memory of the {@link ApplicationServer}.
	 * Setting this to 0 will disable swap space on this server. Setting to -1 will allow unlimited swap.
	 *
	 * @param amount The amount of swap memory
	 * @param dataType The type of data for the amount
	 * @return The same creation action with the applied swap
	 */
	ServerCreationAction setSwap(long amount, DataType dataType);

	/**
	 * The {@link ApplicationServer} will not be allowed to boot if it is using more than this amount of space.
	 * If a {@link ApplicationServer} goes over this limit while running it will be safely stopped and locked until enough space is available.
	 * Set to 0 to allow unlimited disk usage.
	 *
	 * @param amount The amount of disk space
	 * @param dataType The type of data for the amount
	 * @return The same creation action with the applied disk space
	 */
	ServerCreationAction setDisk(long amount, DataType dataType);

	/**
	 * Set IO performance of the {@link ApplicationServer} relative to other running containers.
	 *
	 * @param amount The amount of IO space
	 * @return The same creation action with the applied io space
	 */
	ServerCreationAction setIO(long amount);

	/**
	 * Each physical core on the system is considered to be 100%.
	 * Setting this value to 0 will allow the {@link ApplicationServer} to use CPU time without restrictions.
	 *
	 * @param amount The maximum amount of CPU usage
	 * @return The same creation action with the applied maximum cpu
	 */
	ServerCreationAction setCPU(long amount);

	/**
	 * Set the specific CPU cores that the {@link ApplicationServer} can run on, or leave blank to allow all cores.
	 * This can be a single number, or a comma separated list. Example: 0, 0-1,3, or 0,1,3,4.
	 *
	 * @param cores The amount of cpu cores
	 * @return The same creation action with the applied cpu cores
	 */
	ServerCreationAction setThreads(String cores);

	/**
	 * Set the total number of databases a user is allowed to create for this {@link ApplicationServer}.
	 *
	 * @param amount The amount of databases
	 * @return The same creation action with the applied database amount
	 */
	ServerCreationAction setDatabases(long amount);

	/**
	 * Set the total number of allocations a user is allowed to create for this {@link ApplicationServer}.
	 *
	 * @param amount The amount of allocations
	 * @return The same creation action with the applied allocation amount
	 */
	ServerCreationAction setAllocations(long amount);

	/**
	 * Set the total number of backups a user is allowed to create for this {@link ApplicationServer}.
	 *
	 * @param amount The amount of backups
	 * @return The same creation action with the applied backup amount
	 */
	ServerCreationAction setBackups(long amount);

	/**
	 * Set the {@link EnvironmentValue environment variables} for this {@link ApplicationServer}.
	 *
	 * @param environment The environment variables for the server
	 * @return The same creation action with the applied environment variables
	 */
	ServerCreationAction setEnvironment(Map<String, EnvironmentValue<?>> environment);

	/**
	 * Set the {@link Location locations} for this {@link ApplicationServer}.
	 *
	 * @param locations List of locations
	 * @return The same creation action with the applied locations
	 */
	ServerCreationAction setLocations(Set<Location> locations);

	/**
	 * Set the {@link Location} for this {@link ApplicationServer}.
	 *
	 * @param location The location
	 * @return The same creation action with the applied location
	 */
	default ServerCreationAction setLocation(Location location) {
		return setLocations(Collections.singleton(location));
	}

	/**
	 * Set whether the {@link ApplicationServer} should have a dedicated ip.
	 *
	 * @param dedicatedIP Whether a dedicated ip should be set
	 * @return The same creation action with the applied dedicated ip
	 */
	ServerCreationAction setDedicatedIP(boolean dedicatedIP);

	/**
	 * Set the port range for the {@link ApplicationServer}
	 *
	 * @param ports The port range
	 * @return The same creation action with the applied ports
	 */
	ServerCreationAction setPortRange(Set<Integer> ports);

	/**
	 * Set the port for the {@link ApplicationServer}.
	 *
	 * @param port The port
	 * @return The same creation action with the applied port
	 */
	default ServerCreationAction setPort(int port) {
		return setPortRange(Collections.singleton(port));
	}

	/**
	 * Set the {@link ApplicationAllocation allocations} for this {@link ApplicationServer}.
	 *
	 * @param defaultAllocation the default allocation
	 * @param additionalAllocations additional allocations
	 * @return The same creation action with the applied allocations
	 */
	default ServerCreationAction setAllocations(ApplicationAllocation defaultAllocation, ApplicationAllocation... additionalAllocations) {
		return setAllocations(defaultAllocation, Arrays.asList(additionalAllocations));
	}

	/**
	 * Set the {@link ApplicationAllocation} for this {@link ApplicationServer}.
	 *
	 * @param defaultAllocation the default allocation
	 * @return The same creation action with the applied allocation
	 */
	default ServerCreationAction setAllocation(ApplicationAllocation defaultAllocation) {
		return setAllocations(defaultAllocation);
	}

	/**
	 * Set the {@link ApplicationAllocation allocations} for this {@link ApplicationServer}.
	 *
	 * @param defaultAllocation the default allocation
	 * @param additionalAllocations additional allocations
	 * @return The same creation action with the applied allocations
	 */
	ServerCreationAction setAllocations(ApplicationAllocation defaultAllocation, Collection<ApplicationAllocation> additionalAllocations);

	/**
	 * Set whether the {@link ApplicationServer} should start upon completion.
	 *
	 * @param start Whether it should start upon completion
	 * @return The same creation action with the applied start-on-completion option
	 */
	ServerCreationAction startOnCompletion(boolean start);

	/**
	 * Set whether installation scripts from the egg should be skipped.
	 *
	 * @param skip Whether to skip scripts from the egg
	 * @return The same creation action with the applied skip-scripts option
	 */
	ServerCreationAction skipScripts(boolean skip);

}
