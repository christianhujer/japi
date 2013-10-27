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

package net.sf.japi.progs.jeduca.jtest;

import java.io.File;
import java.io.IOException;
import net.sf.japi.progs.jeduca.jtest.io.JTestSer;
import net.sf.japi.progs.jeduca.jtest.io.JTestV1;
import net.sf.japi.progs.jeduca.jtest.io.KEduca;
import net.sf.japi.progs.jeduca.swing.io.IOModuleProvider;
import net.sf.japi.swing.recent.PrefsRecentURLs;
import org.xml.sax.SAXException;

/** The Program contains the basic references such as the current QuestionCollection and provides important main operations like loading and saving
 * files.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo Refactor to Program 1-n Document, find an alternative common name for Document to avoid conflict with XML names.
 */
public class Program {

    /** The IOProvider. */
    private final IOModuleProvider<QuestionCollection> ioProvider = IOModuleProvider.getProvider(QuestionCollection.class);

    /** The KEduca filter reference. */
    private final KEduca keduca;

    /** The JTestV1 filter reference. */
    private final JTestV1 jtestv1;

    /** The Settings. */
    private final Settings settings;

    /** The currently loaded Question Collection. */
    private QuestionCollection col;

    /** The URL of the currently loaded Question Collection. */
    private String url;

    /** Create a Program. */
    public Program() {
        ioProvider.register(new KEduca());
        ioProvider.register(new JTestV1());
        ioProvider.register(new JTestSer());
        settings = new Settings();
        settings.load();
        keduca = new KEduca();
        jtestv1 = new JTestV1();
    }

    /** Set the QuestionCollection.
     * @param col QuestionCollection
     */
    public void setQuestionCollection(final QuestionCollection col) {
        this.col = col;
        // TODO:2009-02-22:christianhujer:Event Handling
    }

    /** Get the QuestionCollection.
     * @return QuestionCollection
     */
    public QuestionCollection getQuestionCollection() {
        return col;
    }

    /** Get the Settings.
     * @return Settings
     */
    public Settings getSettings() {
        return settings;
    }

    ///** Import a QuestionCollection from KEduca.
    // * @param f File to load
    // * @throws IOException in case of I/O-problems
    // * @throws SAXException in case of XML parsing errors
    // */
    //public void eduImport(final File f) throws IOException, SAXException {
    //    setQuestionCollection(keduca.load(f));
    //}

    ///** Import a QuestionCollection from KEduca.
    // * @param in InputStream to load
    // * @throws IOException in case of I/O-problems
    // * @throws SAXException in case of XML parsing errors
    // */
    //public void eduImport(final InputStream in) throws IOException, SAXException {
    //    setQuestionCollection(keduca.load(in));
    //}

    ///** Import a QuestionCollection from KEduca.
    // * @param uri URI to load
    // * @throws IOException in case of I/O-problems
    // * @throws SAXException in case of XML parsing errors
    // */
    //public void eduImport(final String uri) throws IOException, SAXException {
    //    setQuestionCollection(keduca.load(uri));
    //}

    /** Export a QuestionCollection to KEduca.
     * @param f File to save
     * @throws IOException in case of I/O-problems
     */
    public void eduExport(final File f) throws IOException {
        keduca.save(col, f);
    }

    ///** Import a QuestionCollection vom JTestV1.
    // * @param f File to load
    // * @throws IOException in case of I/O-problems
    // * @throws SAXException in case of XML parsing errors
    // */
    //public void jtestV1Import(final File f) throws IOException, SAXException {
    //    setQuestionCollection(jtestv1.load(f));
    //}

    ///** Import a QuestionCollection vom JTestV1.
    // * @param in InputStream to load
    // * @throws IOException in case of I/O-problems
    // * @throws SAXException in case of XML parsing errors
    // */
    //public void jtestV1Import(final InputStream in) throws IOException, SAXException {
    //    setQuestionCollection(jtestv1.load(in));
    //}

    ///** Import a QuestionCollection vom JTestV1.
    // * @param uri URI to load
    // * @throws IOException in case of I/O-problems
    // * @throws SAXException in case of XML parsing errors
    // */
    //public void jtestV1Import(final String uri) throws IOException, SAXException {
    //    setQuestionCollection(jtestv1.load(uri));
    //}

    ///** Export a QuestionCollection to JTestV1.
    // * @param f File to save
    // * @throws IOException in case of I/O-problems
    // */
    //public void jtestV1Export (final File f) throws IOException {
    //    jtestv1.save(col, f);
    //}

    /** Get the IOModuleProvider.
     * @return IOModuleProvider
     */
    public IOModuleProvider<QuestionCollection> getIOModuleProvider() {
        return ioProvider;
    }

    /** Load a document.
     * @param uri URL to load
     * @throws IOException in case of I/O-problems
     * @throws SAXException in case of XML parsing errors
     */
    public void load(final String uri) throws IOException, SAXException {
        setQuestionCollection(ioProvider.load(uri));
        PrefsRecentURLs.getInstance(Settings.class).addRecentlyURL(uri);
    }

} // class Program
