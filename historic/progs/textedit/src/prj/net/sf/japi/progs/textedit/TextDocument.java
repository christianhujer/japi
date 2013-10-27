/*
 * Copyright (C) 2009  Christian Hujer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.japi.progs.textedit;

import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import net.sf.japi.swing.app.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A TextDocument ist a {@link Document} for {link PlainDocument} from Swing.
 * It listens to the {@link DocumentEvent}s from Swing and changes the Document state accordingly.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class TextDocument extends Document<PlainDocument> implements DocumentListener {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a Document.
     * @param uri URI for this document.
     * @param title Title for this document.
     * @param data Data for this document.
     */
    public TextDocument(@Nullable final String uri, final String title, @NotNull final PlainDocument data) {
        super(uri, title, data);
        //noinspection ThisEscapedInObjectConstruction
        data.addDocumentListener(this);
    }

    /** {@inheritDoc} */
    @Override
    protected Component createDocumentComponent() {
        return new JScrollPane(new JTextArea(getData()));
    }

    /** {@inheritDoc} */
    public void insertUpdate(final DocumentEvent e) {
        if (e.getDocument() == getData()) {
            setChanged(true);
        }
    }

    /** {@inheritDoc} */
    public void removeUpdate(final DocumentEvent e) {
        if (e.getDocument() == getData()) {
            setChanged(true);
        }
    }

    /** {@inheritDoc} */
    public void changedUpdate(final DocumentEvent e) {
        if (e.getDocument() == getData()) {
            setChanged(true);
        }
    }
}
