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

package test.net.sf.japi.tools.replacer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.sf.japi.tools.replacer.LineIterable;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/** Unit Test for {@link LineIterable}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public abstract class LineIterationTestCase {

    /** Creates an Iterator to iterate over the lines in the specified text.
     * @param text Text to iterate line-wise.
     * @return Iterator for iterating line-wise over that text.
     */
    public abstract Iterator<CharSequence> createIterator(@NotNull final CharSequence text);

    /** Tests that an empty String does not have any lines at all. */
    @Test
    public void testEmpty() {
        final Iterator<CharSequence> iterator = createIterator("");
        Assert.assertFalse("An empty String must not return elements.", iterator.hasNext());
    }

    /** Tests that getting the next line if there are no lines at all throws an exception.
     * @throws NoSuchElementException Expected exception when trying to get a next lines where there are no lines.
     */
    @Test(expected = NoSuchElementException.class)
    public void testEmptyThrowsNSEE() {
        final Iterator<CharSequence> iterator = createIterator("");
        iterator.next();
    }

    /** Tests that getting the next line where there is just a single character returns exactly that character. */
    @Test
    public void testOneChar() {
        final Iterator<CharSequence> iterator = createIterator("f");
        Assert.assertTrue("One line.", iterator.hasNext());
        Assert.assertEquals("That line is \"f\"", "f", iterator.next());
        Assert.assertFalse("No more lines left.", iterator.hasNext());
    }

    /** Tests that getting the next line where there is just one empty line returns exactly that empty line. */
    @Test
    public void testOneEmptyLine() {
        final Iterator<CharSequence> iterator = createIterator("\n");
        Assert.assertTrue("One line.", iterator.hasNext());
        Assert.assertEquals("That line is empty (apart from its \\n).", "\n", iterator.next());
        Assert.assertFalse("No more lines left.", iterator.hasNext());
    }

    @Test
    public void testSimple() {
        final Iterator<CharSequence> iterator = createIterator("foo");
        Assert.assertTrue("One line.", iterator.hasNext());
        Assert.assertEquals("That line is \"foo\"", "foo", iterator.next());
        Assert.assertFalse("No more lines left.", iterator.hasNext());
    }

    @Test
    public void testTwoLinesNoEOL() {
        final Iterator<CharSequence> iterator = createIterator("foo\nbar");
        Assert.assertTrue("Two lines left.", iterator.hasNext());
        Assert.assertEquals("First line is \"foo\\n\".", "foo\n", iterator.next());
        Assert.assertTrue("One line left.", iterator.hasNext());
        Assert.assertEquals("Second line is \"bar\".", "bar", iterator.next());
        Assert.assertFalse("No more lines left.", iterator.hasNext());
    }

    @Test
    public void testTwoLinesEOL() {
        final Iterator<CharSequence> iterator = createIterator("foo\nbar\n");
        Assert.assertTrue("Two lines left.", iterator.hasNext());
        Assert.assertEquals("First line is \"foo\\n\".", "foo\n", iterator.next());
        Assert.assertTrue("One line left.", iterator.hasNext());
        Assert.assertEquals("Second line is \"bar\\n\".", "bar\n", iterator.next());
        Assert.assertFalse("No more lines left.", iterator.hasNext());
    }

    @Test
    public void testSingleCharLinesNoEOL() {
        final Iterator<CharSequence> iterator = createIterator("f\nb");
        Assert.assertTrue("Two lines left.", iterator.hasNext());
        Assert.assertEquals("First line is \"f\\n\".", "f\n", iterator.next());
        Assert.assertTrue("One line left.", iterator.hasNext());
        Assert.assertEquals("Second line is \"b\".", "b", iterator.next());
        Assert.assertFalse("No more lines left.", iterator.hasNext());
    }

    @Test
    public void testSingleCharLinesEOL() {
        final Iterator<CharSequence> iterator = createIterator("f\nb\n");
        Assert.assertTrue("Two lines left.", iterator.hasNext());
        Assert.assertEquals("First line is \"f\\n\".", "f\n", iterator.next());
        Assert.assertTrue("One line left.", iterator.hasNext());
        Assert.assertEquals("Second line is \"b\\n\".", "b\n", iterator.next());
        Assert.assertFalse("No more lines left.", iterator.hasNext());
    }

    @Test
    public void testDoubleEOL() {
        final Iterator<CharSequence> iterator = createIterator("f\n\n");
        Assert.assertTrue("Two lines left.", iterator.hasNext());
        Assert.assertEquals("First line is \"f\\n\".", "f\n", iterator.next());
        Assert.assertTrue("One line left.", iterator.hasNext());
        Assert.assertEquals("Second line is \"\\n\".", "\n", iterator.next());
        Assert.assertFalse("No more lines left.", iterator.hasNext());
    }

    /** Tests that for a single DOS line terminator, the line feed, not the carriage return, terminates the line. */
    @Test
    public void testCRLFTerminatedByLF() {
        final Iterator<CharSequence> iterator = createIterator("\r\n");
        Assert.assertTrue("One line.", iterator.hasNext());
        Assert.assertEquals("First line is \"\\r\\n\"", "\r\n", iterator.next());
        Assert.assertFalse("No more lines left.", iterator.hasNext());
    }

    /** Tests that remove() throws an UnsupportedOperationException. */
    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        final Iterator<CharSequence> iterator = createIterator("foo");
        iterator.next();
        iterator.remove();
    }
}
