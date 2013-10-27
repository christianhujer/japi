/*
 * Copyright (C) 2009 - 2010  Christian Hujer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.japi.archstat.log;

import org.jetbrains.annotations.NotNull;

/** Static access to logging.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class LogSystem {

    /** The loggers. */
    private static final Logger[] LOGGERS = { new StreamLogger(System.err) };

    /** Utility class - do not instanciate. */
    private LogSystem() {
    }

    /** Logs a single log entry.
     * @param logEntry LogEntry to log.
     */
    public static void log(@NotNull final LogEntry logEntry) {
        for (final Logger logger : LOGGERS) {
            logger.log(logEntry);
        }
    }
}
