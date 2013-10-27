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

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.io.IOException;

/**
 * Utility class for I/O operations.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class IOUtilities {

    /** Utility class - do not instantiate. */
    private IOUtilities() {
    }

    /** Get the last modified value for a URL.
     * This method is a utility method provided for subclasses.
     * @param uri URL to get last modified value from
     * @return last modified value for <var>uri</var> or 0 if not found
     */
    public static long lastMod(final String uri) {
        try {
            final URL url = new URL(uri);
            final URLConnection con = url.openConnection();
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).setRequestMethod("HEAD");
            }
            return con.getLastModified();
        } catch (final IOException ignore) {
            return 0L;
        }
    }
}
