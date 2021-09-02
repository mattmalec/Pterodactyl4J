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

package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.Cron;
import org.json.JSONObject;

public class CronImpl implements Cron {

	private final JSONObject json;

	public CronImpl(JSONObject json) {
		this.json = json.getJSONObject("cron");
	}

	@Override
	public String getDayOfWeek() {
		return json.getString("day_of_week");
	}

	@Override
	public String getDayOfMonth() {
		return json.getString("day_of_month");
	}

	@Override
	public String getMonth() {
		return json.getString("month");
	}

	@Override
	public String getHour() {
		return json.getString("hour");
	}

	@Override
	public String getMinute() {
		return json.getString("minute");
	}

	@Override
	public String getExpression() {
		return String.format("%s %s %s %s", getMinute(), getHour(), getDayOfMonth(), getDayOfWeek());
	}

	public static Cron ofExpression(String expression) {
		String[] exp = expression.split("\\s+");
		if(exp.length != 5) throw new IllegalArgumentException("P4J Cron Expression must have 5 elements (minute, hour, day of month, month, day of week)");
		JSONObject cron = new JSONObject();
		cron.put("minute", exp[0])
				.put("hour", exp[1])
				.put("day_of_month", exp[2])
				.put("month", exp[3])
				.put("day_of_week", exp[4]);
		return new CronImpl(new JSONObject().put("cron", cron));
	}
}
