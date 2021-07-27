package com.mattmalec.pterodactyl4j.requests.action;

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.entities.impl.ScheduleTaskImpl;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleTaskAction;
import com.mattmalec.pterodactyl4j.requests.Route;

public abstract class AbstractScheduleTaskAction extends PteroActionImpl<Schedule.ScheduleTask> implements ScheduleTaskAction {
    
    protected Schedule.ScheduleTask.ScheduleAction action;
    protected String payload;
    protected String timeOffset;

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
}
