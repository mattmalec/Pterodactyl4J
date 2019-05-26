package com.mattmalec.pterodactyl4j.exceptions;

import org.json.JSONObject;

public class MissingActionException extends RuntimeException {

    public MissingActionException(String message) {
        super(message);
    }

    public MissingActionException(String text, JSONObject json) {
        String message = text + "\n\n";
        for(Object o : json.getJSONArray("errors")) {
            JSONObject obj = new JSONObject(o.toString());
            message += "\t- " + obj.getString("detail") + " (Source: " + obj.getJSONObject("source").getString("field") + ")\n";
        }
        System.out.println(json.toString(4));
        throw new MissingActionException(message);
    }

    public MissingActionException(String message, Throwable cause) {
        super(message, cause);
    }
}