package com.mattmalec.pterodactyl4j.exceptions;

import org.json.JSONObject;

public class IllegalActionException extends RuntimeException {

    public IllegalActionException(String message) {
        super(message);
    }

    public IllegalActionException(String text, JSONObject json) {
        String message = text + "\n\n";
        for(Object o : json.getJSONArray("errors")) {
            JSONObject obj = new JSONObject(o.toString());
            message += "    - " + obj.getString("detail") + "\n";
        }
        throw new IllegalActionException(message);
    }

    public IllegalActionException(String message, Throwable cause) {
        super(message, cause);
    }
}