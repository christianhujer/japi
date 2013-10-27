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
import net.sf.japi.util.Arrays2;
import net.sf.japi.util.filter.Filter;
import org.junit.Assert;
import org.junit.Test;

/** Test for {@link Arrays2}.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class Arrays2Test {

    /** Test case for {@link Arrays2#concat(byte[][])}. */
    @SuppressWarnings({"JavadocReference"}) // javadoc and IntelliJ IDEA disagree on byte[][] (javadoc) vs. byte[]... (IDEA).
    @Test
    public void testConcatByte() {
        final byte[] data1Orig = {1, 2, 3};
        final byte[] data2Orig = {4, 5, 6, 7};
        final byte[] data1Copy = data1Orig.clone();
        final byte[] data2Copy = data2Orig.clone();
        final byte[] concatExpected = {1, 2, 3, 4, 5, 6, 7};
        final byte[] concatResult = Arrays2.concat(data1Copy, data2Copy);
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data1Orig, data1Copy));
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data2Orig, data2Copy));
        Assert.assertTrue("Concatenation must correctly concatenate", Arrays.equals(concatExpected, concatResult));
    }

    /** Test case for {@link Arrays2#concat(short[][])}. */
    @SuppressWarnings({"JavadocReference"}) // javadoc and IntelliJ IDEA disagree on short[][] (javadoc) vs. short[]... (IDEA).
    @Test
    public void testConcatShort() {
        final short[] data1Orig = {1, 2, 3};
        final short[] data2Orig = {4, 5, 6, 7};
        final short[] data1Copy = data1Orig.clone();
        final short[] data2Copy = data2Orig.clone();
        final short[] concatExpected = {1, 2, 3, 4, 5, 6, 7};
        final short[] concatResult = Arrays2.concat(data1Copy, data2Copy);
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data1Orig, data1Copy));
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data2Orig, data2Copy));
        Assert.assertTrue("Concatenation must correctly concatenate", Arrays.equals(concatExpected, concatResult));
    }

    /** Test case for {@link Arrays2#concat(int[][])}. */
    @SuppressWarnings({"JavadocReference"}) // javadoc and IntelliJ IDEA disagree on int[][] (javadoc) vs. int[]... (IDEA).
    @Test
    public void testConcatInt() {
        final int[] data1Orig = {1, 2, 3};
        final int[] data2Orig = {4, 5, 6, 7};
        final int[] data1Copy = data1Orig.clone();
        final int[] data2Copy = data2Orig.clone();
        final int[] concatExpected = {1, 2, 3, 4, 5, 6, 7};
        final int[] concatResult = Arrays2.concat(data1Copy, data2Copy);
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data1Orig, data1Copy));
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data2Orig, data2Copy));
        Assert.assertTrue("Concatenation must correctly concatenate", Arrays.equals(concatExpected, concatResult));
    }

    /** Test case for {@link Arrays2#concat(long[][])}. */
    @SuppressWarnings({"JavadocReference"}) // javadoc and IntelliJ IDEA disagree on long[][] (javadoc) vs. long[]... (IDEA).
    @Test
    public void testConcatLong() {
        final long[] data1Orig = {1, 2, 3};
        final long[] data2Orig = {4, 5, 6, 7};
        final long[] data1Copy = data1Orig.clone();
        final long[] data2Copy = data2Orig.clone();
        final long[] concatExpected = {1, 2, 3, 4, 5, 6, 7};
        final long[] concatResult = Arrays2.concat(data1Copy, data2Copy);
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data1Orig, data1Copy));
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data2Orig, data2Copy));
        Assert.assertTrue("Concatenation must correctly concatenate", Arrays.equals(concatExpected, concatResult));
    }

    /** Test case for {@link Arrays2#concat(float[][])}. */
    @SuppressWarnings({"JavadocReference"}) // javadoc and IntelliJ IDEA disagree on float[][] (javadoc) vs. float[]... (IDEA).
    @Test
    public void testConcatFloat() {
        final float[] data1Orig = {1, 2, 3};
        final float[] data2Orig = {4, 5, 6, 7};
        final float[] data1Copy = data1Orig.clone();
        final float[] data2Copy = data2Orig.clone();
        final float[] concatExpected = {1, 2, 3, 4, 5, 6, 7};
        final float[] concatResult = Arrays2.concat(data1Copy, data2Copy);
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data1Orig, data1Copy));
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data2Orig, data2Copy));
        Assert.assertTrue("Concatenation must correctly concatenate", Arrays.equals(concatExpected, concatResult));
    }

    /** Test case for {@link Arrays2#concat(double[][])}. */
    @SuppressWarnings({"JavadocReference"}) // javadoc and IntelliJ IDEA disagree on double[][] (javadoc) vs. double[]... (IDEA).
    @Test
    public void testConcatDouble() {
        final double[] data1Orig = {1, 2, 3};
        final double[] data2Orig = {4, 5, 6, 7};
        final double[] data1Copy = data1Orig.clone();
        final double[] data2Copy = data2Orig.clone();
        final double[] concatExpected = {1, 2, 3, 4, 5, 6, 7};
        final double[] concatResult = Arrays2.concat(data1Copy, data2Copy);
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data1Orig, data1Copy));
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data2Orig, data2Copy));
        Assert.assertTrue("Concatenation must correctly concatenate", Arrays.equals(concatExpected, concatResult));
    }

    /** Test case for {@link Arrays2#concat(char[][])}. */
    @SuppressWarnings({"JavadocReference"}) // javadoc and IntelliJ IDEA disagree on char[][] (javadoc) vs. char[]... (IDEA).
    @Test
    public void testConcatChar() {
        final char[] data1Orig = {1, 2, 3};
        final char[] data2Orig = {4, 5, 6, 7};
        final char[] data1Copy = data1Orig.clone();
        final char[] data2Copy = data2Orig.clone();
        final char[] concatExpected = {1, 2, 3, 4, 5, 6, 7};
        final char[] concatResult = Arrays2.concat(data1Copy, data2Copy);
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data1Orig, data1Copy));
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data2Orig, data2Copy));
        Assert.assertTrue("Concatenation must correctly concatenate", Arrays.equals(concatExpected, concatResult));
    }

    /** Test case for {@link Arrays2#concat(boolean[][])}. */
    @SuppressWarnings({"JavadocReference"}) // javadoc and IntelliJ IDEA disagree on boolean[][] (javadoc) vs. boolean[]... (IDEA).
    @Test
    public void testConcatBoolean() {
        final boolean[] data1Orig = {true, false, true};
        final boolean[] data2Orig = {false, true, false, false};
        final boolean[] data1Copy = data1Orig.clone();
        final boolean[] data2Copy = data2Orig.clone();
        final boolean[] concatExpected = {true, false, true, false, true, false, false};
        final boolean[] concatResult = Arrays2.concat(data1Copy, data2Copy);
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data1Orig, data1Copy));
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data2Orig, data2Copy));
        Assert.assertTrue("Concatenation must correctly concatenate", Arrays.equals(concatExpected, concatResult));
    }

    /** Test case for {@link Arrays2#concat(Object[][])}. */
    @Test
    public void testConcatObject() {
        final String[] data1Orig = {"1", "2", "3"};
        final String[] data2Orig = {"4", "5", "6", "7"};
        final String[] data1Copy = data1Orig.clone();
        final String[] data2Copy = data2Orig.clone();
        final String[] concatExpected = {"1", "2", "3", "4", "5", "6", "7"};
        final String[] concatResult = Arrays2.concat(data1Copy, data2Copy);
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data1Orig, data1Copy));
        Assert.assertTrue("Original arrays must be unmodified", Arrays.equals(data2Orig, data2Copy));
        Assert.assertTrue("Concatenation must correctly concatenate", Arrays.equals(concatExpected, concatResult));
    }

    /** Test case for {@link Arrays2#filter(Filter, Object[])}. */
    @Test
    public void testFilter() {
        final String[] dataOrig = {"foo1", "foo2", "bar1", "foo3", "bar2", "bar3", "bar4"};
        final String[] dataCopy = dataOrig.clone();
        final Filter<String> filter = new Filter<String>() {
            public boolean accept(final String o) {
                return o.startsWith("foo");
            }
        };
        final String[] expected = {"foo1", "foo2", "foo3"};
        final String[] actual = Arrays2.filter(filter, dataCopy);
        Assert.assertTrue("Original array must be unmodified", Arrays.equals(dataOrig, dataCopy));
        Assert.assertTrue("Filter must filter correctly.", Arrays.equals(expected, actual));
    }

    /** Test case for {@link Arrays2#count(Filter, Object[])}. */
    @Test
    public void testCount() {
        final String[] dataOrig = {"foo1", "foo2", "bar1", "foo3", "bar2", "bar3", "bar4"};
        final String[] dataCopy = dataOrig.clone();
        final Filter<String> filter = new Filter<String>() {
            public boolean accept(final String o) {
                return o.startsWith("foo");
            }
        };
        final int expected = 3;
        final int actual = Arrays2.count(filter, dataCopy);
        Assert.assertTrue("Original array must be unmodified", Arrays.equals(dataOrig, dataCopy));
        Assert.assertEquals("Count must count correctly.", expected, actual);
    }

    /** Test case for {@link Arrays2#linearSearch(int, int[])}. */
    @Test
    public void testLinearSearch() {
        final int[] data = { 0, 1, 2, 3 };
        Assert.assertEquals("For an element not found, linearSearch must return -1.", -1, Arrays2.linearSearch(-1, data));
        Assert.assertEquals("For an element found, linearSearch must return its index.", 0, Arrays2.linearSearch(0, data));
        Assert.assertEquals("For an element found, linearSearch must return its index.", 1, Arrays2.linearSearch(1, data));
        Assert.assertEquals("For an element found, linearSearch must return its index.", 2, Arrays2.linearSearch(2, data));
        Assert.assertEquals("For an element found, linearSearch must return its index.", 3, Arrays2.linearSearch(3, data));
        Assert.assertEquals("For an element not found, linearSearch must return -1.", -1, Arrays2.linearSearch(4, data));
    }

} // class Arrays2Test
