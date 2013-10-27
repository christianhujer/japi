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

import java.util.Collection;

/** This interface servers as a common interface for filters.
 * @param <T> Type of filter.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface Filter<T> {

    /** Tests whether or not the specified object should be included.
     * @param o object to be tested
     * @return <code>true</code> if and only if <code>o</code> should be included
     */
    boolean accept(T o);

    /** Factory for useful filters.
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     */
    @SuppressWarnings({"StaticMethodNamingConvention", "UtilityClass", "InnerClassOfInterface"})
    final class Factory {

        /** Utility class, must not be instantiated. */
        private Factory() {
        }

        /** Create a Filter that is the opposite of another filter.
         * @param filter Filter to negate
         * @return negated version of <var>filter</var>
         */
        public static <T> Filter<T> not(final Filter<T> filter) {
            return new NotFilter<T>(filter);
        }

        /** Create a Filter that is the AND combination of several other filters.
         * @param filters Filters to AND
         * @return AND version of <var>filters</var>
         */
        public static <T> Filter<T> and(final Iterable<Filter<T>> filters) {
            return new AndFilterForIterable<T>(filters);
        }

        /** Create a Filter that is the AND combination of several other filters.
         * @param filters Filters to AND
         * @return AND version of <var>filters</var>
         */
        public static <T> Filter<T> and(final Filter<T>... filters) {
            return new AndFilterForArray<T>(filters);
        }

        /** Create a Filter that is the OR combination of several other filters.
         * @param filters Filters to OR
         * @return OR version of <var>filters</var>
         */
        public static <T> Filter<T> or(final Iterable<Filter<T>> filters) {
            return new OrFilterForIterable<T>(filters);
        }

        /** Create a Filter that is the OR combination of several other filters.
         * @param filters Filters to OR
         * @return OR version of <var>filters</var>
         */
        public static <T> Filter<T> or(final Filter<T>... filters) {
            return new OrFilterForArray<T>(filters);
        }

        /** Create a Filter that returns whether an element is contained in a collection.
         * Note: The collection is not cloned.
         * Changes to the underlying collection will affect the filter behaviour.
         * If you need a stable filter, you have to clone the collection before invoking this method
         * @param collection Collection to use as a base
         * @return Filter that returns whether an element is contained in a collection
         */
        public static <T> Filter<T> contained(final Collection<T> collection) {
            return new CollectionFilter<T>(collection);
        }

    } // class FilterFactory

} // interface Filter
