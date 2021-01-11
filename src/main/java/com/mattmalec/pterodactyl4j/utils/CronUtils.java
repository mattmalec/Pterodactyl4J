package com.mattmalec.pterodactyl4j.utils;

import com.mattmalec.pterodactyl4j.client.entities.Cron;
import com.mattmalec.pterodactyl4j.client.entities.impl.CronImpl;

public class CronUtils {

	public static Cron ofExpression(String expression) {
		return CronImpl.ofExpression(expression);
	}

}
