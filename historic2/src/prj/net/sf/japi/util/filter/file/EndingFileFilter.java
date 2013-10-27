/*
 * Copyright (C) 2009  Christian Hujer.
 * Copyright (C) 2009  Andreas Kirschbaum.
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

/** Swing FileFilter implementation that filters files with specified endings.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @author Andreas Kirschbaum
 * @since 0.1
 * @todo find a convenient way for i18n/l10n of this class
 * @todo option to ignore case
 */
public class EndingFileFilter extends AbstractFileFilter {

    /** Whether the file system is case-insensitive. */
    private static final boolean CASE_INSENSITIVE_FILE_SYSTEM = new File("a").equals(new File("A"));

    /** Whether to accept directories. */
    private final boolean acceptDirectories;

    /** Whether to negate the endings. */
    private final boolean negate;

    /** The description text. */
    private final String description;

    /** The fileendings to accept. */
    private final String[] endings;

    /** Create an EndingFileFilter.
     * <code>negate</code> is set to false on this file filter.
     * @param acceptDirectories pass <code>true</code> if this FileFilter should accept directories as well, <code>false</code> to deny directories
     * @param description The description to use for swing
     * @param endings The endings to accept, including their period
     * Example: <code>new EndingFileFilter(false, "jpeg image files", ".jpg", ".jpeg", ".jfif")</code>
     */
    public EndingFileFilter(final boolean acceptDirectories, final String description, final String... endings) {
        this(acceptDirectories, false, description, endings);
    }

    /** Create an EndingFileFilter.
     * @param acceptDirectories pass <code>true</code> if this FileFilter should accept directories as well, <code>false</code> to deny directories
     * @param negate pass <code>true</code> if the endings are to be negated, which means the filter will only accept files <em>not</em> ending on one
     * of the <var>endings</var>; usually you want to pass <code>false</code>
     * @param description The description to use for swing
     * @param endings The endings to accept, including their period
     * Example: <code>new EndingFileFilter(false, true, "all files but jpeg image files", ".jpg", ".jpeg", ".jfif")</code>
     */
    public EndingFileFilter(final boolean acceptDirectories, final boolean negate, final String description, final String... endings) {
        this.acceptDirectories = acceptDirectories;
        this.negate            = negate;
        this.description       = description;
        this.endings           = endings.clone();
        if (CASE_INSENSITIVE_FILE_SYSTEM) {
            for (int i = 0; i < this.endings.length; i++) {
                this.endings[i] = this.endings[i].toLowerCase();
            }
        }
    }

    /** {@inheritDoc} */
    @Override public final String getDescription() {
        return description;
    }

    /** {@inheritDoc} */
    @Override public boolean accept(final File pathname) {
        if (pathname.isDirectory()) {
            return acceptDirectories;
        }
        final String fileName = CASE_INSENSITIVE_FILE_SYSTEM ? pathname.getName().toLowerCase() : pathname.getName();
        for (final String ending : endings) {
            if (fileName.endsWith(ending)) {
                return !negate;
            }
        }
        return negate;
    }

} // class DFileFilter
