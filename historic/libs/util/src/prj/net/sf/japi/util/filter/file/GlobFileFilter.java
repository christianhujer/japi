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

/** A GlobFileFilter is a FileFilter which works like shell pattern matching.
 * Currently supported are:
 * <ul>
 *  <li><code>*</code> for matching an arbitrary, possibly empty part of a filename</li>
 *  <li><code>?</code> for matching a single arbitrary character</li>
 * </ul>
 * @note Currently only matching file names, not directories is possible. Globs with '/' in it are likely to always fail on most operating systems.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @author Andreas Kirschbaum
 * @since 0.1
 */
public class GlobFileFilter extends RegexFileFilter {

    /** Create a GlobFileFilter.
     * @param globs Globs to match against.
     */
    public GlobFileFilter(final String... globs) {
        super(createPatternsForGlobs(globs));
    }

    /**
     * Converts shell glob expressions into regular expressions patterns.
     * @param globs shell glob expressions
     * @return regular expressions matching the same filename as the shell globs
     */
    public static String[] createPatternsForGlobs(final String... globs) {
        final String[] patterns = new String[globs.length];
        for (int i = 0; i < patterns.length; i++) {
            patterns[i] = createPatternForGlob(globs[i]);
        }
        return patterns;
    }

    /**
     * Converts a shell glob expression into a regular expressions pattern.
     * @param glob shell glob expression
     * @return regular expression matching the same filename as the shell glob
     */
    public static String createPatternForGlob(final String glob) {
        final StringBuilder sb = new StringBuilder();
        boolean quoting = false;
        final char[] chars = glob.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            final char ch = chars[i];
            if (ch == '*') {
                if (quoting) {
                    quoting = false;
                    sb.append("\\E");
                }
                sb.append(".*");
            } else if (ch == '?') {
                if (quoting) {
                    quoting = false;
                    sb.append("\\E");
                }
                sb.append(".");
            } else if (ch == '\\' && i + 1 < chars.length && chars[i + 1] == 'E') {
                if (quoting) {
                    quoting = false;
                    sb.append("\\E");
                }
                sb.append("\\\\");
            } else {
                if (!quoting) {
                    quoting = true;
                    sb.append("\\Q");
                }
                sb.append(ch);
            }
        }
        return sb.toString();
    }

} // class GlobFileFilter
