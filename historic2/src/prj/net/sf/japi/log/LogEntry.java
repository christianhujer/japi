/*
 * Copyright (C) 2009  Christian Hujer.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.sf.japi.log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A LogEntry is a single log element.
 * @param <Level> the enumeration type of log levels to use for this LogEntry.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class LogEntry<Level extends Enum<Level>> {

    /** The log message. */
    @NotNull private final String message;

    /** The log level. */
    @NotNull private final Level level;

    /** The throwable. */
    @Nullable private final Throwable cause;

    /** The timestamp. */
    private final long timestamp;

    /** The StackTraceElement of the invoking method. */
    @NotNull private final StackTraceElement invokingMethod;

    /** The filename. */
    @Nullable private final String filename;

    /** The line number. */
    private final int lineNumber;

    /** The class name. */
    @NotNull private final String className;

    /** The method name. */
    @NotNull private final String methodName;

    /**
     * Create a LogEntry.
     * @param level Level
     * @param message Message
     * @param cause Throwable
     */
    public LogEntry(@NotNull final Level level, @NotNull final String message, @Nullable final Throwable cause) {
        timestamp = System.currentTimeMillis();
        this.level = level;
        this.message = message;
        this.cause = cause;
        invokingMethod = findInvokingMethod();
        filename   = invokingMethod.getFileName();
        lineNumber = invokingMethod.getLineNumber();
        className  = invokingMethod.getClassName();
        methodName = invokingMethod.getMethodName();
    }

    /**
     * Finds the invoking method.
     * @return invoking method
     */
    private StackTraceElement findInvokingMethod() {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int i = 0;
        try {
            while (!stackTrace[i].getClassName().startsWith("net.sf.japi.log.")) {
                i++;
            }
            while (stackTrace[i].getClassName().startsWith("net.sf.japi.log.")) {
                i++;
            }
            return stackTrace[i];
        } catch (final ArrayIndexOutOfBoundsException ignore) {
            assert false;
            throw new Error("Couldn'cause find StackTraceElement.");
        }
    }

    /**
     * Get the log message.
     * @return log message
     */
    @NotNull public String getMessage() {
        return message;
    }

    /**
     * Get the log level.
     * @return log level
     */
    @NotNull public Level getLevel() {
        return level;
    }

    /**
     * Get the cause of this log entry.
     * @return cause or <code>null</code> if no cause
     */
    @Nullable public Throwable getCause() {
        return cause;
    }

    /**
     * Get the time stamp of this log entry.
     * @return time stamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Get the invoking method.
     * @return invoking method
     */
    @NotNull public StackTraceElement getInvokingMethod() {
        return invokingMethod;
    }

    /**
     * Get the source filename that caused this log entry.
     * @return source filename or <code>null</code> if unavailable
     */
    @Nullable public String getFilename() {
        return filename;
    }

    /**
     * Get the source linenumber that caused this log entry.
     * @return source linenumber or a negative number if unavailable
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Get the class name that caused this log entry.
     * @return class name
     */
    @NotNull public String getClassName() {
        return className;
    }

    /**
     * Get the method name that caused this log entry.
     * @return method name
     */
    @NotNull public String getMethodName() {
        return methodName;
    }

} // class LogEntry
