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

package test.net.sf.japi.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import net.sf.japi.util.EnumerationIterator;
import org.junit.Assert;
import org.junit.Test;

/** Test class for {@link EnumerationIterator}.
 * @author <a href="mailto:chris@itcqis.com">Christian Hujer</a>
 */
public class EnumerationIteratorTest {

    /** Test case for {@link EnumerationIterator#iterator()}. */
    @Test
    public void testIteratorEmptyList() {
        final Iterable<?> oUT = new EnumerationIterator<Object>(Collections.enumeration(Arrays.asList()));
        Assert.assertNotNull("Even empty enumerations must generate an iterator instance.", oUT.iterator());
    }

    /** Test case for {@link EnumerationIterator#iterator()}. */
    @Test
    public void testIteratorTwoElements() {
        final Iterable<?> oUT = new EnumerationIterator<String>(Collections.enumeration(Arrays.asList("foo", "bar")));
        Assert.assertNotNull("Even empty enumerations must generate an iterator instance.", oUT.iterator());
    }

    /** Test case for {@link EnumerationIterator#hasNext()}. */
    @Test public void testHasNextEmpty() {
        final Iterator<?> oUT = new EnumerationIterator<Object>(Collections.enumeration(Arrays.asList()));
        Assert.assertFalse("hasNext() on empty enumeration must instantly return false.", oUT.hasNext());
    }

    /** Test case for {@link EnumerationIterator#hasNext()}. */
    @Test public void testHasNextTwoElements() {
        final Iterator<?> oUT = new EnumerationIterator<String>(Collections.enumeration(Arrays.asList("foo", "bar")));
        Assert.assertTrue("hasNext() on nonempty enumeration must first return true.", oUT.hasNext());
    }

    /** Test case for {@link EnumerationIterator#next()}.
     * @throws NoSuchElementException (expected).
     */
    @Test(expected = NoSuchElementException.class)
    public void testNextEmpty() throws NoSuchElementException {
        final Iterator<?> oUT = new EnumerationIterator<Object>(Collections.enumeration(Arrays.asList()));
        oUT.next();
    }

    /** Test case for {@link EnumerationIterator#next()}.
     * @throws NoSuchElementException (expected).
     */
    @Test(expected = NoSuchElementException.class)
    public void testNextTwoElements() throws NoSuchElementException {
        final Iterator<?> oUT = new EnumerationIterator<String>(Collections.enumeration(Arrays.asList("foo", "bar")));
        try {
            oUT.next();
            oUT.next();
        } catch (final NoSuchElementException ignore) {
            Assert.fail("next() on two elements enumeration must not throw NoSuchElementException before third invocation.");
        }
        oUT.next();
    }

    /** Test case for {@link EnumerationIterator#remove()}.
     * @throws UnsupportedOperationException (unexpected).
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveEmpty() throws UnsupportedOperationException {
        final Iterator<?> oUT = new EnumerationIterator<Object>(Collections.enumeration(Arrays.asList()));
        oUT.remove();
    }

    /** Test case for {@link EnumerationIterator#remove()}.
     * @throws UnsupportedOperationException (unexpected).
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveTwoElements() throws UnsupportedOperationException {
        final Iterator<?> oUT = new EnumerationIterator<String>(Collections.enumeration(Arrays.asList("foo", "bar")));
        try {
            oUT.next();
        } catch (final UnsupportedOperationException ignore) {
            Assert.fail("Unexpected UnsupportedOperationException.");
        }
        oUT.remove();
    }

} // class EnumerationIteratorTest
