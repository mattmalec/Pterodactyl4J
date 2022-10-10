/*
 *    Copyright 2021-2022 Matt Malec, and the Pterodactyl4J contributors
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

package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.managers.ServerStartupManager;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.requests.action.AbstractManagerBase;
import com.mattmalec.pterodactyl4j.utils.Checks;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class ServerStartupManagerImpl extends AbstractManagerBase implements ServerStartupManager {

	private final ApplicationServer server;

	private String startupCommand;
	private Map<String, String> environment;
	private long egg;
	private String image;
	private boolean skipScripts;

	public ServerStartupManagerImpl(ApplicationServer server, PteroApplicationImpl impl) {
		super(impl, Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()));
		this.server = server;
	}

	@Override
	public ServerStartupManager setStartupCommand(String command) {
		Checks.notNull(command, "Startup Command");

		set |= STARTUP_COMMAND;
		this.startupCommand = command;
		return this;
	}

	@Override
	public ServerStartupManager setEnvironment(Map<String, EnvironmentValue<?>> environment) {
		Checks.notNull(environment, "Environment");

		Map<String, EnvironmentValue<?>> env =
				new HashMap<>(server.getContainer().getEnvironment());
		env.putAll(environment);

		set |= ENVIRONMENT;
		this.environment = env.entrySet().stream().collect(EnvironmentValue.collector());
		return this;
	}

	@Override
	public ServerStartupManager setEgg(ApplicationEgg egg) {
		Checks.notNull(egg, "Egg");

		set |= EGG;
		this.egg = egg.getIdLong();
		return this;
	}

	@Override
	public ServerStartupManager setImage(String dockerImage) {
		Checks.notNull(dockerImage, "Docker Image");
		Checks.check(dockerImage.length() <= 191, "Docker image cannot be longer than 191 characters");

		set |= IMAGE;
		this.image = dockerImage;
		return this;
	}

	@Override
	public ServerStartupManager setSkipScripts(boolean skipScripts) {
		set |= SKIP_SCRIPTS;
		this.skipScripts = skipScripts;
		return this;
	}

	@Override
	protected RequestBody finalizeData() {

		JSONObject obj = new JSONObject()
				.put(
						"startup",
						shouldUpdate(STARTUP_COMMAND)
								? startupCommand
								: server.getContainer().getStartupCommand())
				.put(
						"environment",
						shouldUpdate(ENVIRONMENT)
								? environment
								: server.getContainer().getEnvironment().entrySet().stream()
										.collect(EnvironmentValue.collector()))
				.put("egg", shouldUpdate(EGG) ? egg : server.getEggIdLong())
				.put(
						"image",
						shouldUpdate(IMAGE) ? image : server.getContainer().getImage())

				// this won't do anything since the server is installed, but pterodactyl requires it so here we are
				.put("skip_scripts", !shouldUpdate(SKIP_SCRIPTS) || skipScripts);

		super.reset();
		return getRequestBody(obj);
	}
}
