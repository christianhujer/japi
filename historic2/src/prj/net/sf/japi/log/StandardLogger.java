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

import java.util.ResourceBundle;

/**
 * Logger is a logger which logs to {@link System#err}.
 * @param <Level> the enumeration type of log levels to use for this LogEntry.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class StandardLogger<Level extends Enum<Level>> implements Logger<Level> {

    /** The minimum enabled level. */
    private Level enabledLevel; // TODO:2009-02-15:christianhujer:init with reasonable value

    /** The ResourceBundle to get messages from. */
    private final ResourceBundle bundle;

    /**
     * Create a Logger.
     * @param name Name of the logger, used for retrieving the resource bundle
     */
    public StandardLogger(final String name) {
        bundle = ResourceBundle.getBundle(name);
    }

    public void log(final Level level, final String key) {
        if (level.ordinal() >= enabledLevel.ordinal()) {
            logImpl(level, null, bundle.getString(key));
        }
    }

    public void log(final Level level, final String key, final Object... params) {
        if (level.ordinal() >= enabledLevel.ordinal()) {
            // TODO:2009-02-15:christianhujer:alternatively use MessageFormat
            logImpl(level, null, String.format(bundle.getString(key), params));
        }
    }

    public void log(final Level level, final Throwable t, final String key) {
        if (level.ordinal() >= enabledLevel.ordinal()) {
            logImpl(level, t, bundle.getString(key));
        }
    }

    public void log(final Level level, final Throwable t, final String key, final Object... params) {
        if (level.ordinal() >= enabledLevel.ordinal()) {
            // TODO:2009-02-15:christianhujer:alternatively use MessageFormat
            logImpl(level, t, String.format(bundle.getString(key), params));
        }
    }

    public void setLevel(final Level level) {
        enabledLevel = level;
    }

    public boolean isEnabled(final Level level) {
        return level.ordinal() >= enabledLevel.ordinal();
    }

    /**
     * Format and print a log message.
     * @param level   Log Level
     * @param t       Throwable to log (maybe null)
     * @param message Message to log
     */
    private void logImpl(final Level level, final Throwable t, final String message) {
        final LogEntry<Level> logEntry = new LogEntry<Level>(level, message, t);
        System.err.format("%s:%d: %s: %s %s", logEntry.getFilename(), logEntry.getLineNumber(), level, t, message);
    }

} // class Logger
