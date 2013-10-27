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

package net.sf.japi.swing.action;

import javax.swing.ActionMap;

/** An ActionMap subclass which provides a (possibly localized) name.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class NamedActionMap extends ActionMap {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The name.
     * @serial include
     */
    private final String name;

    /** Create a NamedActionMap without providing a name.
     * The default name is the empty String.
     */
    public NamedActionMap() {
        this("");
    }

    /** Create a NamedActionMap.
     * @param name Name
     */
    public NamedActionMap(final String name) {
        this.name = name;
    }

    /** Returns the name.
     * @return the name.
     */
    public String getName() {
        return name;
    }

} // class NamedActionMap
