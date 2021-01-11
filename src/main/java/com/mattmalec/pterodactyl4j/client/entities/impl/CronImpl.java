package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.Cron;
import org.json.JSONObject;

public class CronImpl implements Cron {

	private JSONObject json;

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
	public String getHour() {
		return json.getString("hour");
	}

	@Override
	public String getMinute() {
		return json.getString("minute");
	}

	@Override
	public String getExpression() {
		return String.format("%s %s %s %s", getMinute(), getHour(), getDayOfWeek(), getDayOfMonth());
	}
}
