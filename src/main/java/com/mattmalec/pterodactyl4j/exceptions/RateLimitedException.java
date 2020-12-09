package com.mattmalec.pterodactyl4j.exceptions;

import org.json.JSONObject;

public class RateLimitedException extends RuntimeException {

    public RateLimitedException(String text, JSONObject json) {
        StringBuilder message = new StringBuilder(text + "\n\n");
        for(Object o : json.getJSONArray("errors")) {
            JSONObject obj = new JSONObject(o.toString());
            message.append("\t- ").append(obj.getString("detail")).append("\n");
        }
        throw new RateLimitedException(message.toString());
    }

    public RateLimitedException(String message) {
        super(message);
    }

    public RateLimitedException(String message, Throwable cause)
    {
        super(message, cause);
    }
}