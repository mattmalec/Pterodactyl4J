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

package com.mattmalec.pterodactyl4j.requests.action;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.entities.impl.ScheduleTaskImpl;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleTaskAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import okhttp3.RequestBody;
import org.json.JSONObject;

public abstract class AbstractScheduleTaskAction extends PteroActionImpl<Schedule.ScheduleTask> implements ScheduleTaskAction {
    
    protected Schedule.ScheduleTask.ScheduleAction action;
    protected String payload;
    protected String timeOffset;
    protected boolean continueOnFailure;

    public AbstractScheduleTaskAction(PteroClientImpl impl, Schedule schedule, Route.CompiledRoute route) {
        super(impl.getP4J(), route, (response, request) -> new ScheduleTaskImpl(response.getObject(), schedule));
    }

    @Override
    public ScheduleTaskAction setAction(Schedule.ScheduleTask.ScheduleAction action) {
        this.action = action;
        return this;
    }

    @Override
    public ScheduleTaskAction setPowerPayload(PowerAction payload) {
        this.payload = payload.name().toLowerCase();
        return this;
    }

    @Override
    public ScheduleTaskAction setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    @Override
    public ScheduleTaskAction setTimeOffset(String seconds) {
        this.timeOffset = seconds;
        return this;
    }

    @Override
    public ScheduleTaskAction setContinueOnFailure(boolean continueOnFailure) {
        this.continueOnFailure = continueOnFailure;
        return this;
    }

    @Override
    protected RequestBody finalizeData() {
        JSONObject json = new JSONObject()
                .put("action", action.name().toLowerCase())
                .put("payload", payload)
                .put("continue_on_failure", continueOnFailure)
                .put("time_offset", timeOffset == null ? "0" : timeOffset);
        return getRequestBody(json);
    }
}
