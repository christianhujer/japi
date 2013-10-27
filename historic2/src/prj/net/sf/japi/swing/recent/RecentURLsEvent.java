/* JAPI - (Yet another (hopefully) useful) Java API
 *
 * Copyright (C) 2004-2006 Christian Hujer
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.japi.swing.recent;

import java.util.EventObject;

/** A semantic event which indicates that the list of recent URLs in a RecentURLs object changed.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @see RecentURLsListener
 * @see RecentURLs
 */
public class RecentURLsEvent extends EventObject {

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** Creates a new RecentURLsEvent object.
     * @param source the object responsible for the event
     */
    public RecentURLsEvent(final Object source) {
        super(source);
    }

} // class RecentURLsEvent
