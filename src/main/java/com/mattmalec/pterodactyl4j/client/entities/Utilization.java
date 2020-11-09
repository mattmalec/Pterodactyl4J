package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.UtilizationState;

public interface Utilization {

	UtilizationState getState();

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
		return String.format("%.2f %s", getNetworkIngress() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
	}

	long getNetworkEgress();
	default String getNetworkEgressFormatted(DataType dataType) {
		return String.format("%.2f %s", getNetworkEgress() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
	}

	boolean isSuspended();

}
