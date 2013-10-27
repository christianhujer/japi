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

package net.sf.japi.swing.treetable;

import java.io.Serializable;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;

/** The <code>TreeTableModel</code> interface specifies the methods the JTreeTable will use to interrogate a tabular tree data model.
 * <p />
 * The <code>JTreeTable</code> can be set up to display any data model which implements the <code>TreeTableModel</code> interface with a couple of lines of code:
 * <pre>
 *      TreeTableModel myData = new MyTreeTableModel();
 *      JTreeTable treeTable = new JTreeTable(myData);
 * </pre>
 * @param <R> root type
 * @param <T> node type
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface TreeTableModel<R, T> extends Serializable {

    /** Returns a child with a certain index.
     * @param parent Node to return child for
     * @param index Index of child
     * @return child node with index from parent or <code>null</code> if no child was found
     * @see TreeModel#getChild(Object, int)
     */
    T getChild(T parent, int index);

    /** Returns the number of available children.
     * @param node Node to get the number of children for.
     * @return number of available children
     * @see TreeModel#getChildCount(Object)
     */
    int getChildCount(T node);

    /** Returns the type for column number <code>column</code>.
     * @param column Column to get type for
     * @return type of <var>column</var>
     * @see TableModel#getColumnClass(int)
     */
    Class<?> getColumnClass(int column);

    /** Returns the number of availible columns.
     * @return number of available columns
     * @see TableModel#getColumnCount()
     */
    int getColumnCount();

    /** Returns the name for column number <code>column</code>.
     * @param column Column to get name for
     * @return name of <var>column</var>
     * @see TableModel#getColumnName(int)
     */
    String getColumnName(int column);

    /** Returns the root of the treetable.
     * @return root of the treetable
     */
    R getRoot();

    /** Returns the value to be displayed for node <code>node</code> at column number <code>column</code>.
     * @param node Node to get value of
     * @param column Column to get value of
     * @return object
     * @see TableModel#getValueAt(int, int)
     */
    Object getValueAt(T node, int column);

    /** Indicates whether the the value for node <code>node</code> at column number <code>column</code> is editable.
     * @param node Node to check
     * @param column Column to check
     * @return <code>true</code> if the cell is editable, otherwise <code>false</code>
     * @see TableModel#isCellEditable(int, int)
     */
    boolean isCellEditable(T node, int column);

    /** Returns <code<true</code> if node is a leaf.
     * @param node Node
     * @return <code>true</code> if node is a leaf, otherwise <code>false</code>
     */
    boolean isLeaf(T node);

    /** Sets the value for node <code>node</code> at column number <code>column</code>.
     * @param value Value to be set
     * @param node Node to set value at
     * @param column Column of value in the node
     * @see TableModel#setValueAt(Object, int, int)
     */
    void setValueAt(Object value, T node, int column);

} // interface TreeTableModel
