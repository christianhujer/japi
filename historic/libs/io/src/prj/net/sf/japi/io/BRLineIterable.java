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

package net.sf.japi.io;

import java.io.Reader;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

/** Iterable wrapper for {@link Reader} to return lines.
 *
 * @note Usually this iterable can be iterated only once.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class BRLineIterable implements Iterable<String> {

    /** The Reader over which to iterate. */
    @NotNull private final Reader in;

    /** Create a BRLineIterable.
     * @param in Reader over which to iterate.
     */
    public BRLineIterable(@NotNull final Reader in) {
        this.in = in;
    }

    /** {@inheritDoc} */
    public Iterator<String> iterator() {
        return new BRLineIterator(in);
    }
}
