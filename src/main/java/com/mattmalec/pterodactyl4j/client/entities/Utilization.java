/*
 *    Copyright 2021-2022 Matt Malec, and the Pterodactyl4J contributors
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

public interface Utilization {

	UtilizationState getState();

	Duration getUptime();

	default String getUptimeFormatted() {
		long time = getUptime().toMillis() / 1000;

		double days = Math.floor((double) time / (24 * 60 * 60));
		double hours = Math.floor(Math.floor(time) / 60 / 60 % 24);
		double remainder = Math.floor((double) time - (hours * 60 * 60));
		double minutes = Math.floor(remainder / 60 % 60);
		double seconds = remainder % 60;

		if (days > 0) return String.format("%.0fd %.0fh %.0fm %.0fs", days, hours, minutes, seconds);
		else return String.format("%.0fh %.0fm %.0fs", hours, minutes, seconds);
	}

	long getMemory();

	default String getMemoryFormatted(DataType dataType) {
		return String.format("%.2f %s", getMemory() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
	}

	long getDisk();

	default String getDiskFormatted(DataType dataType) {
		return String.format("%.2f %s", getDisk() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
	}

	double getCPU();

	long getNetworkIngress();

	default String getNetworkIngressFormatted(DataType dataType) {
		return String.format(
				"%.2f %s", getNetworkIngress() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
	}

	long getNetworkEgress();

	default String getNetworkEgressFormatted(DataType dataType) {
		return String.format(
				"%.2f %s", getNetworkEgress() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
	}

	boolean isSuspended();
}
