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

package test.net.sf.japi.io.args;

import java.util.Arrays;
import java.util.Iterator;
import net.sf.japi.io.args.StringJoiner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Test for {@link StringJoiner}.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class StringJoinerTest {

    /**
     * Expected String.
     */
    private static final String EXPECTED = "foo, bar, buzz";

    /**
     * Argument values.
     */
    private static final String[] ARGS = {"foo", "bar", "buzz"};

    /**
     * Tests that {@link StringJoiner#join(CharSequence,CharSequence...)} works.
     */
    @Test
    public void testJoinCSCS() {
        final String[] nargs = ARGS.clone();
        final String actual = StringJoiner.join(", ", nargs);
        assertEquals("arguments must be joined correctly.", EXPECTED, actual);
        assertTrue("arguments must not be changed by join().", Arrays.equals(ARGS, nargs));
    }

    /**
     * Tests that {@link StringJoiner#join(CharSequence,Iterable)} works.
     */
    @Test
    public void testJoinCSIe() {
        final String[] nargs = ARGS.clone();
        final String actual = StringJoiner.join(", ", Arrays.asList(nargs));
        assertEquals("arguments must be joined correctly.", EXPECTED, actual);
        assertTrue("arguments must not be changed by join().", Arrays.equals(ARGS, nargs));
    }

    /**
     * Tests that {@link StringJoiner#join(CharSequence, Iterator)} works.
     */
    @Test
    public void testJoinCSIr() {
        final String[] nargs = ARGS.clone();
        final String actual = StringJoiner.join(", ", Arrays.asList(nargs).iterator());
        assertEquals("arguments must be joined correctly.", EXPECTED, actual);
        assertTrue("arguments must not be changed by join().", Arrays.equals(ARGS, nargs));
    }

} // class StringJoinerTest
