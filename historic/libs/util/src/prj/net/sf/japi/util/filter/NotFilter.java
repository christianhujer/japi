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

package net.sf.japi.util.filter;

/** A Filter that is the negation of another filter.
 * @param <T> Type of filter.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
final class NotFilter<T> implements Filter<T> {

    /** The filter to negate. */
    private final Filter<T> filter;

    /** Create a NotFilter.
     * @param filter Filter to negate
     */
    NotFilter(final Filter<T> filter) {
        this.filter = filter;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return "NotFilter[" + super.toString() + ']';
    }

    /** {@inheritDoc} */
    public boolean accept(final T o) {
        return !filter.accept(o);
    }

} // class NotFilter
