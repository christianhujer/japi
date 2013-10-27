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

import java.io.File;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/** Swing FileChooser FileFilter for filtering Importers (Import Modules).
 * The class itself controls what instances exist, so in larger applications you don't run into the danger of memory bashing by creating superfluous
 * instances of this class.
 * @param <T> Type of the importer for which to filter.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class ImporterFileFilter<T> extends FileFilter {

    /** The Import Module. */
    private final Importer<T> importer;

    /** The created instances. */
    private static final Map<Importer, WeakReference<ImporterFileFilter>> FILTERS = new WeakHashMap<Importer, WeakReference<ImporterFileFilter>>();

    /** Create an ImporterFileFilter.
     * The recommended way to create an ImporterFileFilter is to use the static method {@link #getInstanceFor(Importer)}.
     * @param importer Importer to create filter for
     */
    private ImporterFileFilter(final Importer<T> importer) {
        this.importer = importer;
    }

    /** Get an ImporterFileFilter.
     * @param importer Importer to get ImporterFileFilter for
     * @return ImporterFileFilter for the specified Importer.
     */
    public static <E> ImporterFileFilter<E> getInstanceFor(final Importer<E> importer) {
        final WeakReference<ImporterFileFilter<E>> ref = (WeakReference<ImporterFileFilter<E>>) (Object) FILTERS.get(importer);
        ImporterFileFilter<E> filter;
        if (ref == null || (filter = ref.get()) == null) {
            FILTERS.put(importer, new WeakReference<ImporterFileFilter>(filter = new ImporterFileFilter<E>(importer)));
        }
        return filter;
    }

    /** {@inheritDoc} */
    @Override
    public boolean accept(final File f) {
        boolean accept = f.isDirectory();
        try {
            if (!accept) {
                accept = importer.canLoad(f.toURI().toURL().toString());
            }
        } catch (MalformedURLException e) {
            System.err.println(e);
        }
        return accept;
    }

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return importer.getName();
    }

    /** Get the Importer this ImporterFileFilter was created for.
     * @return Importer
     */
    public Importer<T> getImporter() {
        return importer;
    }

    /** Create FileFilters for all importers that are provided by a JTest IOModuleProvider.
     * @param provider JTest IOModuleProvider to create FileFilters for
     * @return Collection with FileFilters
     */
    public static <E> Collection<ImporterFileFilter<E>> createFileFilters(final IOModuleProvider<E> provider) {
        final List<ImporterFileFilter<E>> filters = new ArrayList<ImporterFileFilter<E>>();
        filters.add(getInstanceFor(provider));
        for (final Importer<E> importer : provider.getImporters()) {
            filters.add(getInstanceFor(importer));
        }
        return filters;
    }

    /** Initialize a JFileChooser with all importers that are provided by a JTest IOModuleProvider.
     * @param provider JTest IOModuleProvider to create FileFilters for
     * @param chooser JFileChooser to add FileFilters to
     * @todo recursively add filters for providers that reference providers
     */
    public static <E> void attachFileFilters(final JFileChooser chooser, final IOModuleProvider<E> provider) {
        for (final ImporterFileFilter<E> filter : createFileFilters(provider)) {
            chooser.addChoosableFileFilter(filter);
        }
    }

} // class ImporterFileFilter

