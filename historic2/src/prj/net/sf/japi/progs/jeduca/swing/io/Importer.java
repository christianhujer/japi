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

package net.sf.japi.progs.jeduca.swing.io;

import java.io.IOException;
import org.xml.sax.SAXException;

/** Interface for classes that are able to import documents (called "Import Module").
 * @param <T> Type which is imported.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo think about a generic caching implementation
 */
public interface Importer<T> extends IOBase<T> {

    /** Check whether a file can be loaded.
     * It is recommended to invoke this method mainly for file:/ and other local URL schemes to keep network traffic and user response time low.
     * Also it is recommended that implementations use caching mechanisms.
     * @param uri URL to load
     * @return <code>true</code> if this Import instance is able to load the supplied url (file type is okay), otherwise <code>false</code>
     */
    boolean canLoad(String uri);

    /** Load a Document from a URL.
     * @param url URL to load
     * @return Document
     * @throws IOException in case of I/O-Problems
     * @throws SAXException in case of XML-problems
     */
    T load(String url) throws IOException, SAXException;

} // interface Importer

