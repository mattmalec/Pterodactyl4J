package com.mattmalec.pterodactyl4j.entities;

public interface Limit {

	String getMemory();
	default long getMemoryLong() { return Long.parseLong(getMemory()); }
	String getSwap();
	default long getSwapLong() { return Long.parseLong(getSwap()); }
	String getDisk();
	default long getDiskLong() { return Long.parseLong(getDisk()); }
	String getIO();
	default long getIOLong() { return Long.parseLong(getIO()); }
	String getCPU();
	default long getCPULong() { return Long.parseLong(getCPU()); }

}
