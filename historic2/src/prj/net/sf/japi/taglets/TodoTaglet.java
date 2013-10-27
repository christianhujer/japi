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

package net.sf.japi.taglets;

import com.sun.tools.doclets.Taglet;
import java.util.Map;

/**
 * Taglet for entries in a todo list.
 * A todo entry states a missing or unimplemented feature or other work that needs to be done as soon as sombody has time for it.
 * Todo entries shouldn't be used for code that is known to be or at least looks like being erraneous.
 * For these situations, use either {@link XxxTaglet @xxx} or {@link FixmeTaglet @fixme}.
 *
 * @note This tag should only be used if the issue in context is important for the reader of the documentation.
 *       Internal issues should not be put in javadoc.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @see XxxTaglet for code that looks bogus but seems to work
 * @see FixmeTaglet for code that is known to be bogus
 * @see <a href="http://www.riedquat.de/TR/TODO_Syntax">TODO Syntax Specification</a>
 */
public final class TodoTaglet extends BlockListTaglet {

    /**
     * Create an TodoTaglet.
     */
    private TodoTaglet() {
        super("todo", "Todo");
    }

    /**
     * Register this Taglet.
     * @param tagletMap the map to register this tag to.
     */
    public static void register(final Map<String, Taglet> tagletMap) {
        final Taglet taglet = new TodoTaglet();
        tagletMap.put(taglet.getName(), taglet);
    }

} // class TodoTaglet
