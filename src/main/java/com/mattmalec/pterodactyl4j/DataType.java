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

package com.mattmalec.pterodactyl4j;

/**
 * Represents a format of data such as a {@link DataType#GB}
 */
public enum DataType {

//	MB((byte) 0, 1,       "Megabyte"),
//	GB((byte) 1, 1024,    "Gigabyte"),
//	TB((byte) 2, 1048576, "Terabyte");
	/**
	 * DataType format for Bytes
	 */
	B ((byte) 0, 1,                  "Byte"),
	/**
	 * DataType format for Kilo-Bytes
	 */
	KB((byte) 1, 1024,               "Kilobyte"),
	/**
	 * DataType format for Mega-Bytes
	 */
	MB((byte) 2, 1048576,            "Megabyte"),
	/**
	 * DataType format for Giga-Bytes
	 */
	GB((byte) 3, 1_073_741_824,      "Gigabyte"),
	/**
	 * DataType format for Tera-Bytes
	 */
	TB((byte) 4, 1_099_511_627_776L, "Terabyte");

	private final byte identifier;
	private final long byteValue;
	private final String friendlyName;

	DataType(byte identifier, long byteValue, String friendlyName) {
		this.identifier = identifier;
		this.byteValue = byteValue;
		this.friendlyName = friendlyName;
	}

	public static DataType getByIdentifier(byte identifier) {
		for(DataType dataType : values()) {
			if(dataType.getIdentifier() == identifier) {
				return dataType;
			}
		}
		return null;
	}

	public byte getIdentifier() {
		return identifier;
	}

	public long getByteValue() {
		return byteValue;
	}

	public long getMbValue() {
		return byteValue / MB.getByteValue();
	}

	public String getFriendlyName() {
		return friendlyName;
	}

}
