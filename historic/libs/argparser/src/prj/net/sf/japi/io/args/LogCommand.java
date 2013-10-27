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

package net.sf.japi.io.args;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Subclass of BasicCommand that provides logging through {@link java.util.logging}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public abstract class LogCommand extends BasicCommand {

    /** The Logger to use. */
    private final Logger log;

    /** Create a LogCommand with the specified log.
     * @param log Logger to use
     */
    protected LogCommand(@NotNull final Logger log) {
        this.log = log;
    }

    /** Create a LogCommand which automatically creates a log that matches the class name. */
    protected LogCommand() {
        log = Logger.getLogger(getClass().getName(), getClass().getName());
    }

    /** Sets the log level to log.
     * @param level LogLevel to log.
     */
    @Option({"l", "loglevel"})
    public void setLevel(@NotNull final Level level) {
        try {
            LogManager.getLogManager().readConfiguration(new ByteArrayInputStream((".level=" + level + "\nhandlers=java.util.logging.ConsoleHandler\njava.util.logging.ConsoleHandler.level=" + level).getBytes()));
        } catch (final IOException ignore) {
            assert false : "This should never happen because we're reading from RAM.";
        }
    }

    /** Initializes the logging system from a properties file.
     * @param filename Filename of the file to initialize the logging system with.
     * @throws IOException In case of I/O problems reading the properties file.
     */
    @Option({"L", "logconfig"})
    public void setLogConfig(@NotNull final String filename) throws IOException {
        final InputStream in = new FileInputStream(filename);
        try {
            LogManager.getLogManager().readConfiguration(in);
        } finally {
            in.close();
        }
    }

    /** Returns the logger.
     * @return The Logger.
     */
    public Logger getLog() {
        return log;
    }

    /** Log something.
     * @param level Level to log.
     * @param key Message key.
     * @param args Message arguments
     */
    protected void log(@NotNull final Level level, @NotNull final String key, @NotNull final Object... args) {
        log.log(level, key, args);
    }

} // class LogCommand
