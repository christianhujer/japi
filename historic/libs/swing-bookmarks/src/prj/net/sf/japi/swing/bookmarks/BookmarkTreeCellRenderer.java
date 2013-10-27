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

package net.sf.japi.swing.bookmarks;

import java.awt.Component;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/** Class for rendering TreeCells in JTrees which manage Bookmarks.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo improve separator
 */
public class BookmarkTreeCellRenderer extends DefaultTreeCellRenderer {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** JSeparator.
     * @serial include
     */
    private final JSeparator sep = new JSeparator();

    /** {@inheritDoc} */
    @Override public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
        if (!(value instanceof BookmarkManager.Bookmark)) {
            throw new IllegalArgumentException("Supplied value must be a bookmark but was " + value.getClass().getName());
            //return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        }
        if (value instanceof BookmarkManager.BookmarkSeparator) {
            return super.getTreeCellRendererComponent(tree, "----------------", sel, expanded, leaf, row, hasFocus);
            //sep.setMinimumSize(new Dimension(tree.getWidth() / 2, 2));
            //sep.setPreferredSize(new Dimension(tree.getWidth(), 2));
            //sep.setSize(5, tree.getWidth());
            ////sep.setBorder(new );
            //return sep;
        } else {
            return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        }
    }

} // class BookmarkTreeCellRenderer
