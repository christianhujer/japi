/* JAPI - (Yet another (hopefully) useful) Java API
 *
 * Copyright (C) 2004-2006 Christian Hujer
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

package net.sf.japi.io;

/** A class to get nibbles from byte sequences.
 * This class works in sequentially numbered big endian (network byte) order, which means that the first nibble (0-nibble) is the high nibble of the
 * first byte (high byte high nibble).
 * You may also call this "high nibble first".
 * The long value 0x0123456789ABCDEFl reflects the nibble indices in longs.
 * Other datatypes work similarly:
 * The int value 0x01234567 reflects the nibble indices in ints.
 * <p />
 * Binary nibbles are returned as ints because the general contract of I/O-methods as in package <code>java.io</code> is to expect and return single
 * bytes being stored in ints and it saves neither space nor performance to return bytes instead of ints.
 * <p />
 * When needing chars, you should invoke those methods returning chars directly (single method invocation) instead of first getting the nibble and then
 * converting it to a char (two method invocations) because inlining compilers will give you a better performance then.
 * <p />
 * Everything in this class is final for performance reasons: Final methods can be sort of inlined by some compilers.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
@SuppressWarnings({"UtilityClass", "ClassWithTooManyMethods"})
public final class Nibbles {

    /** Lowercase nibble characters. */
    private static final char[] LCASE_NIBBLES = "0123456789abcdef".toCharArray();

    /** Uppercase nibble characters. */
    private static final char[] UCASE_NIBBLES = "0123456789ABCDEF".toCharArray();

    /** This class cannot be instanciated. */
    private Nibbles() {}

    /** Get a nibble from a byte array.
     * @param data byte array to get nibble from
     * @param index nibble number
     * @return nibble value with nibble number
     */
    public static int getNibble(final byte[] data, final int index) {
        return getNibble(data[index >> 1], index & 1);
    }

    /** Get all nibbles from a byte array.
     * @param data byte array to get nibbles from
     * @return byte array with nibbles; the length of the returned byte array is twice the length of the argument byte array
     */
    public static byte[] getNibbles(final byte[] data) {
        return getNibbles(data, 0, data.length << 1);
    }

    /** Get some nibbles from a byte array.
     * @param data byte array to get nibbles from
     * @param startIndex first nibble index to get nibble for
     * @param length number of nibbles to get
     * @return byte array with nibbles
     */
    public static byte[] getNibbles(final byte[] data, final int startIndex, final int length) {
        if (length < 0) { throw new IllegalArgumentException("Length may not be negative: " + length); }
        if (startIndex < 0) { throw new IllegalArgumentException("Start index may not be negative: " + startIndex); }
        if (startIndex >> 1 > data.length) { throw new IllegalArgumentException("Start index out of range: " + startIndex); }
        if (startIndex + length >> 1 > data.length) { throw new IllegalArgumentException("Length out of range: " + length); }
        final byte[] nibbles = new byte[length];
        for (int i = 0, j = startIndex; i < length; i++, j++) {
            nibbles[i] = (byte) (0xF & data[j >> 1] >> 4 * (1 - j & 1));
        }
        return nibbles;
    }

    /** Get a nibble from a byte value.
     * @param data byte value to get nibble from
     * @param index nibble number
     * @return nibble value with nibble number
     */
    public static int getNibble(final byte data, final int index) {
        return getNibble((long) data, index + 14);
    }

    /** Get a nibble from a short value.
     * @param data short value to get nibble from
     * @param index nibble number
     * @return nibble value with nibble number
     */
    public static int getNibble(final short data, final int index) {
        return getNibble((long) data, index + 12);
    }

    /** Get a nibble from an int value.
     * @param data int value to get nibble from
     * @param index nibble number
     * @return nibble value with nibble number
     */
    public static int getNibble(final int data, final int index) {
        return getNibble((long) data, index + 8);
    }

    /** Get a nibble from a long value.
     * @param data int value to get nibble from
     * @param index nibble number
     * @return nibble value with nibble number
     */
    public static int getNibble(final long data, final int index) {
        return 0xF & (int) (data >> 4 * (15 - index));
    }

    /** Get a nibble from a char value.
     * @param data char value to get nibble from
     * @param index nibble nubmer
     * @return nibble value with nibble number
     */
    public static int getNibble(final char data, final int index) {
        return getNibble((long) data, index + 12);
    }

    /** Get a nibble from a float value (raw format).
     * To get the nibble from a float value in logical format, convert the float to an int yourself using {@link Float#floatToIntBits(float)}.
     * @param data float value to get nibble from
     * @param index nibble nubmer
     * @return nibble value with nibble number
     */
    public static int getNibble(final float data, final int index) {
        return getNibble((long) Float.floatToRawIntBits(data), index + 8);
    }

    /** Get a nibble from a double value (raw format).
     * To get the nibble from a double value in logical format, convert the float to an int yourself using {@link Double#doubleToLongBits(double)}.
     * @param data double value to get nibble from
     * @param index nibble nubmer
     * @return nibble value with nibble number
     */
    public static int getNibble(final double data, final int index) {
        return getNibble(Double.doubleToRawLongBits(data), index);
    }

    /** Get a lowercase character for a nibble.
     * @param nibble nibble to get character for
     * @return lowercase character for <var>data</var>
     * @throws IllegalArgumentException if <code>data</code> isn't a single nibble
     */
    public static char getNibbleLC(final int nibble) {
        try {
            return LCASE_NIBBLES[nibble];
        } catch (final ArrayIndexOutOfBoundsException ignore) {
            throw new IllegalArgumentException("Not a nibble: " + nibble);
        }
    }

    /** Get a uppercase character for a nibble.
     * @param nibble nibble to get character for
     * @return uppercase character for <var>data</var>
     * @throws IllegalArgumentException if <code>data</code> isn't a single nibble
     */
    public static char getNibbleUC(final int nibble) {
        try {
            return UCASE_NIBBLES[nibble];
        } catch (final ArrayIndexOutOfBoundsException ignore) {
            throw new IllegalArgumentException("Not a nibble: " + nibble);
        }
    }

    /** Get a lowercase character reflecting a nibble from a byte array.
     * Shorthand for getNibbleLC(getNibble(data, index)).
     * @param data byte array to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleLC(final byte[] data, final int index) {
        return getNibbleLC(getNibble(data, index));
    }

    /** Get an uppercase character reflecting a nibble from a byte array.
     * @param data byte array to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleUC(final byte[] data, final int index) {
        return getNibbleUC(getNibble(data, index));
    }

    /** Get a lowercase character reflecting a nibble from a byte.
     * Shorthand for getNibbleLC(getNibble(data, index)).
     * @param data byte to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleLC(final byte data, final int index) {
        return getNibbleLC(getNibble(data, index));
    }

    /** Get an uppercase character reflecting a nibble from a byte.
     * @param data byte to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleUC(final byte data, final int index) {
        return getNibbleUC(getNibble(data, index));
    }

    /** Get a lowercase character reflecting a nibble from a short.
     * Shorthand for getNibbleLC(getNibble(data, index)).
     * @param data short to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleLC(final short data, final int index) {
        return getNibbleLC(getNibble(data, index));
    }

    /** Get an uppercase character reflecting a nibble from a short.
     * @param data short to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleUC(final short data, final int index) {
        return getNibbleUC(getNibble(data, index));
    }

    /** Get a lowercase character reflecting a nibble from a int.
     * Shorthand for getNibbleLC(getNibble(data, index)).
     * @param data int to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleLC(final int data, final int index) {
        return getNibbleLC(getNibble(data, index));
    }

    /** Get an uppercase character reflecting a nibble from a int.
     * @param data int to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleUC(final int data, final int index) {
        return getNibbleUC(getNibble(data, index));
    }

    /** Get a lowercase character reflecting a nibble from a long.
     * Shorthand for getNibbleLC(getNibble(data, index)).
     * @param data long to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleLC(final long data, final int index) {
        return getNibbleLC(getNibble(data, index));
    }

    /** Get an uppercase character reflecting a nibble from a long.
     * @param data long to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleUC(final long data, final int index) {
        return getNibbleUC(getNibble(data, index));
    }

    /** Get a lowercase character reflecting a nibble from a char.
     * Shorthand for getNibbleLC(getNibble(data, index)).
     * @param data char to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleLC(final char data, final int index) {
        return getNibbleLC(getNibble(data, index));
    }

    /** Get an uppercase character reflecting a nibble from a char.
     * @param data char to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleUC(final char data, final int index) {
        return getNibbleUC(getNibble(data, index));
    }

    /** Get a lowercase character reflecting a nibble from a float.
     * Shorthand for getNibbleLC(getNibble(data, index)).
     * @param data float to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleLC(final float data, final int index) {
        return getNibbleLC(getNibble(data, index));
    }

    /** Get an uppercase character reflecting a nibble from a float.
     * @param data float to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleUC(final float data, final int index) {
        return getNibbleUC(getNibble(data, index));
    }

    /** Get a lowercase character reflecting a nibble from a double.
     * Shorthand for getNibbleLC(getNibble(data, index)).
     * @param data double to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleLC(final double data, final int index) {
        return getNibbleLC(getNibble(data, index));
    }

    /** Get an uppercase character reflecting a nibble from a double.
     * @param data double to get nibble from
     * @param index nibble number
     * @return nibble character
     */
    public static char getNibbleUC(final double data, final int index) {
        return getNibbleUC(getNibble(data, index));
    }

} // class Nibbles
