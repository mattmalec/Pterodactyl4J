package com.mattmalec.pterodactyl4j.exceptions;

import org.json.JSONObject;

public class MissingActionException extends RuntimeException {

    public MissingActionException(String message) {
        super(message);
    }

    public MissingActionException(String text, JSONObject json) {
        StringBuilder message = new StringBuilder(text + "\n\n");
        for(Object o : json.getJSONArray("errors")) {
            JSONObject obj = new JSONObject(o.toString());
            message.append("\t- ").append(obj.getString("detail")).append(" (Source: ").append(obj.getJSONObject("meta").getString("source_field")).append(")\n");
        }
        throw new MissingActionException(message.toString());
    }

    public MissingActionException(String message, Throwable cause) {
        super(message, cause);
    }
}