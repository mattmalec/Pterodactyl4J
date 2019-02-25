package com.mattmalec.pterodactyl4j.exceptions;

public class ServerException extends RuntimeException
{
    public ServerException(String message)
    {
        super(message);
    }

    public ServerException(String message, Throwable cause)
    {
        super(message, cause);
    }
}