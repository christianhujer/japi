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

import net.sf.japi.log.def.Level;

/**
 * Provides simple static access to logging using the configured default logger.
 * If there is no configured logger, a default configuration will be used.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class SimpleLogger {

    /** The default logger. */
    private static final Logger<Level> DEFAULT_LOGGER = LoggerFactory.INSTANCE.getLogger(SimpleLogger.class);

    /** Utility class - don't instanciate. */
    private SimpleLogger() {
    }

    /** Creates a log entry.
     * @param level Level for the log entry
     * @param key  Key for retrieving the message from the bundle
     * @see Logger#log(Enum, String)
     */
    public static void log(final Level level, final String key) {
        DEFAULT_LOGGER.log(level, key);
    }

    /** Creates a log entry.
     * @param level  Level for the log entry
     * @param key    Key for retrieving the message from the bundle
     * @param params Values to use during message formatting
     * @see Logger#log(Enum, String, Object...)
     */
    public static void log(final Level level, final String key, final Object... params) {
        DEFAULT_LOGGER.log(level, key, params);
    }

    /** Creates a log entry.
     * @param level  Level for the log entry
     * @param key    Key for retrieving the message from the bundle
     * @param t      Throwable to log
     * @see Logger#log(Enum, Throwable, String)
     */
    public static void log(final Level level, final Throwable t, final String key) {
        DEFAULT_LOGGER.log(level, t, key);
    }

    /** Creates a log entry.
     * @param level  Level for the log entry
     * @param key    Key for retrieving the message from the bundle
     * @param t      Throwable to log
     * @param params Values to use during message formatting
     * @see Logger#log(Enum, Throwable, String, Object...)
     */
    public static void log(final Level level, final Throwable t, final String key, final Object... params) {
        DEFAULT_LOGGER.log(level, t, key, params);
    }

} // class SimpleLogger
