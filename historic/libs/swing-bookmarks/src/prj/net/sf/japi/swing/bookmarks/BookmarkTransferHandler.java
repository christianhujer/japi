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
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;

/** Class for DnD in Bookmarks displaying JTrees.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo improve implementation
 */
public class BookmarkTransferHandler extends TransferHandler {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** {@inheritDoc} */
    @Override
    public Transferable createTransferable(final JComponent c) {
        return new BookmarkTransferable((BookmarkManager.Bookmark) ((JTree) c).getSelectionPath().getLastPathComponent());
    }

    /** {@inheritDoc}
     * @todo correctly implement this
     */
    @Override
    public boolean canImport(final JComponent comp, final DataFlavor[] transferFlavors) {
        // TODO:2009-02-15:christianhujer:Proper implementation.
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public int getSourceActions(final JComponent c) {
        return COPY_OR_MOVE;
    }

} // class BookmarkTransferHandler
