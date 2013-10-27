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

/**
 * TODO
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class KeyStrokeRootNode extends AbstractSimpleNode<ActionMapNode> {

    /** Create a KeyStrokeRootNode.
     * @param builders ActionBuilders
     */
    public KeyStrokeRootNode(final List<ActionBuilder> builders) {
        super(createChildren(builders));
    }

    /** Create the children from ActionBuilders.
     * @param builders ActionBuilders
     * @return children
     */
    @SuppressWarnings({"ObjectAllocationInLoop"})
    private static ActionMapNode[] createChildren(final List<ActionBuilder> builders) {
        final ActionMapNode[] children = new ActionMapNode[builders.size()];
        for (int i = 0; i < children.length; i++) {
            children[i] = new ActionMapNode(builders.get(i).getActionMap());
        }
        return children;
    }

    /** {@inheritDoc} */
    public Object getValueAt(final int column) {
        return null;  // TODO:2009-02-23:christianhujer:Implementation.
    }

} // class KeyStrokeRootNode
