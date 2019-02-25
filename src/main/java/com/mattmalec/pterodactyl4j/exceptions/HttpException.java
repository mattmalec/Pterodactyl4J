package com.mattmalec.pterodactyl4j.exceptions;

public class HttpException extends RuntimeException
{
    public HttpException(String message)
    {
        super(message);
    }

    public HttpException(String message, Throwable cause)
    {
        super(message, cause);
    }
}