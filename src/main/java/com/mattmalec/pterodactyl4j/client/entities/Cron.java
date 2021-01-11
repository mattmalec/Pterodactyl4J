package com.mattmalec.pterodactyl4j.client.entities;

public interface Cron {

	String getDayOfWeek();
	String getDayOfMonth();
	String getHour();
	String getMinute();
	String getExpression();

}
