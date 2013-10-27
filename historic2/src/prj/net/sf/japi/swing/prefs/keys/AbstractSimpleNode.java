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

import org.jetbrains.annotations.Nullable;

/** Base class for simple nodes.
 * @param <C> type for children of this node.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public abstract class AbstractSimpleNode<C> implements SimpleNode<C> {

    /** The Children. */
    @Nullable private final C[] children;

    /** The number of children. */
    private final int length;

    /** Create an AbstractSimpleNode.
     * @param children Children for this AbstractSimpleNode.
     */
    protected AbstractSimpleNode(@Nullable final C[] children) {
        if (children != null) {
            this.children = children.clone();
            assert this.children != null;
            length = this.children.length;
        } else {
            this.children = null;
            length = 0;
        }
    }

    /** {@inheritDoc} */
    @Nullable public final C getChild(final int index) {
        if (children == null) {
            return null;
        }
        try {
            return children[index];
        } catch (final ArrayIndexOutOfBoundsException ignore) {
            return null;
        }
    }

    /** {@inheritDoc} */
    public final int getChildCount() {
        return length;
    }

} // class AbstractSimpleNode
