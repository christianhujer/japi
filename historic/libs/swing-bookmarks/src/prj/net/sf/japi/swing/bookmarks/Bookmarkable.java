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
import net.sf.japi.swing.app.CanLoad;
import net.sf.japi.swing.app.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Interface for classes that want to interact with the BookmarkManager.
 * Implement this interface if your class provides information for creating bookmarks.
 * See the class {@link BookmarkManager} for more information on Bookmarks.
 * @param <D> The document type that can be loaded.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @see BookmarkManager
 */
public interface Bookmarkable<D> extends CanLoad<D> {

    /** Get whether it currently is possible to create a bookmark.
     * @return <code>true</code> if it is possible to create a bookmark, e.g. {@link #getBookmarkTitle()} and {@link #getBookmarkURL()} will return
     * meaningful values, otherwise <code>false</code>
     */
    boolean isBookmarkPossible();

    /** Get the title for the Bookmark.
     * @return title for Bookmark
     * @todo Eventually change to @NotNull
     */
    @Nullable String getBookmarkTitle();

    /** Get the URL for the Bookmark.
     * @return url for Bookmark
     * @todo Eventually change to @NotNull
     */
    @Nullable String getBookmarkURL();

    /** {@inheritDoc}
     * Invoked when the user requests to load a Bookmark.
     * The implementor of this method is itself responsible of handling errors and displaying eventual error messages to the user
     * @param uri URL from bookmark
     */
    @NotNull
    Document<D> load(@NotNull String uri);

    /** Get the component which to block for modal dialogs.
     * It is safe to return <code>null</code>.
     * @return component
     */
    @Nullable Component getBookmarkBlocker();

} // interface Bookmarkable
