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
	String getThreads();

}
