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
import net.sf.japi.util.Table;

/** Test class for Table.
 * @author <a href="mailto:chris@itcqis.com">Christian Hujer</a>
 */
public class TableTest extends TestCase {

    /** Object Under Test: A Table. */
    private Table<Object,Object> oUT;

    /** {@inheritDoc} */
    @Override public void setUp() throws Exception {
        super.setUp();
        oUT = new Table<Object,Object>();
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"AssignmentToNull"})
    @Override protected void tearDown() throws Exception {
        super.tearDown();
        oUT = null;
    }

    /** Test case for creating an empty table. */
    public void testTable() {
        assertEquals("Newly created table must be empty.", 0, oUT.size());
    }

    /** Test case for clearing a table.
     * @throws Exception (unexpected).
     */
    public void testClear() throws Exception {
        oUT.putPair(new Object(), new Object());
        assertSize("Added 1 Element", 1);
        oUT.clear();
        assertSize("Cleared", 0);
        oUT.putPair(new Object(), new Object());
        assertSize("Added 1 Element", 1);
        oUT.putPair(new Object(), new Object());
        assertSize("Added 2 Elements", 2);
        oUT.putPair(new Object(), new Object());
        assertSize("Added 3 Elements", 3);
        oUT.clear();
        assertSize("Cleared", 0);
    }

    /** Test case for putPair() methods. */
    public void testPutPair() {
        final String key1 = "key1";
        final Object value1 = "value1";
        oUT.putPair(key1, value1);
        assertSize("Added 1 Element", 1);
        final String key2 = "key2";
        final Object value2 = "value2";
        oUT.putPair(new Pair<Object,Object>(key2, value2));
        assertSize("Added 2 Elements", 2);
        // TODO: Get pairs and compare...
    }

    /** Assert that the table (oUT) has a certain size.
     * @param reason Reason why the size is expected
     * @param size Expected size
     */
    private void assertSize(final String reason, final int size) {
        assertEquals("Expected size " + size + " (Reason: " + reason + ')', size, oUT.size());
    }

} // class TableTest
