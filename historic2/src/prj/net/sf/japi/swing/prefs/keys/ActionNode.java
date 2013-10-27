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

import javax.swing.Action;
import org.jetbrains.annotations.Nullable;

/** Node object for an Action.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
class ActionNode extends AbstractSimpleNode<SimpleNode> {

    /** The action of this node. */
    private final Action action;

    /** Create an ActionNode.
     * @param action Action for this node.
     */
    ActionNode(final Action action) {
        super(null);
        this.action = action;
    }

    /** Returns the action of this node.
     * @return the action of this node
     */
    public Action getAction() {
        return action;
    }

    /** {@inheritDoc} */
    @Override public boolean equals(final Object obj) {
        return obj instanceof ActionNode && ((ActionNode) obj).action == action;
    }

    /** {@inheritDoc} */
    @Override public int hashCode() {
        return action.hashCode();
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return String.valueOf(action.getValue(Action.NAME));
    }

    /** {@inheritDoc} */
    @Nullable public Object getValueAt(final int column) {
        switch (column) {
            case 1: return ActionKeyAction.getLocalizedKeyStrokeText(action);
            default: return null;
        }
    }

} // class ActionNode
