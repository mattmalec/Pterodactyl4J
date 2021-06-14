package com.mattmalec.pterodactyl4j.client.ws.events.connection;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class FailureEvent extends ConnectionEvent {

    private final Response response;
    private final Throwable throwable;

    public FailureEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, boolean connected, Response response, Throwable throwable) {
        super(api, server, manager, connected);
        this.response = response;
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public JSONObject getResponse() throws IOException {
        return new JSONObject(response.body().string());
    }

}
