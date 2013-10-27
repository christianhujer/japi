/*
 * Copyright (C) 2009  Christian Hujer
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

package net.sf.japi.cstyle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A LineEndingParser can verify whether a file has the correct line ending.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class LineEndingParser extends AbstractParser implements CharParser {

    /** Whether the previous character was {@link #ASCII_CR}. */
    private boolean carriageReturn;

    /** ASCII control character CR (Carriage Return). */
    private static final int ASCII_CR = 0x0D;

    /** ASCII control character LF (Line Feed). */
    private static final int ASCII_LF = 0x0A;

    /** The desired NewLine. */
    @NotNull private NewLine desiredNewLine;

    /** {@inheritDoc} */
    public void process(final char c) {
        final boolean lineFeed = c == ASCII_LF;
        @Nullable final NewLine newLineFound;
        newLineFound = lineFeed
                ? carriageReturn ? NewLine.DOS : NewLine.UNIX
                : carriageReturn ? NewLine.MAC : null;
        carriageReturn = c == ASCII_CR;
        if (newLineFound != null && newLineFound != desiredNewLine) { // NOPMD
            // TODO:2009-02-18:christianhujer:Finish implementation.
        }
    }

    /** Set the desired NewLine.
     * @param desiredNewLine Desired NewLine.
     */
    public void setDesiredNewLine(@NotNull final NewLine desiredNewLine) {
        this.desiredNewLine = desiredNewLine;
    }

    /** Newline state. */
    public enum NewLine {

        /** CRLF DOS NewLine. */
        DOS,

        /** LF UNIX NewLine. */
        UNIX,

        /** CR MAC NewLine. */
        MAC

    } // enum NewLine

} // class LineEndingParser
