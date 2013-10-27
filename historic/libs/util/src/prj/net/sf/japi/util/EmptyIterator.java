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

/** An iterator that never returns elements and thus is always empty.
 * @param <E> Iterator element type.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class EmptyIterator<E> implements Iterator<E> {

    /** Create an Empty Iterator.
     * Please note that this class does not hold any state.
     * The Constructor only exists for convenient Generics programming.
     * It makes sense to store an empty iterator somewhere and reuse it, eventually even in a static final variable.
     */
    public EmptyIterator() {
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
        return false;
    }

    /** {@inheritDoc} */
    public E next() throws NoSuchElementException {
        throw new NoSuchElementException("No more elements");
    }

    /** {@inheritDoc} */
    public void remove() {
        throw new UnsupportedOperationException();
    }

} // class EmptyIterator
