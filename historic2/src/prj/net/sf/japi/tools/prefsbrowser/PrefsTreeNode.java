/*
 * Copyright (C) 2009  Christian Hujer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.japi.tools.prefsbrowser;

import org.jetbrains.annotations.NotNull;

/** Superclass of PrefsTreeNode.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface PrefsTreeNode {

    /** Get the value at a specific column.
     * @param column Column to get value for
     * @return value for column
     */
    @NotNull Object getValueAt(int column);

    /** Get a child with a specific index.
     * @param index Index of child to get (<code>0 &lt;= <var>index</var> &lt; {@link #getChildCount()}</code>)
     * @return child node
     */
    @NotNull PrefsTreeNode getChild(int index);

    /** Get the number of children.
     * @return number of children
     */
    int getChildCount();

} // class PrefsTreeNode
