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

package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.Cron;
import com.mattmalec.pterodactyl4j.client.entities.Schedule;

public interface ScheduleAction extends PteroAction<Schedule> {

	ScheduleAction setName(String name);
	ScheduleAction setActive(boolean active);
	ScheduleAction setWhenServerIsOnline(boolean whenServerIsOnline);
	ScheduleAction setCron(Cron cron);
	ScheduleAction setCronExpression(String expression);
	ScheduleAction setMinute(String minute);
	ScheduleAction setHour(String hour);
	ScheduleAction setDayOfWeek(String dayOfWeek);
	ScheduleAction setMonth(String month);
	ScheduleAction setDayOfMonth(String dayOfMonth);

}
