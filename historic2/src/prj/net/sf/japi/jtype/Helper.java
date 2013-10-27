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

package net.sf.japi.jtype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Utility methods for Strings.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
// mögliche Suchstrategien:
// - RE1 (alte und neue Buchstaben) passt
// - RE1 (alte und neue Buchstaben) passt, RE2 (nur alte Buchstaben) passt nicht
// - Buchstabenkombinationen
public final class Helper {

    /** Utility class - do not instanciate. */
    private Helper() {
    }

    /** Returns all strings that match a specific regular expression but not a second one.
     * @param match regular expression pattern.
     * @param nomatch regular expression pattern.
     * @param strings Strings to search.
     * @return All strings that match the pattern specified by match but do not match the pattern specified by nomatch.
     */
    public static List<String> find(@NotNull final Pattern match, @Nullable final Pattern nomatch, @NotNull final Iterable<String> strings) {
        final List<String> found = new ArrayList<String>();
        final Matcher m1 = match.matcher("");
        final Matcher m2 = nomatch != null ? nomatch.matcher("") : null;
        for (final String s : strings) {
            if (m1.reset(s).matches() && (m2 == null || !m2.reset(s).matches())) {
                found.add(s);
            }
        }
        return found;
    }

    /** Returns all strings that match a specific regular expression but not a second one.
     * @param match regular expression.
     * @param nomatch regular expression.
     * @param strings Strings to search.
     * @return All strings that match the regular expression specified by match but do not match the regular expression specified by nomatch.
     */
    public static List<String> find(@NotNull final String match, @Nullable final String nomatch, @NotNull final Iterable<String> strings) {
        return find(Pattern.compile(match), nomatch != null ? Pattern.compile(nomatch) : null, strings);
    }

    /** Returns all Strings from the aspell master.
     * @return List with all words from the aspell master.
     */
    @NotNull public static List<String> getAspellMaster() {
        return executeAndGetLines("aspell dump master");
    }

    /** Returns all Strings from the aspell master.
     * @param language Language for which the aspell master shall be retrieved.
     * @return List with all words from the aspell master in the specified language.
     */
    public static List<String> getAspellMaster(@NotNull final String language) {
        if (!language.matches("^[a-zA-Z_]{2,10}$")) {
            throw new IllegalArgumentException("Illegal language");
        }
        return executeAndGetLines("aspell -l " + language + " dump master");
    }

    /** Returns the output from executing a command.
     * @param command Command to execute.
     * @return The output of the executed command, line by line )without trailing newline character).
     */
    public static List<String> executeAndGetLines(@NotNull final String command) {
        final Runtime runtime = Runtime.getRuntime();
        try {
            final Process process = runtime.exec(command);
            final List<String> lines = new ArrayList<String>();
            @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
            final BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                for (String line; (line = in.readLine()) != null;) {
                    lines.add(line);
                }
                return lines;
            } finally {
                in.close();
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    /** Main program.
     * @param args Command line arguments (try --help).
     */
    public static void main(@NotNull final String... args) {
        for (final String word : find("[eidrEIDR]+", null, getAspellMaster())) {
            System.out.println(word);
        }
    }

    /** Random number generator. */
    private static final Random RANDOM = new Random();

    /** Returns a random String from a List of Strings.
     * @param strings List from which a random String shall be returned.
     * @return A random String from that List.
     */
    public static String random(@NotNull final List<String> strings) {
        return strings.get(RANDOM.nextInt(strings.size()));
    }
}
