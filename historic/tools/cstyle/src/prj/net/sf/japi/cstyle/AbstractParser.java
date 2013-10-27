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

package net.sf.japi.cstyle;

import javax.swing.event.EventListenerList;
import org.jetbrains.annotations.NotNull;

/**
 * Useful baseclass for Parsers.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public abstract class AbstractParser implements Parser {

    /** The listeners. */
    private final EventListenerList listeners = new EventListenerList();

    /** Create an AbstractParser. */
    protected AbstractParser() {
    }

    /** {@inheritDoc} */
    public void addParseListener(@NotNull final ParseListener listener) {
        listeners.add(ParseListener.class, listener);
    }

    /** {@inheritDoc} */
    public void removeParseListener(@NotNull final ParseListener listener) {
        listeners.remove(ParseListener.class, listener);
    }

    /** {@inheritDoc} */
    public void init() {
    }

    /** {@inheritDoc} */
    public void reset() {
    }

    /** {@inheritDoc} */
    public void end() {
    }

    /** Fires a ParseEvent.
     * @param event ParseEvent to fire
     */
    protected void fireParseEvent(@NotNull final ParseEvent event) {
        for (final ParseListener listener : listeners.getListeners(ParseListener.class)) {
            listener.parseEvent(event);
        }
    }

} // class AbstractParser
