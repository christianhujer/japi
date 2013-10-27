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

package net.sf.japi.progs.jeduca.jtest.io;

import org.xml.sax.ext.DefaultHandler2;

/** Class for XML Utilities like EntityResolvers, ErrorHandlers and stuff like that.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class XMLUtils {

    /** Singleton instance. */
    private static final XMLUtils INSTANCE = new XMLUtils();

    /** Singleton constructor. */
    private XMLUtils() { }

    /** Get an instance of XMLUtils.
     * @return instance of XMLUtils
     */
    public static XMLUtils getInstance() {
        return INSTANCE;
    }

    /** Quiet error handler. */
    private final DefaultHandler2 quietHandler = new DefaultHandler2();

    /** Get the quiet error handler.
     * @return The quiet error handler.
     */
    public DefaultHandler2 getQuietHandler() {
        return quietHandler;
    }

} // class XMLUtils
