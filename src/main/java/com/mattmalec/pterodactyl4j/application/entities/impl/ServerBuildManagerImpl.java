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
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.managers.ServerBuildManager;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractManagerBase;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class ServerBuildManagerImpl extends AbstractManagerBase implements ServerBuildManager {

    private final ApplicationServer server;

    private long allocation;
    private long memory;
    private long swap;
    private long io;
    private long cpu;
    private String threads;
    private long disk;

    private long databases;
    private long allocations;
    private long backups;

    private boolean oom;

    public ServerBuildManagerImpl(ApplicationServer server, PteroApplicationImpl impl) {
        super(impl, Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()));
        this.server = server;
    }

    @Override
    public ServerBuildManager setAllocation(Allocation allocation) {
        Checks.notNull(allocation, "Allocation");

        this.allocation = allocation.getIdLong();
        set |= ALLOCATION;
        return this;
    }

    @Override
    public ServerBuildManager setMemory(long amount, DataType dataType) {
        Checks.notNull(dataType, "Data Type");
        Checks.check(amount > 0, "Memory cannot be less than 0 MB");

        set |= MEMORY;
        this.memory = convert(amount, dataType);
        return this;
    }

    @Override
    public ServerBuildManager setSwap(long amount, DataType dataType) {
        Checks.notNull(dataType, "Data Type");
        Checks.check(amount >= -1, "Swap cannot be less than -1 MB");

        set |= SWAP;
        this.swap = convert(amount, dataType);
        return this;
    }

    @Override
    public ServerBuildManager setIO(long amount) {
        Checks.check(amount >= 10 && amount <= 1000, "Proportion must be between 10-1000");

        set |= IO;
        this.io = amount;
        return this;
    }

    @Override
    public ServerBuildManager setCPU(long amount) {
        Checks.check(amount > 0, "CPU must be greater than 0");

        set |= CPU;
        this.cpu = amount;
        return this;
    }

    @Override
    public ServerBuildManager setThreads(String cores) {
        set |= THREADS;
        this.threads = cores;
        return this;
    }

    @Override
    public ServerBuildManager setDisk(long amount, DataType dataType) {
        Checks.notNull(dataType, "Data Type");
        Checks.check(amount > 0, "Disk size must be greater than 0 MB");

        set |= DISK;
        this.disk = convert(amount, dataType);
        return this;
    }

    @Override
    public ServerBuildManager setAllowedDatabases(int amount) {
        Checks.check(amount >= 0, "Database limit must be 0 or greater");

        set |= DATABASES;
        this.databases = amount;
        return this;
    }

    @Override
    public ServerBuildManager setAllowedAllocations(int amount) {
        Checks.check(amount >= 0, "Allocation limit must be 0 or greater");

        set |= ALLOCATIONS;
        this.allocations = amount;
        return this;
    }

    @Override
    public ServerBuildManager setAllowedBackups(int amount) {
        Checks.check(amount >= 0, "Backup limit must be 0 or greater");

        set |= BACKUPS;
        this.backups = amount;
        return this;
    }

    @Override
    public ServerBuildManager setEnableOOMKiller(boolean enable) {
        set |= OOM;
        this.oom = enable;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        JSONObject obj = new JSONObject()
                .put("allocation", shouldUpdate(ALLOCATION) ? allocation : server.getDefaultAllocationIdLong());

        if (shouldUpdate(OOM))
            obj.put("oom_disabled", !oom);

        JSONObject limits = new JSONObject()
                .put("memory", shouldUpdate(MEMORY) ? memory : server.getLimits().getMemory())
                .put("swap", shouldUpdate(SWAP) ? swap : server.getLimits().getSwap())
                .put("io", shouldUpdate(IO) ? io : server.getLimits().getIO())
                .put("cpu", shouldUpdate(CPU) ? cpu : server.getLimits().getCPU())
                .put("disk", shouldUpdate(DISK) ? disk : server.getLimits().getDisk())
                .put("threads", shouldUpdate(THREADS) ? threads : server.getLimits().getThreads());
        obj.put("limits", limits);
        JSONObject featureLimits = new JSONObject()
                .put("databases", shouldUpdate(DATABASES) ? databases : server.getFeatureLimits().getDatabases())
                .put("allocations", shouldUpdate(ALLOCATIONS) ? allocations : server.getFeatureLimits().getAllocations())
                .put("backups", shouldUpdate(BACKUPS) ? backups : server.getFeatureLimits().getBackups());
        obj.put("feature_limits", featureLimits);

        super.reset();
        return getRequestBody(obj);
    }

    private long convert(long amount, DataType dataType) {
        if (dataType != DataType.MB)
            amount = amount * dataType.getMbValue();
        return amount;
    }
}
