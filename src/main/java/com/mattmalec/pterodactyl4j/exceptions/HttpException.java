package com.mattmalec.pterodactyl4j.exceptions;

import org.json.JSONObject;

public class HttpException extends RuntimeException
{
    public HttpException(String message)
    {
        super(message);
    }

    public HttpException(String text, JSONObject json) {
        String message = text + "\n\n";
        for(Object o : json.getJSONArray("errors")) {
            JSONObject obj = new JSONObject(o.toString());
            message += "\t- " + obj.getString("detail") + "\n";
        }
        throw new HttpException(message);
    }

    public HttpException(String message, Throwable cause)
    {
        super(message, cause);
    }
}