package com.mattmalec.pterodactyl4j.utils;

public class ExceptionUtils {

    public static <T extends Throwable> T appendCause(T throwable, Throwable cause) {
        Throwable t = throwable;
        while (t.getCause() != null)
            t = t.getCause();
        t.initCause(cause);
        return throwable;
    }

}
