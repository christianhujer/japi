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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/** An iterator that iterates over a regular expression split.
 *
 * @note Splitting the empty String will lead to zero CharSequences returned by this Iterator.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class SplitIterator implements Iterator<CharSequence> {

    /** The CharSequence to split. */
    @NotNull private final CharSequence text;

    /** The Matcher used. */
    @NotNull private final Matcher matcher;

    /** The start of the next part.
     * By initializing it with zero, {@link #hasNext()} will return false for an empty String.
     */
    private int nextStart;

    /** Creates a SplitIterator.
     * @param text Text to split.
     * @param pattern Pattern to use for splitting.
     */
    public SplitIterator(@NotNull final CharSequence text, @NotNull final Pattern pattern) {
        this.text = text;
        matcher = pattern.matcher(text);
    }

    /** Creates a SplitIterator.
     * @param text Text to split.
     * @param pattern Patten to use for splitting.
     */
    public SplitIterator(@NotNull final CharSequence text, @NotNull final String pattern) {
        this.text = text;
        matcher = Pattern.compile(pattern).matcher(text);
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
        return nextStart < text.length();
    }

    /** {@inheritDoc} */
    public CharSequence next() {
        // 1. Throw an exception if there are no remaining elements.
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        // 2. Determine the start of the element.
        // The start was the initial value or set by the previous invocation of next().
        // Do not rename start - matches CharSequence#subSequence(int, int).
        final int start = nextStart;

        // 3. Determine the end (exclusive) of the element and the start of the next element.
        final int end;
        if (matcher.find()) {
            // If the matcher found something, there either are remaining elements or it matched the end of the text.
            // In both situations, this element ends before the start of the match.
            end = matcher.start();
            // If there are remaining elements, nextStart points to the next element.
            // If not, nextStart points beyond the end of the text, which correctly prevents further iteration.
            nextStart = matcher.end();
        } else {
            // If there are no remaining parts, the end of the part is the end of the text().
            // Setting nextStart beyond the end of the text will prevent further iterations.
            end = text.length();
            nextStart = text.length();
        }

        // 4. Return the subsequence that is that part.
        return text.subSequence(start, end);
    }

    /** {@inheritDoc}
     * @throws UnsupportedOperationException The remove operation is not supported by this iterator.
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
