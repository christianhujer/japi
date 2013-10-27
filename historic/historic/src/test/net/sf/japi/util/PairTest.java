/*
 * JAPI - (Yet another (hopefully) useful) Java API
 *
 * Copyright (C) 2006 Christian Hujer
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package test.net.sf.japi.util;

import junit.framework.TestCase;
import net.sf.japi.util.Pair;

/** Test class for {@link Pair}.
 * @author <a href="mailto:chris@itcqis.com">Christian Hujer</a>
 */
public class PairTest extends TestCase {

    /** Object Under Test: A Table. */
    private Pair<Object,Object> oUT;
    private String first;
    private String second;

    /** {@inheritDoc} */
    @Override protected void setUp() throws Exception {
        super.setUp();
        first = "First";
        second = "Second";
        oUT = new Pair<Object,Object>(first, second);
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"AssignmentToNull"})
    @Override protected void tearDown() throws Exception {
        super.tearDown();
        oUT = null;
        first = null;
        second = null;
    }

    /** Test case for {@link Pair#getFirst()}.
     * @throws Exception (unexpected).
     */
    public void testGetFirst() throws Exception {
        assertSame("First must be retreivable via getFirst()", first, oUT.getFirst());
    }

    /** Test case for {@link Pair#getSecond()}.
     * @throws Exception (unexpected).
     */
    public void testGetSecond() throws Exception {
        assertSame("First must be retreivable via getSecond()", second, oUT.getSecond());
    }

    /** Test case for {@link Pair#Pair(Object,Object)}.
     * @throws Exception (unexpected).
     */
    public void testPair() throws Exception {
        assertTrue("Dummy assertion.", true);
        // This test is already implicitly performed by #setUp().
    }

    /** Test case for {@link Pair#equals(Object)}.
     * @throws Exception (unexpected).
     */
    public void testEquals() throws Exception {
        assertFalse("A pair must not be equal to random objects.", oUT.equals(new Object()));
        assertEquals("A pair must be equal to itself", oUT, oUT);
        assertEquals("A pair must be equal to another pair with same first and second", oUT, new Pair<Object, Object>(first, second));
        assertEquals("A pair must be equal to another pair with equal first and second", oUT, new Pair<Object, Object>(new String(first.toCharArray()), new String(second.toCharArray())));
    }

} // class PairTest
