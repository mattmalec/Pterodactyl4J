package com.mattmalec.pterodactyl4j.exceptions;

import com.mattmalec.pterodactyl4j.requests.Route;

public class RateLimitedException extends HttpException {

    public RateLimitedException(Route.CompiledRoute route, long retryAfter) {
        super(String.format("The request was rate limited. Retry-After: %d  Route: %s", retryAfter, route.getCompiledRoute()));
    }
}