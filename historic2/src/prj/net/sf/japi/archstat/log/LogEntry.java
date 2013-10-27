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

import java.io.File;
import java.util.Formatter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.sf.japi.archstat.MessageType;

/** A LogEntry represents an atomic ArchStat information.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class LogEntry {

    /** The File for which this LogEntry is being logged. */
    private final File file;

    /** The line in {@link #file} for which this LogEntry is being logged.
     * Maybe <code>null</code> if the LogEntry does not apply to a particular line.
     */
    @Nullable private final String line;

    /** The lineNumber in {@link #file} for which this LogEntry is being logged.
     * Maybe <code>null</code> if the LogEntry does not apply to a particular lineNumber.
     * The line number is the user line, the first line is 1.
     */
    @Nullable private final Integer lineNumber;

    /** The column in {@link #file} {@link #lineNumber} for which this LogEntry is being logged.
     * Maybe <code>null</code> if the LogEntry does not apply to a particular column.
     * The column is the user column, the first column is 1.
     */
    @Nullable private final Integer column;

    /** The MessageType for this LogEntry. */
    @NotNull private final MessageType messageType;

    /** The message ID of this type of log entry. */
    @NotNull private final String messageId;

    /** The message of this log entry. */
    @NotNull private final String message;

    /** Creates a LogEntry.
     * @param file File for which this LogEntry is being logged.
     * @param line Line in <var>file</var> or <code>null</code>.
     * @param lineNumber Line number in <var>file</var> or <code>null</code> (user line, first line is 1).
     * @param column Column in <var>lineNumber</var> or <code>null</code> (user column, first column is 1).
     * @param messageType Message Type.
     * @param messageId Message ID.
     * @param message The message.
     */
    public LogEntry(final File file, @Nullable final String line, @Nullable final Integer lineNumber, @Nullable final Integer column, @NotNull final MessageType messageType, @NotNull final String messageId, @NotNull final String message) {
        this.file = file;
        this.line = removeEOL(line);
        this.lineNumber = lineNumber;
        this.column = column;
        this.messageType = messageType;
        this.messageId = messageId;
        this.message = message;
    }

    /** Returns a String with any trailing end of line characters removed.
     * Trailing whitespace other than EOL characters are not removed.
     * This method currently treats LF and CR as EOL characters.
     * @param line Line from which to remove trailing end of line characters, maybe <code>null</code>
     * @return The line with trailing end of line characters removed.
     */
    private static String removeEOL(@Nullable final String line) {
        if (line != null) {
            return line.replaceAll("[\n\r]+$", "");
        }
        return line;
    }

    /** Creates a LogEntry.
     * @param file File for which this LogEntry is being logged.
     * @param messageType Message Type.
     * @param messageId Message ID.
     * @param message The message.
     */
    public LogEntry(final File file, @NotNull final MessageType messageType, @NotNull final String messageId, @NotNull final String message) {
        this(file, null, null, null, messageType, messageId, message);
    }

    /** Returns the file for which this LogEntry was created.
     * @return The file for which this LogEntry was created.
     */
    public File getFile() {
        return file;
    }

    /** Returns the lineNumber for which this LogEntry was created.
     * @return The lineNumber number or <code>null</code> in case it's not applicable.
     */
    @Nullable public Integer getLineNumber() {
        return lineNumber;
    }

    /** Returns the column for which this LogEntry was created.
     * @return The column number or <code>null</code> in case it's not applicable.
     */
    @Nullable public Integer getColumn() {
        return column;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return toString(new Formatter()).toString();
    }

    /** Formats this LogEntry to the specified formatter.
     * @param out Formatter to log to.
     * @return <var>out</var> for convenience.
     */
    public Formatter toString(@NotNull final Formatter out) {
        if (lineNumber != null && column != null) {
            out.format("%s:%s:%s: %s: (%s): %s", file, lineNumber, column, messageType, messageId, message);
            if (line != null) {
                out.format("%n%s%n", line);
                for (int i = 1; i < column; i++) {
                    out.format("-");
                }
                out.format("^%n");
            }
        } else if (lineNumber != null) {
            out.format("%s:%s: %s: (%s): %s", file, lineNumber, messageType, messageId, message);
        } else {
            out.format("%s: %s: (%s): %s", file, messageType, messageId, message);
        }
        return out;
    }
}
