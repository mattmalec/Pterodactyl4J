package com.mattmalec.pterodactyl4j.client.ws;

import org.json.JSONArray;
import org.json.JSONObject;

public final class WebSocketAction {

    public static final String AUTH = "auth";
    public static final String SET_STATE = "set state";
    public static final String SEND_LOGS = "send logs";
    public static final String SEND_COMMAND = "send command";
    public static final String SEND_STATS = "send stats";

    public static String create(String event, String argument) {
        JSONObject returnable = new JSONObject();
        JSONArray args = new JSONArray();
        args.put(argument);
        returnable.put("event", event)
                .put("args", args);
        return returnable.toString();
    }

}
