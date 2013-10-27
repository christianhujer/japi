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

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreePath;

/** Wraps a TreeTableModel in a TableModel.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class TreeTableModelTableModelAdapter extends AbstractTableModel {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The underlying JTree.
     * @serial include
     */
    private final JTree tree;

    /** The underlying TreeTableModel.
     * @serial include
     */
    private final TreeTableModel treeTableModel;

    /** Create a TreeTableModelAdapter.
     * @param treeTableModel TreeTableModel to adapt
     * @param tree JTree to adapt
     */
    public TreeTableModelTableModelAdapter(final TreeTableModel treeTableModel, final JTree tree) {
        this.tree = tree;
        this.treeTableModel = treeTableModel;

        tree.addTreeExpansionListener(new MyTreeExpansionListener());
    }

    /** {@inheritDoc} */
    public int getRowCount() {
        return tree.getRowCount();
    }

    /** {@inheritDoc} */
    public int getColumnCount() {
        return treeTableModel.getColumnCount();
    }

    /** {@inheritDoc} */
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return treeTableModel.getValueAt(nodeForRow(rowIndex), columnIndex);
    }

    /** {@inheritDoc} */
    @Override public Class<?> getColumnClass(final int columnIndex) {
        return treeTableModel.getColumnClass(columnIndex);
    }

    /** {@inheritDoc} */
    @Override public String getColumnName(final int column) {
        return treeTableModel.getColumnName(column);
    }

    /** {@inheritDoc} */
    @Override public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return treeTableModel.isCellEditable(nodeForRow(rowIndex), columnIndex);
    }

    protected Object nodeForRow(final int row) {
        final TreePath treePath = tree.getPathForRow(row);
        return treePath.getLastPathComponent();
    }

    /** {@inheritDoc} */
    @Override public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        treeTableModel.setValueAt(aValue, nodeForRow(rowIndex), columnIndex);
    }

    /** TreeExpansionListener for handling TreeExpansion events.
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     */
    private class MyTreeExpansionListener implements TreeExpansionListener {

        /** {@inheritDoc} */
        public void treeExpanded(final TreeExpansionEvent event) {
            fireTableDataChanged();
        }

        /** {@inheritDoc} */
        public void treeCollapsed(final TreeExpansionEvent event) {
            fireTableDataChanged();
        }

    } // class MyTreeExpansionListener

} // class TreeTableModelAdapter
