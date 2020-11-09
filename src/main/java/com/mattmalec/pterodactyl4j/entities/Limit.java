package com.mattmalec.pterodactyl4j.entities;

public interface Limit {

	long getMemoryLong();
	default String getMemory() {
		return Long.toUnsignedString(getMemoryLong());
	}
	long getSwapLong();
	default String getSwap() {
		return Long.toUnsignedString(getSwapLong());
	}
	long getDiskLong();
	default String getDisk() {
		return Long.toUnsignedString(getDiskLong());
	}
	long getIOLong();
	default String getIO() {
		return Long.toUnsignedString(getIOLong());
	}
	long getCPULong();
	default String getCPU() {
		return Long.toUnsignedString(getCPULong());
	}

}
