/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * This class serves as a LoggerFactory for P4J internals.
 * <br>It will either return a Logger from a SLF4J implementation via {@link org.slf4j.LoggerFactory} if present,
 * or an instance of a custom {@link SimpleLogger} (From slf4j-simple).
 */
public class P4JLogger {
    // thanks jda

    /**
     * Marks whether or not a SLF4J <code>StaticLoggerBinder</code> (pre 1.8.x) or
     * <code>SLF4JServiceProvider</code> implementation (1.8.x+) was found. If false, P4J will use its fallback logger.
     * <br>This variable is initialized during static class initialization.
     */
    public static final boolean SLF4J_ENABLED;

    static {
        boolean tmp = false;

        try {
            Class.forName("org.slf4j.impl.StaticLoggerBinder");
            tmp = true;
        } catch (ClassNotFoundException eStatic) {
            // there was no static logger binder (SLF4J pre-1.8.x)

            try {
                Class<?> serviceProviderInterface = Class.forName("org.slf4j.spi.SLF4JServiceProvider");

                // check if there is a service implementation for the service, indicating a provider for SLF4J 1.8.x+ is installed
                tmp = ServiceLoader.load(serviceProviderInterface).iterator().hasNext();
            } catch (ClassNotFoundException eService) {
                // there was no service provider interface (SLF4J 1.8.x+)
                // let's print a warning of missing implementation
                LoggerFactory.getLogger(P4JLogger.class);
            }
        }

        SLF4J_ENABLED = tmp;
    }

    private static final Map<String, Logger> LOGS = new HashMap<>();

    /**
     * Will get the {@link org.slf4j.Logger} for the given Class
     * or create and cache a fallback logger if there is no SLF4J implementation present.
     * <p>
     * The fallback logger will be an instance of a slightly modified version of SLF4Js SimpleLogger.
     *
     * @param  clazz
     *         The class used for the Logger name
     *
     * @return Logger for given Class
     */
    public static Logger getLogger(Class<?> clazz) {
        synchronized (LOGS) {
            if (SLF4J_ENABLED)
                return LoggerFactory.getLogger(clazz);
            return LOGS.computeIfAbsent(clazz.getName(), (n) -> new SimpleLogger(clazz.getSimpleName()));
        }
    }
}