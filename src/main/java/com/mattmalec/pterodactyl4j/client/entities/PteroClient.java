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

import com.mattmalec.pterodactyl4j.PowerAction;
import com.mattmalec.pterodactyl4j.PteroAction;

import java.util.List;

public interface PteroClient {

    PteroAction<Account> retrieveAccount();

    @Deprecated
    PteroAction<Void> setPower(ClientServer server, PowerAction powerAction);

    @Deprecated
    PteroAction<Void> sendCommand(ClientServer server, String command);

    @Deprecated
    PteroAction<Utilization> retrieveUtilization(ClientServer server);

    PteroAction<List<ClientServer>> retrieveServers();
    PteroAction<ClientServer> retrieveServerByIdentifier(String identifier);
    PteroAction<List<ClientServer>> retrieveServersByName(String name, boolean caseSensetive);

}
