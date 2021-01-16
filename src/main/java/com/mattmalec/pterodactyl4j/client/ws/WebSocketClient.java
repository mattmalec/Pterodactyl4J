package com.mattmalec.pterodactyl4j.client.ws;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.connection.ConnectedEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.connection.DisconnectedEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.connection.DisconnectingEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.connection.FailureEvent;
import com.mattmalec.pterodactyl4j.client.ws.handle.*;
import com.mattmalec.pterodactyl4j.requests.Route;
import okhttp3.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WebSocketClient extends WebSocketListener implements Runnable {

    private OkHttpClient webSocketClient = new OkHttpClient();

    public static final Logger WEBSOCKET_LOG = LoggerFactory.getLogger(WebSocketClient.class);

    private WebSocket webSocket;
    private PteroClientImpl client;
    private ClientServer server;
    private WebSocketManager manager;
    private boolean connected = false;
    private Map<String, ClientSocketHandler> handlers = new HashMap<>();

    public WebSocketClient(PteroClientImpl client, ClientServer server, WebSocketManager manager) {
        this.client = client;
        this.server = server;
        this.manager = manager;
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
        handlers.put("install output", new InstallOuputHandler(client, server, manager));
        handlers.put("stats", new StatsHandler(client, server, manager));
        handlers.put("token expiring", new TokenExpiringHandler(client, server, manager, this));
        handlers.put("token expired", new TokenExpiredHandler(client, server, manager, this));
        handlers.put("daemon error", new DaemonErrorHandler(client, server, manager));
        handlers.put("jwt error", new JWTErrorHandler(client, server, manager, this));
    }

    public void connect() {
        if(connected)
            throw new IllegalStateException("Client already connected");
        Route.CompiledRoute route = Route.Client.GET_WEBSOCKET.compile(server.getIdentifier());
        JSONObject json = client.getRequester().request(route).toJSONObject();
        JSONObject data = json.getJSONObject("data");
        String url = data.getString("socket");

        Request req = new Request.Builder().url(url).build();
        webSocketClient.newWebSocket(req, this);
    }

    public void shutdown() {
        if (!connected)
            throw new IllegalStateException("Client isn't connected to server websocket");

        WEBSOCKET_LOG.info(String.format("Shutting down websocket for server %s", server.getIdentifier()));

        webSocket.close(1000, "Client shutting down");
    }

    public boolean send(String message) {
        if(!connected)
            throw new IllegalStateException("Client isn't connected to server websocket");
        return webSocket.send(message);
    }

    public void sendAuthenticate() {
        sendAuthenticate(null);
    }

    public void sendAuthenticate(String token) {
        if(!connected)
            throw new IllegalStateException("Client isn't connected to server websocket");
        String t = Optional.ofNullable(token).orElseGet(() -> {
            Route.CompiledRoute route = Route.Client.GET_WEBSOCKET.compile(server.getIdentifier());
            JSONObject json = client.getRequester().request(route).toJSONObject();
            JSONObject data = json.getJSONObject("data");
            return data.getString("token");
        });
        send(WebSocketAction.create(WebSocketAction.AUTH, t));
    }

    private void onEvent(JSONObject json) {
        if (json.has("args"))
            handleEvent(json.getString("event"), json.getJSONArray("args").optString(0));
        else handleEvent(json.getString("event"), null);
    }

    private void handleEvent(String event, String args) {
        ClientSocketHandler handler = getHandler(event);
        handler.handleInternally(args);
    }

    @SuppressWarnings("unchecked")
    public <T extends ClientSocketHandler> T getHandler(String type) {
        try {
            return (T) handlers.get(type);
        }
        catch (ClassCastException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        connected = true;
        this.webSocket = webSocket;
        WEBSOCKET_LOG.info(String.format("Connected to websocket for server %s", server.getIdentifier()));
        manager.getEventManager().handle(new ConnectedEvent(client, server, manager, connected));
        sendAuthenticate();
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        onEvent(new JSONObject(text));
    }


    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        connected = false;
        manager.getEventManager().handle(new DisconnectingEvent(client, server, manager, connected, code));

    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        connected = false;
        manager.getEventManager().handle(new DisconnectedEvent(client, server, manager, connected, code));
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        connected = false;
        WEBSOCKET_LOG.error(String.format("There was an error in the websocket for server %s", server.getIdentifier()), t);
        manager.getEventManager().handle(new FailureEvent(client, server, manager, connected, t));
    }
}
