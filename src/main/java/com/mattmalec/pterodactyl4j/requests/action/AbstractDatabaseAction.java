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

package com.mattmalec.pterodactyl4j.requests.action;

import com.mattmalec.pterodactyl4j.entities.P4J;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Request;
import com.mattmalec.pterodactyl4j.requests.Response;
import com.mattmalec.pterodactyl4j.requests.Route;

import java.util.function.BiFunction;

public abstract class AbstractDatabaseAction<T> extends PteroActionImpl<T> {

    protected String name;
    protected String remote;

    public AbstractDatabaseAction(P4J api, Route.CompiledRoute route, BiFunction<Response, Request<T>, T> handler) {
        super(api, route, handler);
    }

}
