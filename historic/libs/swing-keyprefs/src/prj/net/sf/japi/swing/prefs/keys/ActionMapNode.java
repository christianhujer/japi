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

import java.util.Arrays;
import javax.swing.ActionMap;
import net.sf.japi.swing.action.NamedActionMap;

/** Node object for an ActionMap.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
class ActionMapNode extends AbstractSimpleNode<ActionNode> {

    /** The ActionMap. */
    private final ActionMap actionMap;

    /** Create an ActionMapNode.
     * @param actionMap ActionMap
     */
    ActionMapNode(final ActionMap actionMap) {
        super(createChildren(actionMap));
        this.actionMap = actionMap;
    }

    /** Create the children from ActionBuilders.
     * @param actionMap ActionMap
     * @return children
     */
    @SuppressWarnings({"ObjectAllocationInLoop"})
    private static ActionNode[] createChildren(final ActionMap actionMap) {
        final ActionNode[] children = new ActionNode[actionMap.size()];
        final Object[] allKeys = actionMap.allKeys();
        Arrays.sort(allKeys);
        for (int i = 0; i < children.length; i++) {
            children[i] = new ActionNode(actionMap.get(allKeys[i]));
        }
        return children;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        //noinspection ObjectToString
        return actionMap instanceof NamedActionMap
            ? ((NamedActionMap) actionMap).getName()
            : actionMap.toString();
    }

    /** {@inheritDoc} */
    public Object getValueAt(final int column) {
        return null;  // TODO:2009-02-23:christianhujer:Implement.
    }

} // class ActionMapNode
