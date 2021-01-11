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
		return String.format("%s %s %s %s", getMinute(), getHour(), getDayOfMonth(), getDayOfWeek());
	}

	public static Cron ofExpression(String expression) {
		String[] exp = expression.split("\\s+");
		if(exp.length != 4) throw new IllegalArgumentException("P4J Cron Expression must have 4 elements (minute, hour, day of month, day of week)");
		JSONObject cron = new JSONObject();
		cron.put("minute", exp[0])
				.put("hour", exp[1])
				.put("day_of_month", exp[2])
				.put("day_of_week", exp[3]);
		return new CronImpl(new JSONObject().put("cron", cron));
	}
}
