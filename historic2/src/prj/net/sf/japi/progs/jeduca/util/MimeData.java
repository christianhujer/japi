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

package net.sf.japi.progs.jeduca.util;

/** Interface for classes that represent MIME Information like MIME Data.
 * This interface makes no assumption about the mutability of its implementations.
 * Implementations may or may not be mutable.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface MimeData {

    /** Get the MIME Type of this MimeData object.
     * @return MIME Type
     */
    String getType();

    /** Get the binary data of this MimeData object.
     * Optional Operation.
     * @return binary data
     * @throws UnsupportedOperationException <code>getData</code> is not supported by this MimeData
     */
    byte[] getData() throws UnsupportedOperationException;

    /** Set the MIME Type of this MimeData object.
     * @param type MIME Type
     * @throws UnsupportedOperationException <code>setType</code> is not supported by this MimeData
     */
    void setType(String type) throws UnsupportedOperationException;

    /** Set the binary data of this MimeData object.
     * Optional Operation.
     * @param data binary data
     * @throws UnsupportedOperationException <code>setData</code> is not supported by this MimeData
     */
    void setData(byte[] data) throws UnsupportedOperationException;

} // class MimeData
