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

/**
 * Represents Pterodactyl {@link SFTP} connection info
 */
public interface SFTP {
    /**
     * Gets the host for the sftp connection
     * @return host of the sftp connection
     */
    String getIP();
    /**
     * Gets the ip for the sftp connection
     * @return ip of the sftp connection
     */
    int getPort();

}
