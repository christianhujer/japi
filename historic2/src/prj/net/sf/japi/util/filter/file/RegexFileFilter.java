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
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;

/** A FileFilter implementation that matches against a regular expression.
 * @see Pattern
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo allow setting the description
 */
public class RegexFileFilter extends AbstractFileFilter {

    /** The pattern. */
    private final Pattern[] patterns;

    /** Create a RegexFileFilter.
     * @param patterns String of Pattern to match against
     * @note The patterns is verified using {@link Matcher#matches()}, which means the whole filename (but excluding path) is matched.
     */
    public RegexFileFilter(final String... patterns) {
        this(compilePatterns(patterns));
    }

    private static Pattern[] compilePatterns(final String[] regexes) {
        final Pattern[] patterns = new Pattern[regexes.length];
        for (int i = 0; i < regexes.length; i++) {
            patterns[i] = compile(regexes[i]);
        }
        return patterns;
    }

    /** Create a RegexFileFilter.
     * @param patterns Patterns to match against
     * @note The pattern is verified using {@link Matcher#matches()}, which means the whole filename (but excluding path) is matched.
     */
    public RegexFileFilter(final Pattern... patterns) {
        this.patterns = patterns.clone();
    }

    /** {@inheritDoc} */
    @Override public boolean accept(final File pathname) {
        for (final Pattern pattern : patterns) {
            if (pattern.matcher(pathname.getName()).matches()) {
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override public String getDescription() {
        return Arrays.toString(patterns);
    }

} // class RegexFileFilter
