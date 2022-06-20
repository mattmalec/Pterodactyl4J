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

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.Util;
import org.slf4j.spi.LocationAwareLogger;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;

class SimpleLogger extends MarkerIgnoringBase {

    private static final long serialVersionUID = -4634988394764007409L;

    private static boolean INITIALIZED = false;

    private static final int LOG_LEVEL_TRACE = LocationAwareLogger.TRACE_INT;
    private static final int LOG_LEVEL_DEBUG = LocationAwareLogger.DEBUG_INT;
    private static final int LOG_LEVEL_INFO = LocationAwareLogger.INFO_INT;
    private static final int LOG_LEVEL_WARN = LocationAwareLogger.WARN_INT;
    private static final int LOG_LEVEL_ERROR = LocationAwareLogger.ERROR_INT;

    private static final int DEFAULT_LOG_LEVEL = LOG_LEVEL_INFO;
    private static final boolean SHOW_DATE_TIME = false;
    private static final DateFormat DATE_FORMATTER = DateFormat.getTimeInstance();
    private static final boolean SHOW_THREAD_NAME = true;
    private static final boolean SHOW_LOG_NAME = true;
    private static final boolean SHOW_SHORT_LOG_NAME = false;
    private static final PrintStream TARGET_STREAM = System.err;

    static void init() {
        INITIALIZED = true;
    }

    protected int currentLogLevel = DEFAULT_LOG_LEVEL;
    private transient String shortLogName = null;

    SimpleLogger(String name) {
        if (!INITIALIZED)
            init();

        this.name = name;
    }

    private void log(int level, String message, Throwable t) {
        if (!isLevelEnabled(level))
            return;

        StringBuilder buf = new StringBuilder(32);

        // Append date-time if so configured
        if (SHOW_DATE_TIME) {
            buf.append(getFormattedDate());
            buf.append(' ');
        }

        // Append current thread name if so configured
        if (SHOW_THREAD_NAME) {
            buf.append('[');
            buf.append(Thread.currentThread().getName());
            buf.append("] ");
        }

        buf.append('[');

        // Append a readable representation of the log level
        switch (level) {
            case LOG_LEVEL_TRACE:
                buf.append("TRACE");
                break;
            case LOG_LEVEL_DEBUG:
                buf.append("DEBUG");
                break;
            case LOG_LEVEL_INFO:
                buf.append("INFO");
                break;
            case LOG_LEVEL_WARN:
                buf.append("WARN");
                break;
            case LOG_LEVEL_ERROR:
                buf.append("ERROR");
                break;
        }

        buf.append("] ");

        // Append the name of the log instance if so configured
        if (SHOW_SHORT_LOG_NAME) {
            if (shortLogName == null)
                shortLogName = computeShortName();
            buf.append(shortLogName).append(" - ");
        } else if (SHOW_LOG_NAME)
            buf.append(name).append(" - ");

        // Append the message
        buf.append(message);

        write(buf, t);
    }

    void write(StringBuilder buf, Throwable t) {
        TARGET_STREAM.println(buf.toString());
        if (t != null)
            t.printStackTrace(TARGET_STREAM);

        TARGET_STREAM.flush();
    }

    private String getFormattedDate() {
        Date now = new Date();
        String dateText;
        synchronized (DATE_FORMATTER) {
            dateText = DATE_FORMATTER.format(now);
        }
        return dateText;
    }

    private String computeShortName() {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    private void formatAndLog(int level, String format, Object arg1, Object arg2) {
        if (!isLevelEnabled(level))
            return;

        FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
        log(level, tp.getMessage(), tp.getThrowable());
    }

    private void formatAndLog(int level, String format, Object... arguments) {
        if (!isLevelEnabled(level))
            return;

        FormattingTuple tp = MessageFormatter.arrayFormat(format, arguments);
        log(level, tp.getMessage(), tp.getThrowable());
    }

    protected boolean isLevelEnabled(int logLevel) {
        // log level are numerically ordered so can use simple numeric
        // comparison
        return logLevel >= currentLogLevel;
    }

    public boolean isTraceEnabled() {
        return isLevelEnabled(LOG_LEVEL_TRACE);
    }

    public void trace(String msg) {
        log(LOG_LEVEL_TRACE, msg, null);
    }

    public void trace(String format, Object param1) {
        formatAndLog(LOG_LEVEL_TRACE, format, param1, null);
    }

    public void trace(String format, Object param1, Object param2) {
        formatAndLog(LOG_LEVEL_TRACE, format, param1, param2);
    }

    public void trace(String format, Object... argArray) {
        formatAndLog(LOG_LEVEL_TRACE, format, argArray);
    }

    public void trace(String msg, Throwable t) {
        log(LOG_LEVEL_TRACE, msg, t);
    }

    public boolean isDebugEnabled() {
        return isLevelEnabled(LOG_LEVEL_DEBUG);
    }

    public void debug(String msg) {
        log(LOG_LEVEL_DEBUG, msg, null);
    }

    public void debug(String format, Object param1) {
        formatAndLog(LOG_LEVEL_DEBUG, format, param1, null);
    }

    public void debug(String format, Object param1, Object param2) {
        formatAndLog(LOG_LEVEL_DEBUG, format, param1, param2);
    }

    public void debug(String format, Object... argArray) {
        formatAndLog(LOG_LEVEL_DEBUG, format, argArray);
    }

    public void debug(String msg, Throwable t) {
        log(LOG_LEVEL_DEBUG, msg, t);
    }

    public boolean isInfoEnabled() {
        return isLevelEnabled(LOG_LEVEL_INFO);
    }

    public void info(String msg) {
        log(LOG_LEVEL_INFO, msg, null);
    }

    public void info(String format, Object arg) {
        formatAndLog(LOG_LEVEL_INFO, format, arg, null);
    }

    public void info(String format, Object arg1, Object arg2) {
        formatAndLog(LOG_LEVEL_INFO, format, arg1, arg2);
    }

    public void info(String format, Object... argArray) {
        formatAndLog(LOG_LEVEL_INFO, format, argArray);
    }

    public void info(String msg, Throwable t) {
        log(LOG_LEVEL_INFO, msg, t);
    }

    public boolean isWarnEnabled() {
        return isLevelEnabled(LOG_LEVEL_WARN);
    }

    public void warn(String msg) {
        log(LOG_LEVEL_WARN, msg, null);
    }

    public void warn(String format, Object arg) {
        formatAndLog(LOG_LEVEL_WARN, format, arg, null);
    }

    public void warn(String format, Object arg1, Object arg2) {
        formatAndLog(LOG_LEVEL_WARN, format, arg1, arg2);
    }

    public void warn(String format, Object... argArray) {
        formatAndLog(LOG_LEVEL_WARN, format, argArray);
    }

    public void warn(String msg, Throwable t) {
        log(LOG_LEVEL_WARN, msg, t);
    }

    public boolean isErrorEnabled() {
        return isLevelEnabled(LOG_LEVEL_ERROR);
    }

    public void error(String msg) {
        log(LOG_LEVEL_ERROR, msg, null);
    }

    public void error(String format, Object arg) {
        formatAndLog(LOG_LEVEL_ERROR, format, arg, null);
    }

    public void error(String format, Object arg1, Object arg2) {
        formatAndLog(LOG_LEVEL_ERROR, format, arg1, arg2);
    }

    public void error(String format, Object... argArray) {
        formatAndLog(LOG_LEVEL_ERROR, format, argArray);
    }

    public void error(String msg, Throwable t) {
        log(LOG_LEVEL_ERROR, msg, t);
    }
}