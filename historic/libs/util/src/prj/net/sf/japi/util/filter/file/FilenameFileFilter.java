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

/** Swing FileFilter implementation that filters files with specified names.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo find a convenient way for i18n/l10n of this class
 */
public final class FilenameFileFilter extends AbstractFileFilter {

    /** Whether to accept directories. */
    private final boolean acceptDirectories;

    /** Whether to negate the names. */
    private final boolean negate;

    /** The description text. */
    private final String description;

    /** The filenames to accept. */
    private final String[] names;

    /** Create a DFileFilter.
     * <code>negate</code> is set to false on this file filter.
     * @param acceptDirectories pass <code>true</code> if this FileFilter should accept directories as well, <code>false</code> to deny directories
     * @param description The description to use for swing
     * @param names The names to accept
     */
    public FilenameFileFilter(final boolean acceptDirectories, final String description, final String... names) {
        this(acceptDirectories, false, description, names);
    }

    /** Create a DFileFilter.
     * @param acceptDirectories pass <code>true</code> if this FileFilter should accept directories as well, <code>false</code> to deny directories
     * @param negate pass <code>true</code> if the names are to be negated, which means the filter will only accept files <em>not</em> names on one
     * of the <var>names</var>; usually you want to pass <code>false</code>
     * @param description The description to use for swing
     * @param names The names to accept
     */
    public FilenameFileFilter(final boolean acceptDirectories, final boolean negate, final String description, final String... names) {
        this.acceptDirectories = acceptDirectories;
        this.negate            = negate;
        this.description       = description;
        this.names             = names.clone();
    }

    /** {@inheritDoc} */
    @Override public String getDescription() {
        return description;
    }

    /** {@inheritDoc} */
    @Override public boolean accept(final File pathname) {
        final String fileName = pathname.getName();
        boolean ret;
        if (negate) {
            ret = true;
            for (final String name : names) {
                ret &= !fileName.equals(name);
            }
            ret |= acceptDirectories && pathname.isDirectory();
        } else {
            ret = acceptDirectories && pathname.isDirectory();
            for (final String name : names) {
                ret |= fileName.equals(name);
            }
        }
        return ret;
    }

} // class DFileFilter
