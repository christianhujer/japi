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

package net.sf.japi.swing.prefs.keys;

import org.jetbrains.annotations.Nullable;

/** Interface for simple nodes.
 * @param <C> type for children of this node.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface SimpleNode<C> {

    /** Gets a child with a specific index.
     * @param index Index of desired child
     * @return child with index or <code>null</code> if no such child.
     */
    @Nullable C getChild(int index);

    /** Get the number of children.
     * @return number of children
     */
    int getChildCount();

    /** Get the value of this node at a certain value index.
     * @param column Index of desired value
     * @return value at index
     */
    Object getValueAt(int column);

} // interface SimpleNode
