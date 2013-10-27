/* JAPI - (Yet another (hopefully) useful) Java API
 *
 * Copyright (C) 2004-2006 Christian Hujer
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.japi.cpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static net.sf.japi.cpp.State.CHAR;
import static net.sf.japi.cpp.State.CHAR_ESCAPE;
import static net.sf.japi.cpp.State.DIRECTIVE;
import static net.sf.japi.cpp.State.DIRECTIVE_ESCAPE;
import static net.sf.japi.cpp.State.EOL_COMMENT;
import static net.sf.japi.cpp.State.MAYBE_COMMENT;
import static net.sf.japi.cpp.State.MAYBE_ENDOF_COMMENT;
import static net.sf.japi.cpp.State.MULTILINE_COMMENT;
import static net.sf.japi.cpp.State.NORMAL;
import static net.sf.japi.cpp.State.STRING;
import static net.sf.japi.cpp.State.STRING_ESCAPE;

/** A C Preprocessor.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class CPreProcessor {

    /** Synchronization lock object. */
    private final Object syncLock = new Object();

    /** List with include directory paths. */
    private final List<File> includePaths = new ArrayList<File>();

    /** Map with included files. */
    private final Map<String, String> includes = new HashMap<String, String>();

    /** Map with defined macros. */
    private final Map<String, String> defines = new HashMap<String, String>();

    /** Setting whether to strip comments. */
    private final boolean stripComments = true;

    public CPreProcessor() {
    }

    /** Add an include path.
     * @param file include path to add
     * @throws IllegalArgumentException in case <var>file</var> is not a directory
     */
    public void addIncludePath(final File file) {
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("Only directories are valid for include paths.");
        }
        includePaths.add(file);
    }

    /** Get a list with all include paths.
     * @return list with all include paths
     */
    public List<File> getIncludePaths() {
        return unmodifiableList(includePaths);
    }

    private String include(final String filename) throws IOException {
        synchronized (syncLock) {
            if (!includes.containsKey(filename)) {
                includes.put(filename, readFile(filename));
            }
            assert includes.get(filename) != null;
            return includes.get(filename);
        }
    }

    private static String readFile(final String filename) throws IOException {
        final StringBuilder chain = new StringBuilder();
        final char[] buffer = new char[4096];
        final Reader in = new FileReader(filename);
        try {
            for (int bytesRead; (bytesRead = in.read(buffer)) != -1;) {
                chain.append(buffer, 0, bytesRead);
            }
        } finally {
            in.close();
        }
        return chain.toString();
    }

    public void process(final Reader in, final Writer out) throws IOException {
        new Processor(in, out);
    }

    private class Processor {

        private final Reader in;
        private final Writer out;
        private int c;
        private final StringBuilder currentDirective = new StringBuilder();
        private final StringBuilder currentDirectiveArgs = new StringBuilder();

        private Processor(final Reader inStream, final Writer outStream) throws IOException {
            //noinspection IOResourceOpenedButNotSafelyClosed
            in  = inStream instanceof BufferedReader ? (BufferedReader) inStream : new BufferedReader(inStream);
            //noinspection IOResourceOpenedButNotSafelyClosed
            out = outStream instanceof PrintWriter ? (PrintWriter) outStream : new PrintWriter(outStream);
            run();
            out.flush();
        }

        private void run() throws IOException {
            State state = NORMAL;
            while ((c = in.read()) != -1) {
                switch (state) {
                    case NORMAL:
                        switch (c) {
                            case '#':  state = DIRECTIVE;     break;
                            case '/':  state = MAYBE_COMMENT; break;
                            case '"':  out.write(c); state = STRING; break;
                            case '\'': out.write(c); state = CHAR; break;
                            default: out.write(c);
                        } break;
                    case DIRECTIVE:
                        switch (c) {
                            case '\\': state = DIRECTIVE_ESCAPE; break;
                            case '\n': state = NORMAL; processDirective();
                        } break;
                    case DIRECTIVE_ESCAPE:
                        state = DIRECTIVE; break;
                    case MAYBE_COMMENT:
                        switch (c) {
                            case '*': if (!stripComments) { out.write('/'); out.write(c); } state = MULTILINE_COMMENT; break;
                            case '/': if (!stripComments) { out.write('/'); out.write(c); } state = EOL_COMMENT; break;
                            default: out.write('/'); out.write(c);
                        } break;
                    case MULTILINE_COMMENT:
                        switch (c) {
                            case '*': if (!stripComments) { out.write(c); } state = MAYBE_ENDOF_COMMENT; break;
                            default: if (!stripComments) { out.write(c); }
                        } break;
                    case EOL_COMMENT:
                        switch (c) {
                            case '\n': out.write(c); state = NORMAL; break;
                            default: if (!stripComments) { out.write(c); }
                        } break;
                    case MAYBE_ENDOF_COMMENT:
                        switch (c) {
                            case '/': if (!stripComments) { out.write(c); } state = NORMAL; break;
                            case '*': if (!stripComments) { out.write(c); } break;
                            default: if (!stripComments) { out.write(c); } state = MULTILINE_COMMENT; break;
                        } break;
                    case STRING:
                        switch (c) {
                            case '\\': state = STRING_ESCAPE; break;
                            case '"': state = NORMAL; break;
                        } break;
                    case STRING_ESCAPE:
                        state = STRING; break;
                    case CHAR:
                        switch (c) {
                            case '\\': state = CHAR_ESCAPE; break;
                            case '\'': state = NORMAL; break;
                        } break;
                    case CHAR_ESCAPE:
                        state = CHAR; break;
                }
            }
        }

        private void processDirective() {
        }

    } // class Processor

    /** Main program.
     * @param args command line arguments
     * @throws IOException in case of I/O problems.
     */
    public static void main(final String... args) throws IOException {
        new CPreProcessor().process(System.in, System.out);
    }

    public void process(final InputStream in, final OutputStream out) throws IOException {
        //noinspection IOResourceOpenedButNotSafelyClosed
        process(new InputStreamReader(in), new OutputStreamWriter(out));
    }

} // class CPreProcessor
