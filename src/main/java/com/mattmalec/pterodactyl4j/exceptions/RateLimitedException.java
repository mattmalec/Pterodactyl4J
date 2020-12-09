package com.mattmalec.pterodactyl4j.exceptions;

import org.json.JSONObject;

public class RateLimitedException extends HttpException {

    public RateLimitedException(String text, JSONObject json) {
        super(text, json);
    }

    public RateLimitedException(String message) {
        super(message);
    }

    public RateLimitedException(String message, Throwable cause)
    {
        super(message, cause);
    }
}