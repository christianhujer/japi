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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.SAXException;

/** Provides access to different IO-Modules.
 * @param <T> Type for which to provide IO modules.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo add MIME Type support, perhaps supporting classes like DataFlavor
 * @todo add auto configuration support via system properties
 */
public final class IOModuleProvider<T> implements Importer<T>, Exporter<T> {

    /** Type for this IOMOduleProvider. */
    private final Class<T> type;

    /** List with exporters. */
    private final List<Exporter<T>> exporters;

    /** Default exporter. */
    private Exporter<T> defaultExporter;

    /** List with importers. */
    private final List<Importer<T>> importers;

    /** Map with instances. */
    private static final Map<Class<?>, IOModuleProvider<?>> PROVIDERS = new HashMap<Class<?>, IOModuleProvider<?>>();

    /** Create a new IOModuleProvider.
     * @param type Type to im- or export with this IOModuleProvider.
     */
    private IOModuleProvider(final Class<T> type) {
        this.type = type;
        exporters = new ArrayList<Exporter<T>>();
        importers = new ArrayList<Importer<T>>();
    }

    /** Get the provider for a certain class.
     * @param c Class to get provider for
     * @return IOModuleProvider for c
     */
    public static <E> IOModuleProvider<E> getProvider(final Class<E> c) {
        IOModuleProvider<E> provider = (IOModuleProvider<E>) PROVIDERS.get(c); // todo: avoid unchecked cast if possible
        if (provider == null) {
            provider = new IOModuleProvider<E>(c);
            PROVIDERS.put(c, provider);
        }
        return provider;
    }

    /** Get all registered Export Modules.
     * @return Collection of export modules
     */
    public Collection<Exporter<T>> getExporters() {
        return Collections.unmodifiableCollection(exporters);
    }

    /** Get all registered Import Modules.
     * @return Collection of import modules
     */
    public Collection<Importer<T>> getImporters() {
        return Collections.unmodifiableCollection(importers);
    }

    /** Register a module.
     * It is safe to register a module more than once.
     * Subsequent registrations of an already registered module are ignored.
     * @param module Module to register, should be an instance of {@link Importer}, {@link Exporter} or both.
     */
    public void register(final IOBase<T> module) {
        if (module instanceof Importer && !importers.contains(module)) {
            importers.add((Importer<T>) module);
        }
        if (module instanceof Exporter && !exporters.contains(module)) {
            exporters.add((Exporter<T>) module);
        }
    }

    /** {@inheritDoc}
     * Tries all registered importers in registration order to load the document.
     * @throws IOException in case of I/O-problems
     * @throws SAXException in case of XML problems
     */
    public T load(final String url) throws IOException, SAXException {
        for (final Importer<T> module : importers) {
            if (module.canLoad(url)) {
                System.err.println(module);
                return module.load(url);
            }
        }
        throw new IllegalArgumentException("Nicht unterstützter Dateityp");
    }

    /** {@inheritDoc}
     * Uses the default exporter or, if unset, the first exporter to save.
     * @throws IOException in case of I/O-problems
     * @throws IllegalStateException in no defaultExporter was set and the list of exporters is empty.
     */
    public void save(final T doc, final File file) throws IOException {
        final Exporter<T> exporter;
        if (defaultExporter != null) {
            exporter = defaultExporter;
        } else if (exporters.size() == 0) {
            throw new IllegalStateException("IOModuleProvider has no default exporter and is empty.");
        } else {
            exporter = exporters.get(0);
        }
        exporter.save(doc, file);
    }

    /** Set the default exporter.
     * @param exporter default exporter
     */
    public void setDefaultExporter(final Exporter<T> exporter) {
        if (!exporters.contains(exporter)) {
            exporters.add(exporter);
        }
        defaultExporter = exporter;
    }

    /** Get the default exporter.
     * @return default exporter or <code>null</code> if no default exporter is set
     */
    public Exporter<T> getDefaultExporter() {
        return defaultExporter;
    }

    /** {@inheritDoc} */
    public boolean canLoad(final String uri) {
        for (final Importer<T> module : importers) {
            if (module.canLoad(uri)) {
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    public String getName() {
        return "Alle unterstützten Dateitypen"; // TODO:2009-02-22:christianhujer:i18n/l10n
    }

    /** {@inheritDoc} */
    public Class<T> getType() {
        return type;
    }

} // class IOModuleProvider
