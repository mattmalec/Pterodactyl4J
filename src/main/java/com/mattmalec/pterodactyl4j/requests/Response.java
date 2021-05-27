package com.mattmalec.pterodactyl4j.requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Response {

    public static final int ERROR_CODE = -1;

    private final int code;
    private final long retryAfter;
    private final InputStream body;
    private final okhttp3.Response rawResponse;
    private Exception exception;


    public Response(okhttp3.Response response, Exception exception)
    {
        this(response, response != null ? response.code() : ERROR_CODE, -1);
        this.exception = exception;
    }

    public Response(okhttp3.Response response, int code, long retryAfter) {
        this.code = code;
        this.retryAfter = retryAfter;
        this.rawResponse = response;

        if(response == null) {
            this.body = null;
        } else {
            body = response.body().byteStream();
        }
    }

    public Response(long retryAfter) {
        this(null, 429, retryAfter);
    }

    public Response(okhttp3.Response response, long retryAfter) {
        this(response, response.code(), retryAfter);
    }

    public boolean isEmpty() {
        try {
            return body.read(new byte[0]) == -1;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public String getRawObject() {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        try {
            for (int length; (length = body.read(buffer)) != -1; ) {
                result.write(buffer, 0, length);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result.toString();
    }

    public long getRetryAfter() {
        return this.retryAfter;
    }

    public int getCode() {
        return this.code;
    }

    public okhttp3.Response getRawResponse() {
        return this.rawResponse;
    }

    public JSONObject getObject() {
        return new JSONObject(getRawObject());
    }

    public JSONArray getArray() {
        return new JSONArray(getRawObject());
    }

    public boolean isOk() {
        return this.code > 199 && this.code < 300;
    }

    public boolean isRateLimit() {
        return this.code == 429;
    }

    public Exception getException() {
        return this.exception;
    }
}
