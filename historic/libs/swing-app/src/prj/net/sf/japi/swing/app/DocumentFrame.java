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

import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;
import org.jetbrains.annotations.NotNull;

/** Internal Frame for a document.
 * @param <D> The document type that is managed by the application.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
class DocumentFrame<D> extends JInternalFrame {

    /** The document of this DocumentFrame. */
    @NotNull private final Document<D> document;

    /** The Action for activating this frame. */
    private final Action windowAction = new AbstractAction() {

        /** Serial version. */
        private static final long serialVersionUID = 1L;

        public void actionPerformed(final ActionEvent e) {
            try {
                setSelected(true);
            } catch (final PropertyVetoException ignore) {
                /* simply ignore this. */
            }
        }
    };

    /** Creates a DocumentFrame.
     * @param document Document for which to create a JInternalFrame.
     */
    DocumentFrame(@NotNull final Document<D> document) {
        super("TODO", true, true, true, true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.document = document;
    }

    /** Returns the document of this DocumentFrame.
     * @return The document of this DocumentFrame.
     */
    @NotNull Document<D> getDocument() {
        return document;
    }

    /** Updates the title of this frame. */
    void updateFrameTitle() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(document.getTitle());
        if (document.hasMoreThanOneFrame()) {
            stringBuilder.append(":");
            stringBuilder.append(document.getFrameNumber(this) + 1);
        }
        final String frameTitle = stringBuilder.toString();
        setTitle(frameTitle);
        windowAction.putValue(Action.NAME, frameTitle);
    }

    public Action getWindowAction() {
        return windowAction;
    }
}
