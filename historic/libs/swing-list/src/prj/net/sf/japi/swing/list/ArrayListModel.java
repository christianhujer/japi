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

package net.sf.japi.swing.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** ArrayList-backed implementation of {@link MutableListModel}.
 * @param <E> element type of this ListModel.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ArrayListModel<E> extends AbstractMutableListModel<E> {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The ArrayList that backs this ArrayListModel.
     * @serial include
     */
    private final List<E> list = new ArrayList<E>();

    /** Creates a new ArrayListModel.
     */
    public ArrayListModel() {
    }

    /** {@inheritDoc} */
    public int getSize() {
        return list.size();
    }

    /** {@inheritDoc} */
    public E getElementAt(final int index) {
        return list.get(index);
    }

    /** {@inheritDoc} */
    public boolean add(final E e) {
        if (list.contains(e)) {
            return false;
        }
        final int index;
        final boolean retVal;
        synchronized (list) {
            index = list.size();
            retVal = list.add(e);
        }
        fireIntervalAdded(this, index, index);
        return retVal;
    }

    /** {@inheritDoc} */
    public boolean remove(final Object e) {
        if (!list.contains(e)) {
            throw new IllegalArgumentException("Element " + e + " not part of " + this);
        }
        final int index;
        final boolean retVal;
        synchronized (list) {
            index = list.indexOf(e);
            retVal = list.remove(e);
        }
        fireIntervalRemoved(this, index, index);
        return retVal;
    }

    /** {@inheritDoc} */
    public boolean moveToTop(final E e) {
        synchronized (list) {
            if (!list.contains(e)) {
                throw new IllegalArgumentException("Element " + e + " not part of " + this);
            }
            final int oldIndex = list.indexOf(e);
            final int newIndex = 0;
            return move(e, oldIndex, newIndex);
        }

    }

    /** {@inheritDoc} */
    public boolean moveToTop(final int index) {
        return move(null, index, 0);
    }

    /** {@inheritDoc} */
    public boolean moveUp(final E e) {
        synchronized (list) {
            if (!list.contains(e)) {
                throw new IllegalArgumentException("Element " + e + " not part of " + this);
            }
            final int oldIndex = list.indexOf(e);
            final int newIndex = oldIndex - 1;
            return move(e, oldIndex, newIndex);
        }
    }

    /** {@inheritDoc} */
    public boolean moveUp(final int index) {
        return move(null, index, index - 1);
    }

    /** {@inheritDoc} */
    public boolean moveDown(final E e) {
        synchronized (list) {
            if (!list.contains(e)) {
                throw new IllegalArgumentException("Element " + e + " not part of " + this);
            }
            final int oldIndex = list.indexOf(e);
            final int newIndex = oldIndex + 1;
            return move(e, oldIndex, newIndex);
        }
    }

    /** {@inheritDoc} */
    public boolean moveDown(final int index) {
        return move(null, index, index + 1);
    }

    /** {@inheritDoc} */
    public boolean moveToBottom(final E e) {
        synchronized (list) {
            if (!list.contains(e)) {
                throw new IllegalArgumentException("Element " + e + " not part of " + this);
            }
            final int oldIndex = list.indexOf(e);
            final int newIndex = list.size() - 1;
            return move(e, oldIndex, newIndex);
        }
    }

    /** {@inheritDoc} */
    public boolean moveToBottom(final int index) {
        return move(null, index, list.size() - 1);
    }

    /** Performs the move operation of an element.
     * @param e Element to move
     * @param oldIndex old index of the element
     * @param newIndex new index of the element
     * @return <code>true</code> if the element was moved and the event was fired, otherwise <code>false</code>.
     */
    private boolean move(@Nullable final E e, final int oldIndex, final int newIndex) {
        synchronized (list) {
            if (oldIndex == newIndex || oldIndex < 0 || newIndex < 0 || newIndex >= list.size()) {
                return false;
            }
            assert e == null || list.indexOf(e) == oldIndex;
            final E moving = list.remove(oldIndex);
            list.add(newIndex, moving);
            assert e == null || list.indexOf(e) == newIndex;
            fireContentsChanged(this, oldIndex, newIndex);
            return true;
        }
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator() {
        return new IteratorWrapper(list.iterator());
    }

    /** Iterator wrapper.
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     */
    private class IteratorWrapper implements Iterator<E> {

        /** Iterator that's wrapped. */
        private final Iterator<E> iterator;

        /** The current element, needed in {@link #remove()}. */
        private E currentElement = null;

        /** Create an IteratorWrapper.
         * @param iterator Iterator to wrap.
         */
        IteratorWrapper(@NotNull final Iterator<E> iterator) {
            this.iterator = iterator;
        }

        /** {@inheritDoc} */
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /** {@inheritDoc} */
        public E next() {
            currentElement = iterator.next();
            return currentElement;
        }

        /** {@inheritDoc} */
        public void remove() {
            synchronized (list) {
                final int index = list.indexOf(currentElement);
                iterator.remove();
                fireIntervalRemoved(this, index, index);
            }
        }
    }
} // class ArrayListModel
