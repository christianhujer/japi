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

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Iterator / Iterable decorator that skips elements which are <code>null</code>.
 * @param <E> Iterator element type.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class NotNullIterator<E> implements Iterator<E> {

    /** The iterator to use. */
    @NotNull private final Iterator<E> iterator;

    /** Prefetched next element. */
    @Nullable private E next;

    /** Create a NotNullIterator over another Iterator.
     * @param iterator Iterator to wrap / decorate.
     */
    public NotNullIterator(@NotNull final Iterator<E> iterator) {
        this.iterator = iterator;
        prefetchNext();
    }

    /** Prefetches the next element. */
    private void prefetchNext() {
        //noinspection NestedAssignment
        while (iterator.hasNext() && (next = iterator.next()) == null) { // NOPMD
            // nothing to do.
        }
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
        return next != null;
    }

    /** {@inheritDoc} */
    @NotNull public E next() {
        if (next == null) {
            throw new NoSuchElementException();
        }
        try {
            return next;
        } finally {
            prefetchNext();
        }
    }

    /** {@inheritDoc} */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /** An Iterable adapter for NotNullIterator.
     * @param <T> Iterable element type.
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     */
    public static class NotNullIterable<T> implements Iterable<T> {

        /** The iterable to iterate over. */
        @NotNull private final Iterable<T> iterable;

        public NotNullIterable(@NotNull final Iterable<T> iterable) {
            this.iterable = iterable;
        }

        /** {@inheritDoc} */
        @NotNull public Iterator<T> iterator() {
            return new NotNullIterator<T>(iterable.iterator());
        }

    } // class NotNullIterable

} // class NotNullIterator
