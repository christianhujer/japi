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

package net.sf.japi.util;

/** Lightweight class for data pairs.
 * The hashcode of a pair is the combined hashcode of its values.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class Pair<T1,T2> {

    /** First data element. */
    private final T1 first;

    /** Second data element. */
    private final T2 second;

    /** Create a Pair.
     * @param first First data element
     * @param second Second data element
     */
    public Pair(final T1 first, final T2 second) {
        this.first = first;
        this.second = second;
    }

    /** Get first member of this pair.
     * @return first member of this pair
     */
    public T1 getFirst() {
        return first;
    }

    /** Get second member of this pair.
     * @return second member of this pair
     */
    public T2 getSecond() {
        return second;
    }

    /** {@inheritDoc} */
    @Override public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof Pair)) {
            return false;
        }
        final Pair<?,?> other = (Pair<?,?>) obj;
        return first.equals(other.first) && second.equals(other.second);
    }

    /** {@inheritDoc} */
    @Override public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }

} // class Pair
