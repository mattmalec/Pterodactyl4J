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
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationAllocation;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;

/**
 * Manager providing functionality to modify the build configuration for an
 * {@link ApplicationServer ApplicationServer}.
 *
 * <p><b>Example</b>
 * <pre>{@code
 * manager.setMemory(4, DataType.GB) // set the server memory to 4 GB
 *     .setDisk(5, DataType.GB) // set the server disk space to 5 GB
 *     .executeAsync();
 * }</pre>
 *
 * @see ApplicationServer#getBuildManager()
 */
public interface ServerBuildManager extends PteroAction<ApplicationServer> {

    // The following bits are used internally, but are here in case something user-facing is added in the future

    long ALLOCATION = 0x1;

    long MEMORY = 0x2;
    long SWAP = 0x4;
    long IO = 0x8;
    long CPU = 0x10;
    long THREADS = 0x20;
    long DISK = 0x40;

    long DATABASES = 0x80;
    long ALLOCATIONS = 0x100;
    long BACKUPS = 0x200;

    long OOM = 0x400;

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
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}
     * instance, useful for chaining
     */
    ServerBuildManager setAllocation(ApplicationAllocation allocation);

    /**
     * Sets the memory limit of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
     *
     * <br>Setting to {@code 0 MB} will allow unlimited memory in the container
     *
     * <p><b>Tip:</b> If you are setting the memory to 4 GB for example, setting the amount to {@code 4} and DataType
     * to {@code GB} will convert the amount to {@code 4096} MB when submitting the data to the panel.
     *
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
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}
     * instance, useful for chaining
     */
    ServerBuildManager setMemory(long amount, DataType dataType);

    /**
     * Sets the swap limit of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
     *
     * <br>Setting to {@code -1 MB} will allow unlimited swap, setting to {@code 0 MB} will allow unlimited swap
     *
     * <p><b>Tip:</b> If you are setting the swap to 4 GB for example, setting the amount to {@code 4} and DataType
     * to {@code GB} will convert the amount to {@code 4096} MB when submitting the data to the panel.
     *
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
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}
     * instance, useful for chaining
     */
    ServerBuildManager setSwap(long amount, DataType dataType);

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
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}
     * instance, useful for chaining
     */
    ServerBuildManager setIO(long amount);
    
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
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}
     * instance, useful for chaining
     */
    ServerBuildManager setCPU(long amount);

    /**
     * Sets the indivdual cores this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} can use.
     * <br>This allows you to specify the individual cpu cores the container process can run on.
     * You can specify a single number or a range of values
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
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}
     * instance, useful for chaining
     */
    ServerBuildManager setThreads(String cores);

    /**
     * Sets the disk space limit of this {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
     *
     * <br>Setting to {@code 0 MB} will allow unlimited disk space
     *
     * <p><b>Tip:</b> If you are setting the disk space to 4 GB for example, setting the amount to {@code 4} and DataType
     * to {@code GB} will convert the amount to {@code 4096} MB when submitting the data to the panel.
     *
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
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}
     * instance, useful for chaining
     */
    ServerBuildManager setDisk(long amount, DataType dataType);

    /**
     * Sets the maximum number of databases that can be created for this
     * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
     *
     * @param  amount
     *         The new database limit
     *
     * @throws IllegalArgumentException
     *         If the provided amount is less than 0
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}
     * instance, useful for chaining
     */
    ServerBuildManager setAllowedDatabases(int amount);

    /**
     * Sets the maximum number of allocations that can be assigned to this
     * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
     *
     * @param  amount
     *         The new allocation limit
     *
     * @throws IllegalArgumentException
     *         If the provided amount is less than 0
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}
     * instance, useful for chaining
     */
    ServerBuildManager setAllowedAllocations(int amount);

    /**
     * Sets the maximum number of backups that can be created for this
     * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
     *
     * @param  amount
     *         The new backup limit
     *
     * @throws IllegalArgumentException
     *         If the provided amount is less than 0
     *
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}
     * instance, useful for chaining
     */
    ServerBuildManager setAllowedBackups(int amount);

    /**
     * Enables/Disables the out of memory killer for this
     * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}.
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
     * @return The {@link com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager ServerBuildManager}
     * instance, useful for chaining
     */
    ServerBuildManager setEnableOOMKiller(boolean enable);

}
