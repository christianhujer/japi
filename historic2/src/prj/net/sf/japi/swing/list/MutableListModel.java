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

import javax.swing.ListModel;

/** A MutableListModel is a list model that additionally provides operations for adding and moving the list's elements.
 * @param <E> element type of this ListModel.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface MutableListModel<E> extends ListModel, Iterable<E> {

    /** {@inheritDoc} */
    E getElementAt(final int index);

    /** Addes the specified element to this model.
     * @param e Element to add.
     * @return <code>true</code> if the element was successfully added, otherwise <code>false</code>.
     */
    boolean add(E e);

    /** Removes the specified element from this model.
     * @param e Element to remove.
     * @return <code>true</code> if the element was successfully removed, otherwise <code>false</code>.
     */
    boolean remove(Object e);

    /** Moves the specified element to top.
     * @param e Element to move to top.
     * @return <code>true</code> if the move was successful, otherwise <code>false</code>.
     */
    boolean moveToTop(E e);

    /** Moves the element with the specified index to top.
     * @param index Index of the element to move to top.
     * @return <code>true</code> if the move was successful, otherwise <code>false</code>.
     */
    boolean moveToTop(int index);

    /** Moves the specified element up.
     * @param e Element to move up.
     * @return <code>true</code> if the move was successful, otherwise <code>false</code>.
     */
    boolean moveUp(E e);

    /** Moves the element with the specified index up.
     * @param index Index of the element to move up.
     * @return <code>true</code> if the move was successful, otherwise <code>false</code>.
     */
    boolean moveUp(int index);

    /** Moves the specified element down.
     * @param e Element to move down.
     * @return <code>true</code> if the move was successful, otherwise <code>false</code>.
     */
    boolean moveDown(E e);

    /** Moves the element with the specified index down.
     * @param index Index of the element to move down.
     * @return <code>true</code> if the move was successful, otherwise <code>false</code>.
     */
    boolean moveDown(int index);

    /** Moves the specified element to bottom.
     * @param e Element to move to bottom.
     * @return <code>true</code> if the move was successful, otherwise <code>false</code>.
     */
    boolean moveToBottom(E e);

    /** Moves the element with the specified index to bottom.
     * @param index Index of the element to move to bottom.
     * @return <code>true</code> if the move was successful, otherwise <code>false</code>.
     */
    boolean moveToBottom(int index);

} // interface MutableListModel
