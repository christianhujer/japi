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

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;

/** Combined Iterator/Iterable proxy for enumerations.
 * Instances are not reusable.
 * @param <E> Enumeration / Iterator element type.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class EnumerationIterator<E> implements Iterable<E>, Iterator<E> {

    /** Enumeration to iterate over. */
    @NotNull private final Enumeration<E> enumeration;

    /** Create an Iterable based on an Enumeration, useful for foreach-loops.
     * @param enumeration Enumeration to iterate over
     */
    public EnumerationIterator(@NotNull final Enumeration<E> enumeration) {
        this.enumeration = enumeration;
    }

    /** {@inheritDoc} */
    @NotNull public Iterator<E> iterator() {
        return this;
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    /** {@inheritDoc} */
    // SuppressWarnings here because of bug in IntelliJ.
    // enumeration.nextElement actually can throw a NoSuchElementException.
    @SuppressWarnings({"IteratorNextCanNotThrowNoSuchElementException"})
    public E next() throws NoSuchElementException {
        return enumeration.nextElement();
    }

    /** {@inheritDoc} */
    public void remove() {
        throw new UnsupportedOperationException();
    }

} // class EnumerationIterator
