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

import java.util.EventObject;
import org.jetbrains.annotations.NotNull;

/**
 * Event for information while parsing.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ParseEvent extends EventObject {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a ParseEvent.
     * @param source The Parser on which the Event initially occurred.
     * @throws IllegalArgumentException if source is <code>null</code>.
     */
    public ParseEvent(@NotNull final Parser source) {
        super(source);
    }

    /** {@inheritDoc} */
    @Override
    public Parser getSource() {
        return (Parser) super.getSource();
    }

}// class ParseEvent
