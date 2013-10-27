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

package net.sf.japi.swing.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.swing.AbstractListModel;

/**
 * A ListModel for {@link List}.
 * @param <E> element type for the collection to be a list model for.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class CollectionsListModel<E> extends AbstractListModel implements List<E> {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The list to delegate to.
     * @serial include
     */
    private final List<E> list;

    /**
     * Creates a new CollectionsListModel.
     * @param list List to delegate to.
     * @note This ListModel does not hold a copy of the supplied list but references it.
     *       All changes to the list model will be reflected in the supplied list and vice versa.
     * @note In order for events to be fired, this ListModel must be used for list modifications, not the underlying list.
     */
    @SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
    public CollectionsListModel(final List<E> list) {
        this.list = list;
    }

    /**
     * Creates a new CollectionsListModel backed by an ArrayList.
     */
    public CollectionsListModel() {
        list = new ArrayList<E>();
    }

    /** {@inheritDoc} */
    public int size() {
        return list.size();
    }

    /** {@inheritDoc} */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /** {@inheritDoc} */
    public boolean contains(final Object o) {
        return list.contains(o);
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator() {
        return list.iterator();
    }

    /** {@inheritDoc} */
    public Object[] toArray() {
        return list.toArray();
    }

    /** {@inheritDoc} */
    public <T> T[] toArray(final T[] ts) {
        //noinspection SuspiciousToArrayCall
        return list.toArray(ts);
    }

    /** {@inheritDoc} */
    public boolean add(final E e) {
        final int size = size();
        final boolean changed = list.add(e);
        if (changed) {
            fireIntervalRemoved(this, size, size);
        }
        return changed;
    }

    /** {@inheritDoc} */
    public boolean remove(final Object o) {
        final int index = list.indexOf(o);
        final boolean changed = list.remove(o);
        if (changed) {
            fireIntervalRemoved(this, index, index);
        }
        return changed;
    }

    /** {@inheritDoc} */
    public boolean containsAll(final Collection<?> objects) {
        return list.containsAll(objects);
    }

    /** {@inheritDoc} */
    public boolean addAll(final Collection<? extends E> es) {
        final int size = size();
        final boolean changed = list.addAll(es);
        if (changed) {
            fireIntervalAdded(this, size, size() - 1);
        }
        return changed;
    }

    /** {@inheritDoc} */
    public boolean addAll(final int i, final Collection<? extends E> es) {
        final int size = size();
        final boolean changed = list.addAll(i, es);
        if (changed) {
            fireIntervalAdded(this, i, i + size() - size);
        }
        return changed;
    }

    /** {@inheritDoc} */
    public boolean removeAll(final Collection<?> objects) {
        final int size = size();
        final boolean changed = list.removeAll(objects);
        if (changed) {
            fireIntervalRemoved(this, 0, size);
            fireIntervalAdded(this, 0, size());
        }
        return changed;
    }

    /** {@inheritDoc} */
    public boolean retainAll(final Collection<?> objects) {
        final int size = size();
        final boolean changed = list.retainAll(objects);
        if (changed) {
            fireIntervalRemoved(this, 0, size);
            fireIntervalAdded(this, 0, size());
        }
        return changed;
    }

    /** {@inheritDoc} */
    public void clear() {
        final int size = size() - 1;
        list.clear();
        if (size >= 0) {
            fireIntervalRemoved(this, 0, size);
        }
    }

    /** {@inheritDoc} */
    @Override public boolean equals(final Object obj) {
        return obj instanceof CollectionsListModel && list.equals(((CollectionsListModel) obj).list);
    }

    /** {@inheritDoc} */
    @Override public int hashCode() {
        return list.hashCode();
    }

    /** {@inheritDoc} */
    public E get(final int i) {
        return list.get(i);
    }

    /** {@inheritDoc} */
    public E set(final int i, final E e) {
        final E oldElement = list.set(i, e);
        fireContentsChanged(this, i, i);
        return oldElement;
    }

    /** {@inheritDoc} */
    public void add(final int i, final E e) {
        list.add(i, e);
        fireIntervalAdded(this, i, i);
    }

    /** {@inheritDoc} */
    public E remove(final int i) {
        final E removedElement = list.remove(i);
        fireIntervalRemoved(this, i, i);
        return removedElement;
    }

    /** {@inheritDoc} */
    public int indexOf(final Object o) {
        return list.indexOf(o);
    }

    /** {@inheritDoc} */
    public int lastIndexOf(final Object o) {
        return list.lastIndexOf(o);
    }

    /** {@inheritDoc} */
    public ListIterator<E> listIterator() {
        return list.listIterator();
    }

    /** {@inheritDoc} */
    public ListIterator<E> listIterator(final int i) {
        return list.listIterator(i);
    }

    /** {@inheritDoc} */
    public List<E> subList(final int i, final int i1) {
        return list.subList(i, i1);
    }

    /** {@inheritDoc} */
    public int getSize() {
        return size();
    }

    /** {@inheritDoc} */
    public E getElementAt(final int i) {
        return get(i);
    }

} // class CollectionsListModel
