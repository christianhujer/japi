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

import net.sf.japi.lang.PropertyComparator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link PropertyComparator}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class PropertyComparatorTest {

    /** Test the use case comparing String lengths. */
    @Test
    public void testStringLengthExample() {
        final String s1 = "hello";
        final String s2 = "world";
        final String s3 = "a";
        final String s4 = "nice";
        final String s5 = "day";
        final PropertyComparator<Integer, String> lengthComparator = new PropertyComparator<Integer, String>(String.class, "length", null);
        Assert.assertSame(0, lengthComparator.compare(s1, s2));
        Assert.assertTrue(lengthComparator.compare(s2, s3) > 0);
        Assert.assertTrue(lengthComparator.compare(s3, s4) < 0);
        Assert.assertTrue(lengthComparator.compare(s4, s5) > 0);
    }

} // class PropertyComparatorTest
