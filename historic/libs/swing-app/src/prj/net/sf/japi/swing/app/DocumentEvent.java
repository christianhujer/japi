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

import java.util.EventObject;
import org.jetbrains.annotations.NotNull;

/** A DocumentEvent describes notable changes to a {@link Document}.
 * @param <D> The document type that is managed by the application.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class DocumentEvent<D> extends EventObject {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The type of this event.
     * @serial include
     */
    @NotNull private final DocumentEventType type;

    /** The source of this event.
     * @serial include
     */
    private final Document<D> source;

    /** Constructs a DocumentEvent.
     * @param source The Document on which the Event initially occurred.
     * @param type The type of this event.
     * @throws IllegalArgumentException if source is null.
     */
    public DocumentEvent(@NotNull final Document<D> source, @NotNull final DocumentEventType type) {
        super(source);
        this.source = source;
        this.type = type;
    }

    /** {@inheritDoc}
     * The source is the document on which this event occurred.
     */
    @Override public Document<D> getSource() {
        return source;
    }

    /** Returns the type of this event.
     * @return The type of this event.
     */
    @NotNull public DocumentEventType getType() {
        return type;
    }
}
