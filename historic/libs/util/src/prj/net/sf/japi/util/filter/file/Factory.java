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

package net.sf.japi.util.filter.file;

import java.io.File;
import net.sf.japi.util.filter.Filter;

/** A factory for filters.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo check whether this class is complete
 * @todo check whether this class is required or instead {@link Filter.Factory} should be used
 */
@SuppressWarnings({"UtilityClass", "StaticMethodNamingConvention"})
public final class Factory {

    /** Private Utility Class Constructor. */
    private Factory() {
    }

    /** Creates a FileFilter which returns the opposite of the supplied filter.
     * @param filter Filter to invert
     * @return inverted filter
     */
    public static AbstractFileFilter not(final FileFilter filter) {
        return new NotFileFilter(filter);
    }

    /** Not filter for files. */
    private static class NotFileFilter extends AbstractFileFilter {

        /** Filter to invert. */
        private final FileFilter filter;

        /** Create a NotFileFilter.
         * @param filter Filter to invert
         */
        NotFileFilter(final FileFilter filter) {
            this.filter = filter;
        }

        /** {@inheritDoc} */
        @Override public boolean accept(final File pathname) {
            // XXX:2009-02-15:christianhujer:workaround bug in IntelliJ IDEA
            //noinspection RedundantCast
            return !((java.io.FileFilter) filter).accept(pathname);
        }

        /** {@inheritDoc} */
        @Override public String getDescription() {
            return "not ("
                    + (filter instanceof javax.swing.filechooser.FileFilter
                            ? ((javax.swing.filechooser.FileFilter) filter).getDescription()
                            : filter.toString())
                    + ')'; // TODO:2009-02-15:christianhujer:I18N/L10N
        }

    } // class NotFileFilter

} // class Factory
