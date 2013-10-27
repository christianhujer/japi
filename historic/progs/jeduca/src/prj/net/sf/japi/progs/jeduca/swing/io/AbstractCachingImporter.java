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

import java.util.Map;
import java.util.WeakHashMap;

/** Base implementation of {@link Importer} supplying an intelligent cache about the information whether or not a certain file can be loaded.
 * If you want to write your own implementation of {@link Importer}, this is a potential base class.
 * Subclasses should implement {@link #canLoadImpl(String)} rather than implement {@link #canLoad(String)} directly because the interface method
 * decorates the implementation with that intelligent caching mechanism.
 * Subclasses cannot rely on {@link #canLoadImpl(String)} being invoked always, since if the stored information still is uptodate, calling the method
 * would be superfluous.
 * @param <T> Type which is imported.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo enhance map access by using string pools
 * @todo tell clients to use string pools
 */
public abstract class AbstractCachingImporter<T> implements Importer<T> {

    /** Update information for a file.
     * @author $Author: chris $
     * @version $Id: AbstractCachingImporter.java,v 1.1 2004/12/28 23:51:22 chris Exp $
     */
    private static final class Uptodate {

        /** Do not instantiate outside scope. */
        private Uptodate() {
        }

        /** Whether the file can be loaded. */
        private boolean canLoad;

        /** Timestamp of file. */
        private long lastModified;

    } // class Uptodate

    /** Map with uptodate information. */
    private final Map<String, Uptodate> acceptable = new WeakHashMap<String, Uptodate>();

    /** {@inheritDoc} */
    public final boolean canLoad(final String uri) {
        Uptodate v = acceptable.get(uri);
        if (v == null) { acceptable.put(uri, v = new Uptodate()); }
        final long lastModified = IOUtilities.lastMod(uri);
        if (lastModified != v.lastModified) {
            v.lastModified = lastModified;
            v.canLoad = canLoadImpl(uri);
        }
        return v.canLoad;
    }

    /** Check whether a file can be loaded.
     * Subclasses must implement this method instead of {@link #canLoad(String)}
     * @param url URL to check whether or not it is loadable
     * @return <code>true</code> if this import module supports loading a QuestionCollection from the supplied file, otherwise <code>false</code>
     */
    protected abstract boolean canLoadImpl(final String url);

} // class AbstractCachingImport
