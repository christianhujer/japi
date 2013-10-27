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

package net.sf.japi.swing.app.script;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/** The Application from a script's view.
 * @param <D> The document type that is managed by the application.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public abstract class Application<D> {

    /** Loads a document.
     * @param uri URI from which to load the document.
     * @return The loaded document.
     */
    public abstract Document<D> load(@NotNull String uri);

    /** Quits this application. */
    public abstract void quit();

    /** Returns the documents open in this application.
     * @return The documents open in this application.
     */
    public abstract List<Document<D>> getDocuments();

    /** A document within this application.
     * @param <D> The document type that is managed by the application.
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     */
    public abstract class Document<D> {

        /** Saves this document. */
        public abstract void save();

        /** Saves this document at a different URI.
         * The supplied URI is then the default URI for future saves with {@link #save()}.
         * @param uri URI at which to save this document.
         */
        public abstract void saveAs(String uri);

        /** Closes this document. */
        public abstract void close();

        /** Returns the views on this document.
         * @return The views on this document.
         */
        public abstract List<View<D>> getViews();

        /** A view on a document.
         * @param <D> The document type that is managed by the application.
         * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
         */
        public abstract class View<D> {
            /** Closes this view. */
            public abstract void close();
        }
    }
}
