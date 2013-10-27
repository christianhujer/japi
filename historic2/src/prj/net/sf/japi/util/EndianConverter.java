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

package net.sf.japi.util;

/** Class to convert data from little endian to big endian and vice versa.
 * Since the conversion is symmetric, there are no special conversion methods, just generic ones, e.g. the same method is used to convert a little
 * endian integer to a big endian integer and vice versa.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
@SuppressWarnings({"UtilityClass", "MagicNumber"})
public final class EndianConverter {

    /** Private constructor to prevent instantiation. */
    private EndianConverter() {
    }

    /** Convert a double.
     * This method changes byte order from 1, 2, 3, 4, 5, 6, 7, 8 to 8, 7, 6, 5, 4, 3, 2, 1.
     * @param d double to convert
     * @return converted double
     */
    public static double swapEndianess(final double d) {
        return Double.longBitsToDouble(swapEndianess(Double.doubleToRawLongBits(d)));
    }

    /** Convert a float.
     * This method changes byte order from 1, 2, 3, 4 to 4, 3, 2, 1.
     * @param f float to convert
     * @return converted float
     */
    public static float swapEndianess(final float f) {
        return Float.intBitsToFloat(swapEndianess(Float.floatToRawIntBits(f)));
    }

    /** Convert a long.
     * This method changes byte order from 1, 2, 3, 4, 5, 6, 7, 8 to 8, 7, 6, 5, 4, 3, 2, 1.
     * @param l long to convert
     * @return converted long
     */
    @SuppressWarnings("OverlyComplexBooleanExpression")
    public static long swapEndianess(final long l) {
        return    (l & 0x00000000000000FFL) <<  56
                | (l & 0x000000000000FF00L) <<  40
                | (l & 0x0000000000FF0000L) <<  24
                | (l & 0x00000000FF000000L) <<   8
                | (l & 0x000000FF00000000L) >>>  8
                | (l & 0x0000FF0000000000L) >>> 24
                | (l & 0x00FF000000000000L) >>> 40
                | (l & 0xFF00000000000000L) >>> 56;
    }

    /** Convert an int.
     * This method changes byte order from 1, 2, 3, 4 to 4, 3, 2, 1.
     * @param i int to convert
     * @return converted int
     */
    @SuppressWarnings("OverlyComplexBooleanExpression")
    public static int swapEndianess(final int i) {
        return    (i & 0x000000FF) <<  24
                | (i & 0x0000FF00) <<   8
                | (i & 0x00FF0000) >>>  8
                | (i & 0xFF000000) >>> 24;
    }

    /** Convert a char.
     * This method swaps low and high byte.
     * @param c char to convert
     * @return converted char
     */
    public static char swapEndianess(final char c) {
        return (char) ((c & 0x00FF) <<  8 | (c & 0xFF00) >>> 8);
    }

    /** Convert a short.
     * This method swaps low and high byte.
     * @param s short to convert
     * @return converted short
     */
    public static short swapEndianess(final short s) {
        return (short) ((s & 0x00FF) <<  8 | (s & 0xFF00) >>> 8);
    }

    /** Convert a byte.
     * This method does nothing and exists for convenience only.
     * @param b byte to convert
     * @return converted byte (same as b)
     */
    public static byte swapEndianess(final byte b) {
        return b;
    }

    /** Convert a boolean.
     * This method does nothing and exists for convenience only.
     * @param b boolean to convert
     * @return converted boolean (same as b)
     */
    @SuppressWarnings({"BooleanMethodNameMustStartWithQuestion"})
    public static boolean swapEndianess(final boolean b) {
        return b;
    }

} // class EndianConverter
