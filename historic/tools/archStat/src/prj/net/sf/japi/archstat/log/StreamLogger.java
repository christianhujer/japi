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

import java.util.Formatter;
import org.jetbrains.annotations.NotNull;

/** The StreamLogger is a Logger for LogEntries that logs to the specified Stream.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class StreamLogger implements Logger {

    /** The Stream for logging. */
    @NotNull
    private final Formatter out;

    /** Creates a StreamLogger.
     * @param out Stream for logging.
     */
    public StreamLogger(@NotNull final Appendable out) {
        this.out = new Formatter(out);
    }

    /** {@inheritDoc} */
    public void log(@NotNull final LogEntry logEntry) {
        out.format("%s%n", logEntry);
    }
}
