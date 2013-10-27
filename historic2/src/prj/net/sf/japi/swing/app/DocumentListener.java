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

package net.sf.japi.swing.app;

import java.util.EventListener;
import org.jetbrains.annotations.NotNull;

/** The listener interface for receiving {@link DocumentEvent}s.
 *
 * Document events are provided for notification purposes ONLY.
 * JAPI will automatically handle documentation uri and title changes
 * so that these aspects work properly regardless of whether or not
 * a program registers a {@link DocumentListener}.
 * @param <D> The document type that is managed by the application.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface DocumentListener<D> extends EventListener {

    /** Notifies this DocumentListener that a document's uri has changed.
     * @param e DocumentEvent describing the uri change.
     */
    void documentUriChanged(@NotNull DocumentEvent<D> e);

    /** Notifies this DocumentListener that a document's title has changed.
     * @param e DocumentEvent describing the title change.
     */
    void documentTitleChanged(@NotNull DocumentEvent<D> e);

    /** Notifies this DocumentListener that a document's content has changed.
     * Only the first change is reported.
     * Saving a document resets the state to unchanged.
     * @param e DocumentEvent describing the content change.
     */
    void documentContentChanged(@NotNull DocumentEvent<D> e);
}
