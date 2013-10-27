/* JAPI - (Yet another (hopefully) useful) Java API
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

package net.sf.japi.io;

/** A class with methods for converting BCD data from and to Binary data.
 * Currently only int is supported.
 * Probably <code>net.sf.japi.io</code> is not the ideal package for this class, yet it seems most appropriate.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
@SuppressWarnings({"UtilityClass", "ClassNamingConvention"})
public final class BCD {

    /** Private constructor - no instances needed. */
    private BCD() {
    }

    /** Convert a BCD value to an int value.
     * @param bcd BCD value
     * @return int value for bcd value
     */
    public static int bcd2int(final int bcd) {
        int ret = 0;
        for (int i = 0; i < 8; i++) {
            ret += (0xF & bcd >> 4 * i) * base10(i);
        }
        return ret;
    }

    /** Convert an int value to a BCD value.
     * @param val int value
     * @return bcd value for int value
     */
    public static int int2bcd(final int val) {
        int bcd = 0;
        int work = val;
        for (int i = 0; i < 8; i++) {
            final int nibble = work % 10;
            work /= 10;
            bcd += nibble * (1 << 4 * i);
        }
        return bcd;
    }

    /** Check whether a BCD value is correct.
     * If the supplied value contains a nibble with a value &gt;= 10, it will throw an IllegalArgumentException.
     * Otherwise it will simply return.
     * @param bcd number to check
     */
    public static void check(final int bcd) {
        for (int i = 0; i < 8; i++) {
            if ((0xF & bcd >> 4 * i) >= 10) {
                throw new IllegalArgumentException();
            }
        }
    }

    /** Check whether a BCD value is correct.
     * @param bcd bcd value to check
     * @return <code>true</code> if supplied value is bcd, otherwise <code>false</code>
     */
    public static boolean isBcd(final int bcd) {
        for (int i = 0; i < 8; i++) {
            if ((0xF & bcd >> 4 * i) >= 10) {
                return false;
            }
        }
        return true;
    }

    /** Return the 10 base (10^n).
     * @param n power
     * @return <code>10^<var>n</var></code>
     */
    public static int base10(final int n) {
        int ret = 1;
        for (int i = 0; i < n; i++) {
            ret *= 10;
        }
        return ret;
    }

    /** Perform a BCD correction.
     * For each nibble that contains a value greater than or equal 10, the next nibble will be incremented.
     * Any overflow is added to the low byte, so an overflow bcd value will toggle even/odd.
     * @param bcd bcd value to correct
     * @return corrected bcd value
     */
    public static int correct(final int bcd) {
        int newBcd = bcd;
        for (int i = 0; i < 8; i++) {
            if ((0xF & bcd >> 4 * i) >= 10) {
                newBcd -= 10 << 4 * i;
                newBcd += 1 << 4 * (i + 1);
            }
        }
        assert isBcd(newBcd);
        return newBcd;
    }

} // class BCD
