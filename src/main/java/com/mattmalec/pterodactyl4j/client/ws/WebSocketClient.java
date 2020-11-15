package com.mattmalec.pterodactyl4j.client.ws;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.ws.handle.*;
import com.mattmalec.pterodactyl4j.requests.Route;
import okhttp3.*;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

public class WebSocketClient extends WebSocketListener {

    private OkHttpClient webSocketClient = new OkHttpClient();

    private WebSocket webSocket;
    private PteroClientImpl client;
    private ClientServer server;
    private boolean connected = false;
    private volatile Future<?> keepAliveThread;
    private Map<String, ClientSocketHandler> handlers = new HashMap<>();

    public WebSocketClient(PteroClientImpl client, ClientServer server) {
        this.client = client;
        this.server = server;
        setupHandlers();
    }

    private void setupHandlers() {
        handlers.put("auth success", new AuthSuccessHandler(client, server));
        handlers.put("status", new StatusHandler(client, server));
        handlers.put("console output", new ConsoleOuputHandler(client, server));
        handlers.put("stats", new StatsHandler(client, server));
        handlers.put("token expiring", new TokenExpiringHandler(client, server, this));
        handlers.put("token expired", new TokenExpiredHandler(client, server, this));
    }

    protected void connect() {
        if(connected)
            throw new IllegalStateException("Client already connected");
        Route.CompiledRoute route = Route.Client.GET_WEBSOCKET.compile(server.getIdentifier());
        JSONObject json = client.getRequester().request(route).toJSONObject();
        JSONObject data = json.getJSONObject("data");
        String url = data.getString("socket");

        Request req = new Request.Builder().url(url).build();
        webSocketClient.newWebSocket(req, this);
    }

    protected boolean send(String message) {
        return webSocket.send(message);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public void sendAuthenticate(Optional<String> token) {
        if(!connected)
            throw new IllegalStateException("Client isn't connected to server websocket");
        String t = token.orElseGet(() -> {
            Route.CompiledRoute route = Route.Client.GET_WEBSOCKET.compile(server.getIdentifier());
            JSONObject json = client.getRequester().request(route).toJSONObject();
            JSONObject data = json.getJSONObject("data");
            return data.getString("token");
        });

        webSocket.send(WebSocketAction.create(WebSocketAction.AUTH, t));

    }

    private void onEvent(JSONObject json) {
        handleEvent(json.getString("event"), json.getJSONArray("args").optString(0));
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
        sendAuthenticate(Optional.empty());
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        onEvent(new JSONObject(text));
    }


    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {

    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {

        connected = false;

    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {

    }
}
