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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/** A special Collection class that is like a Map but which allows duplicate keys.
 * Only duplicate key/value pairs aren't allowed.
 * The key therefor is named first, the value second.
 * Uniqueness is gained with Key/Value pairs.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
@SuppressWarnings({"BooleanMethodNameMustStartWithQuestion"})
public class Table<T1,T2> {

    /** The pairs of the table. */
    private final Set<Pair<T1,T2>> pairs = new HashSet<Pair<T1,T2>>();

    /** Create a table. */
    public Table() {
    }

    /** Completely Clear the table. */
    public void clear() {
        pairs.clear();
    }

    /** Get all firsts that match the second.
     * @param second second to match
     * @return all firsts that match second
     */
    public Collection<T1> getFirstsBySecond(final T2 second) {
        final Set<T1> firsts = new HashSet<T1>();
        for (final Pair<T1, T2> pair : pairs) {
            final T2 pairSecond = pair.getSecond();
            if (pairSecond.equals(second)) {
                firsts.add(pair.getFirst());
            }
        }
        return firsts;
    }

    /** Get all pairs that match the first.
     * @param first first to match
     * @return all pairs that match first
     */
    public Collection<Pair<T1,T2>> getPairsByFirst(final T1 first) {
        final Set<Pair<T1,T2>> pairsByFirst = new HashSet<Pair<T1,T2>>();
        for (final Pair<T1, T2> pair : pairs) {
            final T1 pairFirst = pair.getFirst();
            if (pairFirst.equals(first)) {
                pairsByFirst.add(pair);
            }
        }
        return pairsByFirst;
    }

    /** Get all pairs that match the second.
     * @param second second to match
     * @return all pairs that match second
     */
    public Collection<Pair<T1,T2>> getPairsBySecond(final T2 second) {
        final Set<Pair<T1,T2>> pairsBySecond = new HashSet<Pair<T1,T2>>();
        for (final Pair<T1, T2> pair : pairs) {
            final T2 pairSecond = pair.getSecond();
            if (pairSecond.equals(second)) {
                pairsBySecond.add(pair);
            }
        }
        return pairsBySecond;
    }

    /** Get all seconds that match the first.
     * @param first first to match
     * @return all seconds that match first
     */
    public Collection<T2> getSecondsByFirst(final T1 first) {
        final Set<T2> seconds = new HashSet<T2>();
        for (final Pair<T1, T2> pair : pairs) {
            final T1 pairFirst = pair.getFirst();
            if (pairFirst.equals(first)) {
                seconds.add(pair.getSecond());
            }
        }
        return seconds;
    }

    /** Put a pair into the table.
     * @param pair pair to put into the table
     * @return <code>true</code> if the table did not already contain that <var>pair</var>, otherwise <code>false</code>
     */
    public boolean putPair(final Pair<T1,T2> pair) {
        return pairs.add(pair);
    }

    /** Put a pair into the table.
     * @param t1 first of pair
     * @param t2 second of pair
     * @return <code>true</code> if the table did not already contain that <var>pair</var>, otherwise <code>false</code>
     */
    public boolean putPair(final T1 t1, final T2 t2) {
        return pairs.add(new Pair<T1,T2>(t1, t2));
    }

    /** Remove all pairs that match a first.
     * @param first first to match
     * @return whether a matching pair was removed
     * @retval <code>true</code> if at least one pair was removed
     * @retval <code>false</code> if no matching pairs were found
     */
    public boolean removeAllFirst(final T1 first) {
        boolean ret = false;
        //noinspection ForLoopWithMissingComponent
        for (final Iterator<Pair<T1,T2>> it = pairs.iterator(); it.hasNext();) {
            final Pair<T1,T2> pair = it.next();
            final T1 pairFirst = pair.getFirst();
            if (pairFirst.equals(first)) {
                it.remove();
                ret = true;
            }
        }
        return ret;
    }

    /** Remove all pairs that match a second.
     * @param second second to match
     * @return whether a matching pair was removed
     * @retval <code>true</code> if at least one pair was removed
     * @retval <code>false</code> if no matching pairs were found
     */
    public boolean removeAllSecond(final T2 second) {
        boolean ret = false;
        //noinspection ForLoopWithMissingComponent
        for (final Iterator<Pair<T1,T2>> it = pairs.iterator(); it.hasNext();) {
            final Pair<T1,T2> pair = it.next();
            final T2 pairSecond = pair.getSecond();
            if (pairSecond.equals(second)) {
                it.remove();
                ret = true;
            }
        }
        return ret;
    }

    /** Remove a pair from the table.
     * @param pair pair to remove
     * @return <code>true</code> if the table contained the <var>pair</var>, thus the <var>pair</var> was successfully removed, otherwise <code>false</code>
     */
    public boolean removePair(final Pair<T1,T2> pair) {
        return pairs.remove(pair);
    }

    /** Remove a pair into the table.
     * @param t1 first of pair
     * @param t2 second of pair
     * @return <code>true</code> if the table did not already contain that <var>pair</var>, otherwise <code>false</code>
     */
    public boolean removePair(final T1 t1, final T2 t2) {
        return pairs.remove(new Pair<T1,T2>(t1, t2));
    }

    /** Get the size of the table.
     * @return size of the table
     */
    public int size() {
        return pairs.size();
    }

} // class Table
