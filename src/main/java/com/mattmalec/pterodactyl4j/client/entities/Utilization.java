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

package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.UtilizationState;

import java.time.Duration;

/**
 * Represents a ClientServer's server utilization.
 */
public interface Utilization {
	/**
	 * Get the current state of the server
	 * @return the state of the server
	 */
	UtilizationState getState();
	/**
	 * Get duration a server has been up for
	 * @return the duration the server has been up for
	 */
	Duration getUptime();
	/**
	 * Get a nicely formatted uptime count
	 * @return A formatted string representation of the current uptime
	 */
	default String getUptimeFormatted() {
		long time = getUptime().toMillis() / 1000;

		double days = Math.floor((double) time / (24 * 60 * 60));
		double hours = Math.floor(Math.floor(time) / 60 / 60 % 24);
		double remainder = Math.floor((double) time - (hours * 60 * 60));
		double minutes = Math.floor(remainder / 60 % 60);
		double seconds = remainder % 60;

		if (days > 0)
			return String.format("%.0fd %.0fh %.0fm %.0fs", days, hours, minutes, seconds);
		else
			return String.format("%.0fh %.0fm %.0fs", hours, minutes, seconds);
	}

	/**
	 * Get the current ram usage
	 * @return the current ram usage in MB
	 */
	long getMemory();

	/**
	 * Get the current memory formatted in your desired data type such as {@link DataType#MB}
	 * @param dataType the size of the data to use such as a GB/MB etc
	 * @return the formatted string representation of the currently used memory
	 */
	default String getMemoryFormatted(DataType dataType) {
		return String.format("%.2f %s", getMemory() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
	}
	/**
	 * Get the current disk space usage
	 * @return the current disk space usage in MB
	 */
	long getDisk();
	/**
	 * Get the current disk usage formatted in your desired data type such as {@link DataType#MB}
	 * @param dataType the size of the data to use such as a GB/MB etc
	 * @return the formatted string representation of the currently used disk space
	 */
	default String getDiskFormatted(DataType dataType) {
		return String.format("%.2f %s", getDisk() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
	}
	/**
	 * Get the current cpu usage of the server
	 * @return the current ram usage in percents (Ex: 10.2)
	 */
	double getCPU();

	long getNetworkIngress();
	default String getNetworkIngressFormatted(DataType dataType) {
		return String.format("%.2f %s", getNetworkIngress() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
	}

	long getNetworkEgress();
	default String getNetworkEgressFormatted(DataType dataType) {
		return String.format("%.2f %s", getNetworkEgress() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
	}

	/**
	 * Checks if server is suspended
 	 * @return weather the server is suspended
	 */
	boolean isSuspended();

}
