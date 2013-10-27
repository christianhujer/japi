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

import java.io.IOException;
import org.xml.sax.SAXException;

/** Adapter class to have a caching implementation of {@link Importer} where the Importer-implementation cannot extend {@link AbstractCachingImporter}.
 * @param <T> Type which is imported.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class CachingImporterAdapter<T> extends AbstractCachingImporter<T> {

    /** Importer to adapt. */
    private final Importer<T> importer;

    /** Create a CachingImporter for another Importer.
     * @param importer Importer to adapt
     */
    public CachingImporterAdapter(final Importer<T> importer) {
        this.importer = importer;
    }

    /** {@inheritDoc}
     * Invokes canLoad of the adapted importer.
     */
    @Override protected boolean canLoadImpl(final String url) {
        return importer.canLoad(url);
    }

    /** {@inheritDoc}
     * Invokes load of the adapted importer.
     */
    public T load(final String url) throws IOException, SAXException {
        return importer.load(url);
    }

    /** {@inheritDoc}
     * Invokes getName of the adapted importer.
     */
    public String getName() {
        return importer.getName();
    }

    /** {@inheritDoc}
     * Invokes getType of the adapted importer.
     */
    public Class<T> getType() {
        return importer.getType();
    }

} // class CachingImporterAdapter
