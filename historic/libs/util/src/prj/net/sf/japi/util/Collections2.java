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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import net.sf.japi.util.filter.Filter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** This class provides some additional utility methods you might miss in {@link Collections}.
 * It is named Collections2 so you have no problems using both, this class and <code>java.util.Collections</code> (no name conflict).
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @see Collections
 */
public final class Collections2 {

    /** Class of java.util.Arrays.ArrayList */
    private static final Class<? extends List<?>> AL;
    static {
        Class<? extends List<?>> c = null;
        try {
            c = (Class<? extends List<?>>) Class.forName("java.util.Arrays.ArrayList");
        } catch (ClassNotFoundException ignore) { /* ignore, null is okay then. */ }
        AL = c;
    }

    /** Utility class - do not instanciate. */
    private Collections2() {
    }

    /** Returns a collection only containing those elements accepted by the given filter.
     * The original collection remains unmodified.
     * This method tries its best to create a new, empty variant of the Collection passed in <var>c</var>:
     * <ol>
     *  <li>it's tried to create a new instance from the class of <var>c</var> using Reflection</li>
     *  <li>it's tried to {@link Object#clone()} <var>c</var> using Reflection and {@link Collection#clear()} the clone</li>
     *  <li>it's tried if c is a {@link RandomAccess} {@link List}, in which case an {@link ArrayList} is returned as an alternative</li>
     * </ol>
     * @param c Collection to filter
     * @param filter Filter to use for <var>c</var>
     * @return collection containing only those elements accepted by the filter or <code>null</code> if the Collection could not be created.
     */
    @Nullable public static <T, C extends Collection<T>> C filter(@NotNull final C c, @NotNull final Filter<? super T> filter) {
        @Nullable C filtered = null;
        try {
            filtered = (C) c.getClass().newInstance();
        } catch (final Exception ignore) { /* ignore, check is done on null. */ }
        if (filtered == null && c instanceof Cloneable) {
            try {
                filtered = (C) c.getClass().getMethod("clone").invoke(c);
                c.clear();
            } catch (final Exception ignore) {
                filtered = null;
            }
        }
        if (filtered == null && (AL != null && AL.isInstance(c) || c instanceof List && c instanceof RandomAccess)) {
            filtered = (C) new ArrayList<T>();
        }
        if (filtered != null) { // only fill if filtered collection could be created.
            for (final T o : c) {
                if (filter.accept(o)) {
                    filtered.add(o);
                }
            }
        }
        return filtered;
    }

    /** Removes all elements from a collection not accepted by the given filter.
     * The original collection is modified.
     * It is required that the collection is modifiable.
     * @param collection Collection to filter
     * @param filter Filter to use for <var>c</var>
     * @return number of elements removed
     */
    public static <T, C extends Collection<T>> int removeFilter(@NotNull final C collection, @NotNull final Filter<? super T> filter) {
        int removed = 0;
        for (final T o : collection) {
            if (!filter.accept(o)) {
                collection.remove(o);
                removed++;
            }
        }
        return removed;
    }

    /** Checks whether a list is sorted.
     * @param list List to check
     * @return <code>true</code> if <var>list</var> is sorted, otherwise <code>false</code>
     * @see Comparable
     * @see Collections#sort(List)
     */
    public static <T extends Comparable<? super T>> boolean isSorted(@NotNull final List<T> list) {
        if (list.size() < 2) {
            // empty lists or lists with only 1 element are always sorted, no need to check
            return true;
        }
        if (list instanceof RandomAccess) {
            final int n = list.size();
            for (int i = 0; i < n; i++) {
                if (list.get(i).compareTo(list.get(i + 1)) < 0) {
                    return false;
                }
            }
            return true;
        } else {
            final Iterator<T> it = list.iterator();
            assert list.size() >= 1; // list.size() is >= 2, but >= 1 is sufficient
            T previous = it.next(); // save because list.size() >= 1;
            while (it.hasNext()) {
                final T current = it.next();
                if (previous.compareTo(current) < 0) {
                    return false;
                }
                previous = current;
            }
            return true;
        }
    }

    /** Checks whether a list is sorted.
     * @param list List to check
     * @param c Comparator to use for comparing list elements or <code>null</code> for natural order
     * @return <code>true</code> if <var>list</var> is sorted, otherwise <code>false</code>
     * @see Comparator
     * @see Collections#sort(List,Comparator)
     */
    public static <T> boolean isSorted(@NotNull final List<T> list, @Nullable final Comparator<? super T> c) {
        if (c == null) {
            //noinspection unchecked,RawUseOfParameterizedType
            return isSorted((List) list);
        }
        if (list.size() < 2) {
            // empty lists or lists with only 1 element are always sorted, no need to check
            return true;
        }
        if (list instanceof RandomAccess) {
            final int n = list.size();
            for (int i = 0; i < n; i++) {
                if (c.compare(list.get(i), list.get(i + 1)) < 0) {
                    return false;
                }
            }
            return true;
        } else {
            final Iterator<T> it = list.iterator();
            assert list.size() >= 1;
            T previous = it.next();
            while (it.hasNext()) {
                final T current = it.next();
                if (c.compare(previous, current) < 0) {
                    return false;
                }
                previous = current;
            }
            return true;
        }
    }

    /** Collects items from an Iterator into a Collection.
     * @param c Collection to which the items shall be collected.
     * @param iterator Iterator from which to collect items.
     * @return <var>c</var> after the items from <var>iterator</var> have been added to it.
     */
    public static <T, C extends Collection<T>> C collect(final C c, final Iterator<T> iterator) {
        while (iterator.hasNext()) {
            c.add(iterator.next());
        }
        return c;
    }

} // class Collections2
