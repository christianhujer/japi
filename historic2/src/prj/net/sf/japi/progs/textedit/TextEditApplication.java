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

package net.sf.japi.progs.textedit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import javax.swing.text.PlainDocument;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.swing.app.AppLaunchCommand;
import net.sf.japi.swing.app.Application;
import net.sf.japi.swing.app.Document;
import org.jetbrains.annotations.NotNull;

/**
 * TODO
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class TextEditApplication extends Application<PlainDocument> {

    /** The default buffer size. */
    private static final int BUF_SIZE = 8192;

    /** Main program.
     * @param args Command line arguments (try --help).
     */
    public static void main(final String... args) {
        ArgParser.simpleParseAndRun(new AppLaunchCommand(TextEditApplication.class), args);
    }

    /** Creates a TextEditApplication. */
    public TextEditApplication() {
    }

    /** {@inheritDoc} */
    @Override
    public void save(@NotNull final Document<PlainDocument> doc, @NotNull final String uri) throws Exception {
        final Writer out = new BufferedWriter(new OutputStreamWriter(openUriForwriting(uri)));
        try {
            final PlainDocument data = doc.getData();
            out.write(data.getText(0, data.getLength()));
            out.flush();
        } finally {
            out.close();
        }
    }

    /** {@inheritDoc} */
    @Override
    @NotNull public TextDocument load(@NotNull final String uri) throws Exception {
        final StringBuilder sb = new StringBuilder();
        final Reader in = new BufferedReader(new InputStreamReader(openUriForReading(uri)));
        try {
            final char[] buf = new char[BUF_SIZE];
            for (int charsRead; (charsRead = in.read(buf)) != -1;) {
                sb.append(buf, 0, charsRead);
            }
        } finally {
            in.close();
        }
        final PlainDocument doc = new PlainDocument();
        doc.insertString(0, sb.toString(), null);
        return new TextDocument(uri, null, doc);
    }

    /** {@inheritDoc} */
    @Override
    @NotNull public TextDocument createNew() {
        final PlainDocument doc = new PlainDocument();
        return new TextDocument(null, null, doc);
    }
}
