package com.mattmalec.pterodactyl4j.exceptions;

public class RateLimitedException extends RuntimeException
{
    public RateLimitedException(String message)
    {
        super(message);
    }

    public RateLimitedException(String message, Throwable cause)
    {
        super(message, cause);
    }
}