package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.UtilizationState;

import java.util.List;

public interface Utilization {

	UtilizationState getState();
	String getCurrentMemory();
	String getMaxMemory();

	String getCurrentCPU();
	List<Double> getCurrentCores();
	String getMaxCPU();

	String getCurrentDisk();
	String getMaxDisk();

}
