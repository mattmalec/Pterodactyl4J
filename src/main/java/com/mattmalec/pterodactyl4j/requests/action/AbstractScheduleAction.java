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

import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Cron;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.entities.impl.ScheduleImpl;
import com.mattmalec.pterodactyl4j.client.managers.ScheduleAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.CronUtils;

public abstract class AbstractScheduleAction extends PteroActionImpl<Schedule> implements ScheduleAction {

    protected String name;
    protected Boolean active;
    protected Cron cron;
    protected String minute;
    protected String hour;
    protected String dayOfWeek;
    protected String dayOfMonth;

    public AbstractScheduleAction(PteroClientImpl impl, ClientServer server, Route.CompiledRoute route) {
        super(impl.getP4J(), route, (response, request) -> new ScheduleImpl(response.getObject(), server, impl));
    }

    @Override
    public ScheduleAction setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ScheduleAction setActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public ScheduleAction setCron(Cron cron) {
        this.cron = cron;
        return this;
    }

    @Override
    public ScheduleAction setCronExpression(String expression) {
        this.cron = CronUtils.ofExpression(expression);
        return this;
    }

    @Override
    public ScheduleAction setMinute(String minute) {
        this.minute = minute;
        return this;
    }

    @Override
    public ScheduleAction setHour(String hour) {
        this.hour = hour;
        return this;
    }

    @Override
    public ScheduleAction setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    @Override
    public ScheduleAction setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
        return this;
    }
}
