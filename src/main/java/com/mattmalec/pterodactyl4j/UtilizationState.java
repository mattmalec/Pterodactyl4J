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
 * Represents a server's state.
 */
public enum UtilizationState {
    /**
     * Represents when the server is offline
     */
    OFFLINE,
    /**
     * Represents when the server is in the process of starting up
     */
    STARTING,
    /**
     * Represents when the server is online
     */
    RUNNING,
    /**
     * Represents when the server is shutting down
     */
    STOPPING;

    public static UtilizationState of(String s) {
        for (UtilizationState state : values()) {
            if (state.name().equalsIgnoreCase(s)) {
                return state;
            }
        }
        return UtilizationState.OFFLINE;
    }
}
