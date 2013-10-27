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

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

/** Abstract base implementation of TreeTableModel.
 * @param <R> root type
 * @param <T> node type
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public abstract class AbstractTreeTableModel<R, T> implements TreeTableModel<R, T> {

    /** The tree root node content object. */
    private final R root;

    /** The Event listener list for registering TreeModelListeners. */
    private final EventListenerList listenerList = new EventListenerList();

    /** Create an AbstractTreeTableModel.
     * @param root tree root node content object
     */
    protected AbstractTreeTableModel(final R root) {
        this.root = root;
    }

    /** {@inheritDoc} */
    public R getRoot() {
        return root;
    }

    /** {@inheritDoc} */
    public boolean isLeaf(final T node) {
        return getChildCount(node) == 0;
    }

    /** Returns the index of the specified child.
     * @param parent Parent within which the index of the child shall be returned.
     * @param child Child of which the index shall be returned.
     * @return The index of child in parent or -1 if child is not a child of parent.
     */
    @SuppressWarnings({"TypeMayBeWeakened"})
    public int getIndexOfChild(final T parent, final T child) {
        for (int i = 0; i < getChildCount(parent); i++) {
            if (getChild(parent, i).equals(child)) {
                return i;
            }
        }
        return -1;
    }

    /** Registers a TreeModelListener with this TreeTableModel.
     * @param l TreeModelListener to register.
     */
    public void addTreeModelListener(final TreeModelListener l) {
        listenerList.add(TreeModelListener.class, l);
    }

    /** Unregisters a TreeModelListener with this TreeTableModel.
     * @param l TreeModelListener to unregister.
     */
    public void removeTreeModelListener(final TreeModelListener l) {
        listenerList.remove(TreeModelListener.class, l);
    }

    /** {@inheritDoc} */
    public Class<?> getColumnClass(final int column) {
        return Object.class;
    }

   /** {@inheritDoc} */
    public boolean isCellEditable(final T node, final int column) {
       //noinspection ObjectEquality
       return getColumnClass(column) == TreeTableModel.class;
    }

    /** {@inheritDoc} */
    public void setValueAt(final Object value, final T node, final int column) {
        // The basic implementation does nothing
    }

    /** Informs all registered listeners of a change.
     * @param source Event source (usually this).
     * @param path Path of the change.
     * @param childIndices Indices of the children that changed.
     * @param children Children that changed.
     */
    protected void fireTreeNodesChanged(final Object source, final T[] path, final int[] childIndices, final T[] children) {
        final Object[] listeners = listenerList.getListenerList();
        final TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            //noinspection ObjectEquality
            if (listeners[i] == TreeModelListener.class) {
                ((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
            }
        }
    }

    /** Informs all registered listeners of an insertion.
     * @param source Event source (usually this).
     * @param path Path of the change.
     * @param childIndices Indices of the children that changed.
     * @param children Children that changed.
     */
    protected void fireTreeNodesInserted(final Object source, final Object[] path, final int[] childIndices, final Object[] children) {
        final Object[] listeners = listenerList.getListenerList();
        final TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            //noinspection ObjectEquality
            if (listeners[i] == TreeModelListener.class) {
                ((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
            }
        }
    }

    /** Informs all registered listeners of a removal.
     * @param source Event source (usually this).
     * @param path Path of the change.
     * @param childIndices Indices of the children that changed.
     * @param children Children that changed.
     */
    protected void fireTreeNodesRemoved(final Object source, final Object[] path, final int[] childIndices, final Object[] children) {
        final Object[] listeners = listenerList.getListenerList();
        final TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            //noinspection ObjectEquality
            if (listeners[i] == TreeModelListener.class) {
                ((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
            }
        }
    }

    /** Informs all registered listeners of a structural change.
     * @param source Event source (usually this).
     * @param path Path of the change.
     * @param childIndices Indices of the children that changed.
     * @param children Children that changed.
     */
    protected void fireTreeStructureChanged(final Object source, final Object[] path, final int[] childIndices, final Object[] children) {
        final Object[] listeners = listenerList.getListenerList();
        final TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            //noinspection ObjectEquality
            if (listeners[i] == TreeModelListener.class) {
                ((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
            }
        }
    }

} // class AbstractTreeTableModel
