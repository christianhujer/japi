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

import java.util.List;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.treetable.AbstractTreeTableModel;
import net.sf.japi.swing.treetable.TreeTableModel;

/**
 * TODO
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class KeyStrokeTreeTableModel extends AbstractTreeTableModel<KeyStrokeRootNode, AbstractSimpleNode<AbstractSimpleNode>> {

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.prefs.keys");

    /** Column names. */
    private static final String[] COLUMN_NAMES = {
        ACTION_BUILDER.getString("keystroke.table.column.action.title"),
        ACTION_BUILDER.getString("keystroke.table.column.keystroke.title"),
    };

    /** Create a KeyStrokeTreeTableModel.
     * @param builders ActionBuilders to create a KeyStrokeTreeTableModel for.
     */
    public KeyStrokeTreeTableModel(final List<ActionBuilder> builders) {
        super(new KeyStrokeRootNode(builders));
    }

    /** {@inheritDoc} */
    public AbstractSimpleNode<AbstractSimpleNode> getChild(final AbstractSimpleNode<AbstractSimpleNode> parent, final int index) {
        //noinspection unchecked
        return (AbstractSimpleNode<AbstractSimpleNode>) parent.getChild(index);
    }

    /** {@inheritDoc} */
    public int getChildCount(final AbstractSimpleNode<AbstractSimpleNode> node) {
        return node.getChildCount();
    }

    /** {@inheritDoc} */
    public int getColumnCount() {
        return 2;
    }

    /** {@inheritDoc} */
    public String getColumnName(final int column) {
        return COLUMN_NAMES[column];
    }

    /** {@inheritDoc} */
    public Object getValueAt(final AbstractSimpleNode<AbstractSimpleNode> node, final int column) {
        return node != null ? node.getValueAt(column) : null;
    }

    /** {@inheritDoc} */
    @Override public Class<?> getColumnClass(final int column) {
        return new Class[] { TreeTableModel.class, String.class}[column];
    }

} // class KeyStrokeTreeTableModel
