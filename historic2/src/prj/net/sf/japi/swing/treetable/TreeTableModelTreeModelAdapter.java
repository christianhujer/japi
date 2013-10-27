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
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * TODO
 * @param <R> root type
 * @param <T> node type
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class TreeTableModelTreeModelAdapter<R, T> implements TreeModel, Serializable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The Event listener list for registering TreeModelListeners.
     * @serial include
     */
    private final EventListenerList listenerList = new EventListenerList();

    /** The underlying TreeTableModel.
     * @serial include
     */
    private final TreeTableModel<R, T> treeTableModel;

    public TreeTableModelTreeModelAdapter(final TreeTableModel<R, T> treeTableModel) {
        this.treeTableModel = treeTableModel;
    }

    /** {@inheritDoc} */
    public Object getRoot() {
        return treeTableModel.getRoot();
    }

    /** {@inheritDoc} */
    public Object getChild(final Object parent, final int index) {
        return treeTableModel.getChild((T) parent, index);
    }

    /** {@inheritDoc} */
    public int getChildCount(final Object parent) {
        return treeTableModel.getChildCount((T) parent);
    }

    /** {@inheritDoc} */
    public boolean isLeaf(final Object node) {
        return treeTableModel.isLeaf((T) node);
    }

    /** {@inheritDoc} */
    public void valueForPathChanged(final TreePath path, final Object newValue) {
    }

    /** {@inheritDoc} */
    public int getIndexOfChild(final Object parent, final Object child) {
        return 0;  // TODO:2009-02-24:christianhujer:Implement.
    }

    /** {@inheritDoc} */
    public void addTreeModelListener(final TreeModelListener l) {
        // TODO:2009-02-24:christianhujer:Implement.
    }

    /** {@inheritDoc} */
    public void removeTreeModelListener(final TreeModelListener l) {
        // TODO:2009-02-24:christianhujer:Implement.
    }

} // class TreeTableModelTreeModelAdapter
