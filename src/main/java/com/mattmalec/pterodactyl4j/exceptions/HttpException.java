package com.mattmalec.pterodactyl4j.exceptions;

import org.json.JSONObject;

public class HttpException extends RuntimeException {
    public HttpException(String message)
    {
        super(message);
    }

    private static String formatMessage(String text, JSONObject json) {
        StringBuilder message = new StringBuilder(text + "\n\n");
        for(Object o : json.getJSONArray("errors")) {
            JSONObject obj = new JSONObject(o.toString());
            message.append("\t- ").append(obj.getString("detail")).append("\n");
        }
        return message.toString();
    }

    public HttpException(String text, JSONObject json) {
        super(formatMessage(text, json));
    }
}