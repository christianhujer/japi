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

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/** Iterable for iterating lines.
 *
 * @warning The returned Iterator may show unexpected behaviour when the underlying CharSequence is concurrently modified.
 *
 * @see LineIterator for more details.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class LineIterable implements Iterable<CharSequence> {

    /** The pattern for splitting lines. */
    private static final Pattern LINE_SPLIT_PATTERN = Pattern.compile("(?<=\\n|\u0085|\u2028|\u2029|\\r(?!\\n))");

    /** The implementation for creating the iterator. */
    public enum IteratorImplementation {

        /** Create the iterator from a list from a pattern split. */
        PATTERN_LIST,

        /** Use the {@link LineIterator} class. */
        LINE_ITERATOR,

        /** Use the {@link SplitIterator} class. */
        SPLIT_ITERATOR
    }

    /** Compile time-switch whether to use regex or LineIterator. */
    public static final IteratorImplementation DEFAULT_MODE = IteratorImplementation.SPLIT_ITERATOR;

    /** The text that should be iterated. */
    @NotNull private final CharSequence text;

    /** The implementation to use. */
    @NotNull private final IteratorImplementation mode;

    /** Create an Iterable for iterating lines.
     * Uses the default mode for creating the iterator.
     * @param text CharSequence that for iterating line-wise.
     */
    public LineIterable(final CharSequence text) {
        this(text, DEFAULT_MODE);
    }

    /** Create an Iterable for iterating lines.
     * Uses the default mode for creating the iterator.
     * @param text CharSequence that for iterating line-wise.
     * @param mode Implemenation to use.
     */
    public LineIterable(final CharSequence text, @NotNull final IteratorImplementation mode) {
        this.text = text;
        this.mode = mode;
    }

    /** {@inheritDoc} */
    public Iterator<CharSequence> iterator() {
        switch (mode) {
        case PATTERN_LIST:
            if (text.length() == 0) {
                return Collections.<CharSequence>emptySet().iterator();
            }
            return Arrays.<CharSequence>asList(LINE_SPLIT_PATTERN.split(text)).iterator();
        case LINE_ITERATOR:
            return new LineIterator(text);
        case SPLIT_ITERATOR:
            return new SplitIterator(text, LINE_SPLIT_PATTERN);
        default:
            assert false;
        }
        //noinspection ProhibitedExceptionThrown
        throw new Error("Compile time error. IteratorImplementation missing.");
    }
}
