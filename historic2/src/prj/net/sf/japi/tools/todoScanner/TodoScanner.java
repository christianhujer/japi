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

package net.sf.japi.tools.todoScanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.io.args.Option;
import org.jetbrains.annotations.NotNull;

/** Scanner for comments of specific formats like to do comments.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class TodoScanner extends BasicCommand {

    /** The file encoding that is used for reading and writing files as weil es parsing substitutions from {@link System#in}. */
    private final Charset encoding = Charset.defaultCharset();

    /** The default list of keywords. */
    private static final List<String> DEFAULT_KEYWORDS = Collections.unmodifiableList(Arrays.asList("TODO", "FIXME", "XXX"));

    /** The list of keywords to scan for. */
    private final List<String> keywords = new ArrayList<String>(DEFAULT_KEYWORDS);

    /** Lazy-initialized pattern. */
    private Pattern pattern;

    /** Lazy-initialized matcher. */
    private Matcher matcher;

    /** Whether to operate recursively. */
    private boolean recursive;

    /** Main program.
     * @param args Command line arguments (try --help).
     */
    public static void main(@NotNull final String... args) {
        ArgParser.simpleParseAndRun(new TodoScanner(), args);
    }

    /** Clears the list of keywords. */
    @Option({"c", "clear"})
    public void clearListOfKeywords() {
        keywords.clear();
    }

    /** Adds an entry to the list of keywords.
     * @param keyword Keyword to add to the list of keywords.
     */
    @Option({"a", "add"})
    public void addKeyword(@NotNull final String keyword) {
        keywords.add(keyword);
        // pattern and matcher are no longer up-to-date, delete them.
        pattern = null;
        matcher = null;
    }

    /** Sets to operate recursively. */
    @Option({"r"})
    public void setRecursive() {
        setRecursive(true);
    }

    /** Sets whether or not to operate recursively.
     * @param recursive whether or not to operate recursively.
     */
    @Option({"recursive"})
    public void setRecursive(final boolean recursive) {
        this.recursive = recursive;
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public int run(@NotNull final List<String> args) throws Exception {
        init();
        if (args.size() == 0) {
            scanForTodos(System.in);
        } else {
            for (final String filename : args) {
                scanForTodos(new File(filename));
            }
        }
        return 0;
    }

    /** Scans for todos in the specified file.
     * @param file File to scan for todos.
     * @throws IOException In case of I/O problems.
     */
    public void scanForTodos(@NotNull final File file) throws IOException {
        try {
            if (file.isDirectory() && recursive) {
                for (final File entry : file.listFiles()) {
                    scanForTodos(entry);
                }
            }
            if (file.isFile() || !recursive) {
                final InputStream in = new FileInputStream(file);
                try {
                    scanForTodos(in);
                } finally {
                    in.close();
                }
            }
        } catch (final IOException e) {
            System.err.println("Error while reading " + file + ": " + e);
        }
    }

    /** Scans for todos in the specified stream.
     * @param in Stream to scan for todos.
     * @throws IOException In case of I/O problems.
     */
    public void scanForTodos(@NotNull final InputStream in) throws IOException {
        //noinspection IOResourceOpenedButNotSafelyClosed
        scanForTodos(new InputStreamReader(in, encoding));
    }

    /** Scans for todos in the specified stream.
     * @param cin Stream to scan for todos.
     * @throws IOException In case of I/O problems.
     */
    public void scanForTodos(@NotNull final Reader cin) throws IOException {
        @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
        final BufferedReader in = cin instanceof BufferedReader ? (BufferedReader) cin : new BufferedReader(cin);
        int lineNumber = 0;
        for (String line; (line = in.readLine()) != null; lineNumber++) {
            if (isMatching(line)) {
                System.out.println(lineNumber + ": " + line);
            }
        }
    }

    /** Initializes pattern and matcher to match any of the keywords. */
    public void init() {
        if (pattern == null) {
            final StringBuilder regex = new StringBuilder();
            regex.append("\\b(");
            boolean first = true;
            for (final String keyword : keywords) {
                if (!first) {
                    regex.append("|");
                }
                regex.append(keyword);
                first = false;
            }
            regex.append(")\\b");
            pattern = Pattern.compile(regex.toString());
            assert matcher == null;
            matcher = pattern.matcher("");
        }
        assert matcher != null;
    }

    /** Returns whether or not the string contains one of the keywords.
     * @param s String to test.
     * @return <code>true</code> if the string contains one of the keywords, otherwise <code>false</code>.
     */
    public boolean isMatching(@NotNull final CharSequence s) {
        matcher.reset(s);
        return matcher.find();
    }

}
