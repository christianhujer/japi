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

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import static net.sf.japi.swing.bookmarks.BookmarkTransferable.getBookmarkDataFlavor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Class for dropping a bookmark over a JTree managing bookmarks.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class BookmarkDropTargetAdapter extends DropTargetAdapter {

    /** {@inheritDoc} */
    public void drop(@NotNull final DropTargetDropEvent dtde) {
        System.err.println("drop");
        final JTree tree = (JTree) dtde.getDropTargetContext().getComponent();
        BookmarkManager.Bookmark dest = getDestBookmark(dtde, tree);
        final BookmarkManager.Bookmark src  = getSourceBookmark(dtde);
        int pos = -1;
        if (!(dest instanceof BookmarkManager.BookmarkFolder)) {
            final BookmarkManager.Bookmark newDest = dest.getFolder();
            assert newDest != null;
            pos = newDest.getIndex(dest);
            dest = newDest;
        }
        assert dest instanceof BookmarkManager.BookmarkFolder;
        ((BookmarkManager.BookmarkFolder) dest).insert(src, pos);
        tree.treeDidChange();
    }

    /** Find the destination bookmark for a drop event.
     * @param dtde drop event
     * @param tree JTree to find bookmark in
     * @return destination bookmark
     */
    @NotNull private static BookmarkManager.Bookmark getDestBookmark(@NotNull final DropTargetDropEvent dtde, @NotNull final JTree tree) {
        final Point p = dtde.getLocation();
        final TreePath tp = tree.getClosestPathForLocation(p.x, p.y);
        return (BookmarkManager.Bookmark) tp.getLastPathComponent();
    }

    /** Find source bookmark for a drop event.
     * @param dtde drop event
     * @return source bookmark
     */
    @Nullable private static BookmarkManager.Bookmark getSourceBookmark(@NotNull final DropTargetDropEvent dtde) {
        final Transferable source = dtde.getTransferable();
        try {
            return (BookmarkManager.Bookmark) source.getTransferData(getBookmarkDataFlavor());
        } catch (final UnsupportedFlavorException ignore) {
            return null;
        } catch (final IOException ignore) {
            return null;
        }
    }

} // class BookmarkDropTargetAdapter
