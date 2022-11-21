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

package com.mattmalec.pterodactyl4j.client.ws;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.connection.ConnectedEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.connection.DisconnectedEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.connection.DisconnectingEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.connection.FailureEvent;
import com.mattmalec.pterodactyl4j.client.ws.handle.*;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.P4JLogger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;

public class WebSocketClient extends WebSocketListener implements Runnable {

	private final OkHttpClient webSocketClient;

	public static final Logger WEBSOCKET_LOG = P4JLogger.getLogger(WebSocketClient.class);

	private WebSocket webSocket;
	private final PteroClientImpl client;
	private final ClientServer server;
	private final WebSocketManager manager;
	private final boolean freshServer;
	private boolean connected = false;
	private final Map<String, ClientSocketHandler> handlers = new HashMap<>();

	public WebSocketClient(PteroClientImpl client, ClientServer server, boolean freshServer, WebSocketManager manager) {
		this.client = client;
		this.server = server;
		this.freshServer = freshServer;
		this.manager = manager;
		this.webSocketClient = client.getP4J().getWebSocketClient();
		setupHandlers();
	}

	@Override
	public void run() {
		connect();
	}

	private void setupHandlers() {
		handlers.put("auth success", new AuthSuccessHandler(client, server, manager));
		handlers.put("status", new StatusHandler(client, server, manager));
		handlers.put("console output", new ConsoleOuputHandler(client, server, manager));
		handlers.put("daemon message", new DaemonMessageHandler(client, server, manager));
		handlers.put("install started", new InstallStartedHandler(client, server, manager));
		handlers.put("install output", new InstallOuputHandler(client, server, manager));
		handlers.put("install completed", new InstallCompletedHandler(client, server, manager));
		handlers.put("stats", new StatsHandler(client, server, manager));
		handlers.put("transfer logs", new TransferLogHandler(client, server, manager));
		handlers.put("transfer status", new TransferStatusHandler(client, server, manager));
		handlers.put("backup completed", new BackupCompletedHandler(client, server, manager));
		handlers.put("token expiring", new TokenExpiringHandler(client, server, manager, this));
		handlers.put("token expired", new TokenExpiredHandler(client, server, manager, this));
		handlers.put("daemon error", new DaemonErrorHandler(client, server, manager));
		handlers.put("jwt error", new JWTErrorHandler(client, server, manager, this));
	}

	public void connect() {
		if (connected) throw new IllegalStateException("Client already connected");
		String url = new PteroActionImpl<String>(
						client.getP4J(),
						Route.Client.GET_WEBSOCKET.compile(server.getIdentifier()),
						(response, request) ->
								response.getObject().getJSONObject("data").getString("socket"))
				.execute();

		Request req = new Request.Builder()
				.url(url)
				.header("User-Agent", client.getP4J().getUserAgent())
				.header("Origin", client.getP4J().getApplicationUrl())
				.build();

		webSocketClient.newWebSocket(req, this);
	}

	public void shutdown() {
		if (!connected) throw new IllegalStateException("Client isn't connected to server websocket");

		WEBSOCKET_LOG.info(String.format("Shutting down websocket for server %s", server.getIdentifier()));

		webSocket.close(1000, "Client shutting down");
	}

	public boolean send(String message) {
		if (!connected) throw new IllegalStateException("Client isn't connected to server websocket");
		return webSocket.send(message);
	}

	public void sendAuthenticate() {
		sendAuthenticate(null);
	}

	public void sendAuthenticate(String token) {
		if (!connected) throw new IllegalStateException("Client isn't connected to server websocket");
		String t = Optional.ofNullable(token).orElseGet(() -> new PteroActionImpl<String>(
						client.getP4J(),
						Route.Client.GET_WEBSOCKET.compile(server.getIdentifier()),
						(response, request) ->
								response.getObject().getJSONObject("data").getString("token"))
				.execute());
		send(WebSocketAction.create(WebSocketAction.AUTH, t));
	}

	private void onEvent(JSONObject json) {
		if (json.has("args"))
			handleEvent(json.getString("event"), json.getJSONArray("args").optString(0));
		else handleEvent(json.getString("event"), null);
	}

	private void handleEvent(String event, String args) {
		ClientSocketHandler handler = getHandler(event);
		if (handler == null) {
			return; // Don't even bother trying to handle an event which we don't know
		}

		if (freshServer)
			client.retrieveServerByIdentifier(server.getIdentifier())
					.executeAsync(
							freshServer -> handler.setServer(freshServer).handleInternally(args),
							__ -> handler.handleInternally(args));
		else handler.handleInternally(args);
	}

	@SuppressWarnings("unchecked")
	public <T extends ClientSocketHandler> T getHandler(String type) {
		try {
			return (T) handlers.get(type);
		} catch (ClassCastException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean isConnected() {
		return connected;
	}

	@Override
	public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
		connected = true;
		this.webSocket = webSocket;
		WEBSOCKET_LOG.info("Connected to websocket for server {}", server.getIdentifier());
		manager.getEventManager().handle(new ConnectedEvent(client, server, manager, connected));
		sendAuthenticate();
	}

	@Override
	public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
		onEvent(new JSONObject(text));
	}

	@Override
	public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
		connected = false;
		manager.getEventManager().handle(new DisconnectingEvent(client, server, manager, connected, code));
	}

	@Override
	public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
		connected = false;
		manager.getEventManager().handle(new DisconnectedEvent(client, server, manager, connected, code));
	}

	@Override
	public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
		connected = false;
		WEBSOCKET_LOG.error(
				String.format("There was an error in the websocket for server %s", server.getIdentifier()), t);
		manager.getEventManager().handle(new FailureEvent(client, server, manager, connected, response, t));
	}
}
