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

package test.net.sf.japi.io;

import junit.framework.TestCase;
import net.sf.japi.io.BCD;

/** Test for {@link BCD}.
 * @author <a href="mailto:chris@itcqis.com">Christian Hujer</a>
 */
public class BCDTest extends TestCase {

    /** {@inheritDoc} */
    @Override public void setUp() throws Exception {
        super.setUp();
    }

    /** {@inheritDoc} */
    @Override public void tearDown() throws Exception {
        super.tearDown();
    }

    /** Test case for {@link BCD#bcd2int(int)}. */
    public void testBcd2int() {
        assertEquals("bcd 0x00000000 -> dec        0",        0, BCD.bcd2int(0x00000000));
        assertEquals("bcd 0x00000010 -> dec       10",       10, BCD.bcd2int(0x00000010));
        assertEquals("bcd 0x99999999 -> dec 99999999", 99999999, BCD.bcd2int(0x99999999));
    }

    /** Test case for {@link BCD#int2bcd(int)}. */
    public void testInt2bcd() {
        assertEquals("dec        0 -> bcd 0x00000000", 0x00000000, BCD.int2bcd(       0));
        assertEquals("dec       10 -> bcd 0x00000010", 0x00000010, BCD.int2bcd(      10));
        assertEquals("dec 99999999 -> bcd 0x99999999", 0x99999999, BCD.int2bcd(99999999));
    }

    /** Test case for {@link BCD#check(int)}. */
    public void testCheck() {
        try {
            BCD.check(0x99999999);
        } catch (final IllegalArgumentException ignore) {
            fail("0x99999999 is okay");
        }
    }

    /** Test case for {@link BCD#isBcd(int)}. */
    public void testIsBcd() {
        assertTrue("0x99999999 is okay", BCD.isBcd(0x99999999));
        assertFalse("0xAAAAAAAA is not okay", BCD.isBcd(0xAAAAAAAA));
    }

    /** Test case for {@link BCD#base10(int)}. */
    public void testBase10() {
        assertEquals("pow(10, 0) is 1.", 1, BCD.base10(0));
        assertEquals("pow(10, 1) is 10.", 10, BCD.base10(1));
        assertEquals("pow(10, 2) is 100.", 100, BCD.base10(2));
        assertEquals("pow(10, 3) is 1000.", 1000, BCD.base10(3));
        assertEquals("pow(10, 4) is 10000.", 10000, BCD.base10(4));
        assertEquals("pow(10, 5) is 100000.", 100000, BCD.base10(5));
        assertEquals("pow(10, 6) is 1000000.", 1000000, BCD.base10(6));
        assertEquals("pow(10, 7) is 10000000.", 10000000, BCD.base10(7));
        assertEquals("pow(10, 8) is 100000000.", 100000000, BCD.base10(8));
        assertEquals("pow(10, 9) is 1000000000.", 1000000000, BCD.base10(9));
    }

    /** Test case for {@link BCD#correct(int)}. */
    public void testCorrect() {
        assertEquals("correct(0xA) is 0x10.", 0x10, BCD.correct(0xA));
    }

} // class BCDTest
