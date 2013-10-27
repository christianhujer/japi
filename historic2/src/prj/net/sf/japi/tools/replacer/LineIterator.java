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

package net.sf.japi.tools.replacer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;

/** Iterator for lines.
 *
 * <h4>Line terminators</h4>
 * A <dfn>line terminator</dfn> is a one- or two-character sequence that marks the end of a line of the input character sequence.
 * The following are recognized as line terminators:
 * <ul>
 *  <li>A newline (line feed) character&nbsp;(<code>'\n'</code>),</li>
 *  <li>a carriage-return character followed immediately by a newline character&nbsp;(<code>"\r\n"</code>),</li>
 *  <li>a standalone carriage-return character&nbsp;(<code>'\r'</code>),</li>
 *  <li>a next-line character&nbsp;(<code>'&#92;u0085'</code>),</li>
 *  <li>a line-separator character&nbsp;(<code>'&#92;u2028'</code>), or</li>
 *  <li>a paragraph-separator character&nbsp;(<code>'&#92;u2029</code>).</li>
 * </ul>
 *
 * The line terminator is included in the returned line.
 *
 * This Iterator does not support {@link Iterator#remove()}.
 *
 * @warning The Iterator may show unexpected behaviour when the underlying CharSequence is concurrently modified.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class LineIterator implements Iterator<CharSequence> {

    /** Unicode character "NEW LINE" / "LINE FEED". */
    private static final char LINE_FEED = '\n';

    /** Unicode character "CARRIAGE RETURN". */
    private static final char CARRIAGE_RETURN = '\r';

    /** Unicode character "NEXT LINE". */
    private static final char NEXT_LINE = '\u0085';

    /** Unicode character "LINE SEPARATOR". */
    private static final char LINE_SEPARATOR = '\u2028';

    /** Unicode character "PARAGRAPH SEPARATOR". */
    private static final char PARAGRAPH_SEPARATOR = '\u2029';

    /** The text that contains the lines over which to iterate. */
    @NotNull private final CharSequence text;

    /** The end index of the previous line (exclusive),
     * at the same time the start index of the next line (inclusive).
     */
    // Do not rename end - matches CharSequence#subSequence(int, int).
    private int end = 0;

    /** Create a line iterator.
     * @param text Text to iterator line-wise.
     */
    public LineIterator(@NotNull final CharSequence text) {
        this.text = text;
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
        return end < text.length();
    }

    /** {@inheritDoc} */
    public CharSequence next() {
        // 1. Check if there is a next line.
        // If not, the iterator was used wrongly, so throw an exception.
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        // 2. Determine start of line.
        // The previous end (exclusive) is the next start (inclusive).
        // Do not rename start - matches CharSequence#subSequence(int, int).
        final int start = end;

        // 3. Determine end of line.
        // The end of line is after the next line terminator.
        //noinspection ValueOfIncrementOrDecrementUsed
        while (++end < text.length()) {
            if (isCharBeforeEndALineTerminator()) {
                break;
            }
        }

        // 4. Return the text's subsequence that is that line.
        return text.subSequence(start, end);
    }

    /** Returns whether or not the character before the current {@link #end} terminates a line.
     * @return Whether or not the character before the current {@link #end} terminates a line.
     * @retval <code>true</code> if the character before the current {@link #end} terminates a line.
     * @retval <code>false</code> otherwise (no line termination).
     */
    private boolean isCharBeforeEndALineTerminator() {
        final char c = text.charAt(end - 1);
        return
                c == LINE_FEED
                // CARRIAGE_RETURN only already terminates a line if it is standalone.
                // If it is immediately followed by a LINE_FEED, that line feed will terminate the line in the next invocation.
                || c == CARRIAGE_RETURN && (end == text.length() || text.charAt(end) != LINE_FEED)
                || c == NEXT_LINE
                || c == LINE_SEPARATOR
                || c == PARAGRAPH_SEPARATOR;
    }

    /** {@inheritDoc}
     * @throws UnsupportedOperationException The remove operation is not supported by this iterator.
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
