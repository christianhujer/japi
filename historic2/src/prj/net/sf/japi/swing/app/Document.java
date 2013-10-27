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

import java.awt.Component;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A document represents an entity of work in an application that can be loaded and saved.
 * In a text editor, a document would represent the text file that is being edited.
 * In a word processor, it would be the document edited in the word processor.
 * In a spread sheet, it would be the spread sheet file.
 * @param <D> The document type that is managed by the application.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public abstract class Document<D> implements Serializable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The frames of this document.
     * @serial include
     */
    private final List<DocumentFrame<D>> frames = new ArrayList<DocumentFrame<D>>();

    /** The Event Listeners.
     * @serial include
     */
    private final EventListenerList listenerList = new EventListenerList();

    /** The data of this Document.
     * @serial include
     */
    @NotNull private final D data;

    /** The URI of this document.
     * @serial include
     */
    @Nullable private String uri;

    /** The title of this document.
     * @serial include
     */
    // TODO:2009-02-15:christianhujer:@Nullable or @NotNull
    private String title;

    /** The changed state of this document.
     * @serial include
     */
    private boolean changed = false;

    /** Creates a Document.
     * @param uri URI.
     * @param title Title.
     * @param data Data for this document.
     */
    protected Document(@Nullable final String uri, final String title, @NotNull final D data) {
        this.uri = uri;
        this.title = title;
        this.data = data;
    }

    /** Adds a {@link DocumentListener} to this Document.
     * @param l the listener to be added.
     */
    public void addDocumentListener(final DocumentListener<D> l) {
        listenerList.add(DocumentListener.class, l);
    }

    /** Removes a {@link DocumentListener} from this Document.
     * @param l the listener to be removed.
     */
    public void removeDocumentListener(final DocumentListener<D> l) {
        listenerList.add(DocumentListener.class, l);
    }

    /** Forward the notification that the document title has changed
     * to all {@link DocumentListener}s that registered themselves
     * as listeners for this Document.
     */
    private void fireDocumentTitleChanged() {
        final Object[] listeners = listenerList.getListenerList();
        DocumentEvent<D> e = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == DocumentListener.class) {
                if (e == null) {
                    e = new DocumentEvent<D>(this, DocumentEventType.DOCUMENT_TITLE_CHANGED);
                }
                //noinspection unchecked
                ((DocumentListener<D>) listeners[i + 1]).documentTitleChanged(e);
            }
        }
    }

    /** Forward the notification that the document uri has changed
     * to all {@link DocumentListener}s that registered themselves
     * as listeners for this Document.
     */
    private void fireDocumentUriChanged() {
        final Object[] listeners = listenerList.getListenerList();
        DocumentEvent<D> e = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == DocumentListener.class) {
                if (e == null) {
                    e = new DocumentEvent<D>(this, DocumentEventType.DOCUMENT_URI_CHANGED);
                }
                //noinspection unchecked
                ((DocumentListener<D>) listeners[i + 1]).documentUriChanged(e);
            }
        }
    }

    /** Forward the notification that the document content has changed
     * to all {@link DocumentListener}s that registered themselves
     * as listeners for this Document.
     */
    private void fireDocumentContentChanged() {
        final Object[] listeners = listenerList.getListenerList();
        DocumentEvent<D> e = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == DocumentListener.class) {
                if (e == null) {
                    e = new DocumentEvent<D>(this, DocumentEventType.DOCUMENT_CONTENT_CHANGED);
                }
                //noinspection unchecked
                ((DocumentListener<D>) listeners[i + 1]).documentContentChanged(e);
            }
        }
    }

    /** Creates a DocumentFrame for this Document.
     * @return DocumentFrame for this Document.
     */
    DocumentFrame<D> createDocumentFrame() {
        final DocumentFrame<D> frame = new DocumentFrame<D>(this);
        frame.add(createDocumentComponent());
        frames.add(frame);
        updateFrameTitles();
        return frame;
    }

    /** Removes a DocumentFrame for this Document.
     * @param docFrame DocumentFrame for this Document to remove.
     */
    @SuppressWarnings({"TypeMayBeWeakened"})
    void removeDocumentFrame(@NotNull final DocumentFrame<D> docFrame) {
        assert frames.contains(docFrame);
        docFrame.setVisible(false);
        docFrame.dispose();
        frames.remove(docFrame);
        updateFrameTitles();
    }

    /** Returns the number of frames this document has.
     * @return The number of frames this document has.
     */
    int getFrameCount() {
        return frames.size();
    }

    /** Returns the data of this document.
     * @return The data of this document.
     */
    @NotNull public D getData() {
        return data;
    }

    /** Returns the first frame of this document.
     * @return the first frame of this document.
     */
    public DocumentFrame<D> getFirstFrame() {
        return frames.get(0);
    }

    /** Creates a Component for the document.
     * @return Component for displaying the specified document.
     */
    protected abstract Component createDocumentComponent();

    /** Returns whether this document has changed.
     * A document is changed if it either was never saved or it was changed after saving.
     * @return <code>true</code> if this document has changed, otherwise <code>false</code>.
     */
    public boolean hasChanged() {
        return changed;
    }

    /** Returns the URI of this document.
     * @return The URI of this document.
     */
    @Nullable String getUri() {
        return uri;
    }

    /** Sets the URI of this document.
     * @param uri URI of thsi document.
     */
    void setUri(@Nullable final String uri) {
        this.uri = uri;
        fireDocumentUriChanged();
    }

    /** Sets the title of this document.
     * @param title New title of this document.
     */
    protected final synchronized void setTitle(@Nullable final String title) {
        this.title = title;
        updateFrameTitles();
        fireDocumentTitleChanged();
    }

    /** Updates the titles of all frames. */
    private synchronized void updateFrameTitles() {
        for (final DocumentFrame<D> frame : frames) {
            frame.updateFrameTitle();
        }
    }

    /** Returns the title of this document.
     * @return The title of this document.
     */
    @Nullable public final synchronized String getTitle() {
        return title;
    }

    /** Returns whether or not this Document has more than one frame.
     * @return <code>true</code> if this Document has more than one frame, otherwise <code>false</code>.
     */
    public boolean hasMoreThanOneFrame() {
        return frames.size() > 1;
    }

    /** Returns the number of a frame.
     * @param frame Frame for which to return the number.
     * @return The number of that frame.
     */
    @SuppressWarnings({"TypeMayBeWeakened"})
    int getFrameNumber(@NotNull final DocumentFrame<D> frame) {
        return frames.indexOf(frame);
    }

    /** Sets the changed state of this document.
     * @param changed New changed state of this document.
     */
    protected void setChanged(final boolean changed) {
        if (!this.changed && changed) {
            this.changed = changed;
            fireDocumentContentChanged();
        }
        this.changed = changed;
    }

}
