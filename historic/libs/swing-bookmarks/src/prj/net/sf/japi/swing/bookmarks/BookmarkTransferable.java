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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

/** Class for transfering a bookmark through a clipboard or drag and drop.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class BookmarkTransferable implements Transferable {

    /** Data Flavor for Bookmarks. */
    private static final DataFlavor BOOKMARK_DATA_FLAVOR = initBookmarkFlavor();

    /** Initialize DataFlavor for Bookmarks.
     * @return DataFlavor for Bookmarks
     */
    private static DataFlavor initBookmarkFlavor() {
        try {
            return new DataFlavor("application/x-jtest-bookmark");
        } catch (final ClassNotFoundException e) {
            throw new Error(e);
        }
    }

    /** Supported DataFlavors. */
    private static final DataFlavor[] FLAVORS = {BOOKMARK_DATA_FLAVOR};

    /** Bookmark to be transferred. */
    private final BookmarkManager.Bookmark bookmark;

    /** Create a BookmarkTransferable.
     * @param bookmark Bookmark to transfer
     */
    public BookmarkTransferable(final BookmarkManager.Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    /** {@inheritDoc} */
    public Object getTransferData(final DataFlavor flavor) {
        return bookmark;
    }

    /** {@inheritDoc} */
    public DataFlavor[] getTransferDataFlavors() {
        return FLAVORS.clone();
    }

    /** {@inheritDoc} */
    public boolean isDataFlavorSupported(final DataFlavor flavor) {
        return flavor.equals(BOOKMARK_DATA_FLAVOR);
    }

    /** Get the DataFlavor for Bookmarks.
     * @return DataFlavor for Bookmarks
     */
    public static DataFlavor getBookmarkDataFlavor() {
        return BOOKMARK_DATA_FLAVOR;
    }

} // class BookmarkTransferable
