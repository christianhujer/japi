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

package test.net.sf.japi.lang;

import java.util.NoSuchElementException;
import net.sf.japi.lang.SuperClassIterable;
import net.sf.japi.lang.SuperClassIterator;
import org.junit.Assert;
import org.junit.Test;

/** Test for {@link SuperClassIterator}.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class SuperClassIteratorTest {

    /** Tests that iterating the superclasses works. */
    @Test
    public void testIteration() {
        final SuperClassIterator it = new SuperClassIterator(String.class);
        Assert.assertTrue("Must have next.", it.hasNext());
        Assert.assertSame("First class must be String.class.", String.class, it.next());
        Assert.assertTrue("Must have next.", it.hasNext());
        Assert.assertSame("Second class must be Object.class.", Object.class, it.next());
        Assert.assertFalse("Must not have next now.", it.hasNext());
    }

    /** Tests that iterating the superclasses works. */
    @Test
    public void testException() {
        final SuperClassIterator it = new SuperClassIterator(String.class);
        it.next();
        it.next();
    }

    /** Tests that iterating the superclasses works. */
    @Test(expected = NoSuchElementException.class)
    public void testException2() {
        final SuperClassIterator it = new SuperClassIterator(String.class);
        it.next();
        it.next();
        it.next();
    }

    /** Tests that {@link SuperClassIterator#remove()} throws an exception. */
    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        final SuperClassIterator it = new SuperClassIterator(String.class);
        it.remove();
    }

    /** Tests that using {@link SuperClassIterable} as Iterable works. */
    @Test
    public void testIterable() {
        for (final Class c : new SuperClassIterable(String.class)) {
            if (!c.equals(String.class) && !c.equals(Object.class)) {
                Assert.fail("Expected String.class or Object.class.");
            }
        }
    }

} // class SuperClassIteratorTest
