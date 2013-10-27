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

package net.sf.japi.progs.jeduca.jtest.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import net.sf.japi.progs.jeduca.jtest.QuestionCollection;
import net.sf.japi.progs.jeduca.swing.io.Exporter;

/** Interface for reading and writing serialized JTest files.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JTestSer extends AbstractJTestImport<QuestionCollection> implements Exporter<QuestionCollection> {

    /** Create a new JTestSer interface.
     */
    public JTestSer() {
    }

    ///** Get all questions from a .jtest file.
    // * @param f .jtest-File
    // * @return all questions from f
    // * @throws IOException in case of I/O-problems
    // */
    //public QuestionCollection load(final File f) throws IOException {
    //    InputStream in = null;
    //    try {
    //        return load(in = new FileInputStream(f));
    //    } finally {
    //        try { in.close(); } catch (Exception e) { /* ignore */ } in = null;
    //    }
    //}

    /** Get all questions from a .edu URI.
     * @param uri .edu-URI
     * @return all questions from uri
     * @throws IOException in case of I/O-problems
     */
    public QuestionCollection load(final String uri) throws IOException {
        final InputStream in = new URL(uri).openStream();
        try {
            return load(in);
        } finally {
            in.close();
        }
    }

    /** Get all questions from a .edu stream.
     * @param in .edu-Stream
     * @return all questions from in
     * @throws IOException in case of I/O-problems
     */
    private QuestionCollection load(final InputStream in) throws IOException {
        try {
            //noinspection IOResourceOpenedButNotSafelyClosed
            return (QuestionCollection) new ObjectInputStream(in).readObject();
        } catch (final ClassNotFoundException e) {
            throw new IOException("This isn't a serialized JTest file", e);
        }
    }

    /** Export a QuestionCollection as serialized JTest file.
     * @param col QuestionCollection to create serialized JTest file from
     * @param f File to save
     * @throws IOException in case of I/O-problems
     */
    public void save(final QuestionCollection col, final File f) throws IOException {
        final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
        try {
            out.writeObject(col);
        } finally {
            out.close();
        }
    }

    /** {@inheritDoc} */
    public String getName() {
        return "Serialized JTest File";
    }

    /** {@inheritDoc} */
    public Class<QuestionCollection> getType() {
        return QuestionCollection.class;
    }

    /** {@inheritDoc} */
    @Override
    protected boolean canLoadImpl(final String uri) {
        try {
            load(uri);
            return true;
        } catch (final Exception ignore) {
            return false;
        }
    }

} // class JTestSer
