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

import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.treetable.AbstractTreeTableModel;
import net.sf.japi.swing.treetable.TreeTableModel;
import org.jetbrains.annotations.NotNull;

/** TreeTableModel for displaying preferences.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class PrefsTreeTableModel extends AbstractTreeTableModel<PrefsRootNode, PrefsTreeNode> {

    /** Action Builder. */
    @NotNull private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.tools.prefsbrowser");

    /** Create an AbstractTreeTableModel.
     */
    protected PrefsTreeTableModel() {
        super(new PrefsRootNode());
    }

    /** {@inheritDoc} */
    @NotNull public PrefsTreeNode getChild(@NotNull final PrefsTreeNode parent, final int index) {
        return parent.getChild(index);
    }

    /** {@inheritDoc} */
    public int getChildCount(@NotNull final PrefsTreeNode node) {
        return node.getChildCount();
    }

    /** {@inheritDoc} */
    @NotNull @Override public Class<?> getColumnClass(final int column) {
        return column == 0 ? TreeTableModel.class : String.class;
    }

    /** {@inheritDoc} */
    public int getColumnCount() {
        return 2;
    }

    /** {@inheritDoc} */
    public String getColumnName(final int column) {
        switch (column) {
            case 0: return ACTION_BUILDER.getString("key.title");
            case 1: return ACTION_BUILDER.getString("value.title");
            default: assert false; throw new IndexOutOfBoundsException("column must be >= 0 and <= " + getColumnCount() + " but was: " + column);
        }
    }

    /** {@inheritDoc} */
    @NotNull public Object getValueAt(@NotNull final PrefsTreeNode node, final int column) {
        return node.getValueAt(column);
    }

    /** {@inheritDoc} */
    @Override public boolean isLeaf(@NotNull final PrefsTreeNode node) {
        return node instanceof PrefsLeafNode;
    }

} // class PrefsTreeTableModel
