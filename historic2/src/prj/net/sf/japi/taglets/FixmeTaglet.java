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
 * Taglet for entries in a fixme list.
 * A fixme entry denotes code that is known to be bogus and needs fixing.
 *
 * @note This tag should only be used if the issue in context is important for the reader of the documentation.
 *       Internal issues should not be put in javadoc.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @see TodoTaglet for general work that needs to be done
 * @see XxxTaglet for code that looks bogus but seems to work
 * @see <a href="http://www.riedquat.de/TR/TODO_Syntax">TODO Syntax Specification</a>
 */
public final class FixmeTaglet extends BlockListTaglet {

    /**
     * Create an FixmeTaglet.
     */
    private FixmeTaglet() {
        super("fixme", "Fixme");
    }

    /**
     * Register this Taglet.
     * @param tagletMap the map to register this tag to.
     */
    public static void register(final Map<String, Taglet> tagletMap) {
        final Taglet taglet = new FixmeTaglet();
        tagletMap.put(taglet.getName(), taglet);
    }

} // class FixmeTaglet
