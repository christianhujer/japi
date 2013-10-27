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

/**
 * A Logger serves as interface for creating log entries.
 * @param <Level> the enumeration type of log levels to use for this LogEntry.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface Logger<Level extends Enum<Level>> {

    /**
     * Creates a log entry.
     * @param level Level for the log entry
     * @param key   Key for retrieving the message from the bundle
     */
    void log(Level level, String key);

    /**
     * Creates a log entry.
     * @param level  Level for the log entry
     * @param key    Key for retrieving the message from the bundle
     * @param params Values to use during message formatting
     */
    void log(Level level, String key, Object... params);

    /**
     * Creates a log entry.
     * @param level  Level for the log entry
     * @param key    Key for retrieving the message from the bundle
     * @param t      Throwable to log
     */
    void log(Level level, Throwable t, String key);

    /**
     * Creates a log entry.
     * @param level  Level for the log entry
     * @param key    Key for retrieving the message from the bundle
     * @param t      Throwable to log
     * @param params Values to use during message formatting
     */
    void log(Level level, Throwable t, String key, Object... params);

    /**
     * Set the level at which to log.
     * @param level level at which to log
     */
    void setLevel(Level level);

    /**
     * Ask whether the specified level is enabled for logging.
     * @param level Level to query
     * @return <code>true</code> if logging for <var>level</var> is enabled, otherwise <code>false</code>
     */
    boolean isEnabled(Level level);

} // class Logger
