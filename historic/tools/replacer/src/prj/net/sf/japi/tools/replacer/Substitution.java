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

package net.sf.japi.tools.replacer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Substitution is a small wrapper class for performing repeated regular expression substitutions.
 * Its main purpose is to provide a sed / perl / vi-like interface to regular expression substitutions.
 *
 * <h4 id="substitutionSyntax">Syntax of the substitution command</h4>
 * <p>
 *  When specifying a substitution with the constructor {@link #Substitution(CharSequence)}, the parameter specifies the substitution.
 *  That parameter MUST follow the syntax described here, which is resembled after the syntax found in sed / perl / vi and similar tools.
 * </p>
 * <p>
 *  Syntax: <code>s/<var>pattern</var>/<var>replacement</var>/<var>flags</var></code>
 * </p>
 * <dl>
 *  <dt><code><var>pattern</var></code></dt>
 *  <dd>
 *      Specifies the regular expression for the substitution.
 *  </dd>
 *  <dt><code><var>replacement</var></code></dt>
 *  <dd>
 *      Specifies the replacement.
 *  </dd>
 *  <dt><code><var>flags</var></code></dt>
 *  <dd>
 *      Specifies the flags.
 *  </dd>
 * </dl>
 *
 * <h4>Supported substitution flags</h4>
 * <p>
 *  The substitution flags are specified as a sequence of characters, each of which activates a certain behaviour.
 *  The flags may be specified in any order and combination.
 *  The following flags are supported:
 * </p>
 * <ul>
 *  <li><code>d</code> activates {@link Pattern#UNIX_LINES}</li>
 *  <li><code>i</code> activates {@link Pattern#CASE_INSENSITIVE}</li>
 *  <li><code>x</code> activates {@link Pattern#COMMENTS}</li>
 *  <li><code>m</code> activates {@link Pattern#MULTILINE}</li>
 *  <li><code>l</code> activates {@link Pattern#LITERAL}</li>
 *  <li><code>s</code> activates {@link Pattern#DOTALL}</li>
 *  <li><code>u</code> activates {@link Pattern#UNICODE_CASE}</li>
 *  <li><code>c</code> activates {@link Pattern#CANON_EQ}</li>
 *  <li><code>g</code> activates global mode (replace all instead of replace first - replace first is default if <code>g</code> is not set)</li>
 *  <li><code>f</code> activates file mode (match pattern against the whole file instead of individual llines - default is line mode)</li>
 * </ul>
 *
 * <h4>Replacement syntax extension</h4>
 * In addition to group reference with \ and $, the replacement part may contain the following escape sequences:
 * <ul>
 *  <li><code>\/</code> A literal '/' (otherwise '/' would terminate the substitution)</li>
 *  <li><code>\\</code> A literel '\'</li>
 *  <li><code>\n</code> The newline character (line feed)</li>
 *  <li><code>\r</code> The carriage return character</li>
 *  <li><code>\t</code> The tab character</li>
 *  <li><code>\f</code> The form feed character</li>
 *  <li><code>\a</code> The bell character</li>
 *  <li><code>\e</code> The escape character</li>
 *  <li><code>\$</code> The dollar character (otherwise would start a backreference)</li>
 * </ul>
 * @note The replacement syntax extension is subject of change.
 *       There are plans to support \l, \L, &#92;u, \U, \E etc. in the replacement syntax.
 *
 * @see Pattern for a detailled description of the supported regular expression syntax.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class Substitution {

    /** The regular expression used to parse an sed / perl / vi like substitution command.
     * The syntax is "s/pattern/replacement/flags".
     * If "/" appears in the pattern or the replacement, it needs to be escaped.
     */
    private static final Pattern RE_PATTERN = Pattern.compile("^s/((?:\\\\/|[^/])*+)/((?:\\\\|\\\\/|[^/])*+)/(.*+)$");

    /** The unmodifiable map with the flags.
     * Key: Flag character.
     * Value: integer flag value for {@link Pattern#compile(String, int)} resp. returned by {@link Pattern#flags()}.
     */
    private static final Map<Character, Integer> FLAG_MAP = createFlagMap();

    /** The pattern used for this substitution. */
    private final String patternString;

    /** The replacement specifier for a substitution. */
    private final String replacement;

    /** The flags used for substitution.
     * Only those flags supported by {@link Pattern} are stored in this variable.
     */
    private final int flags;

    /** The global flag.
     * If true, replaces all occurrences.
     * If false, replaces the first occurrency only.
     */
    private final boolean global;

    /** The file flag.
     * If true, replace file wise.
     * If false, replace line wise.
     */
    private final boolean file;

    /** The matcher for performing the substitution. */
    private final Matcher matcher;

    /** Creates a Substitution based on the specified parameters.
     * @param params Parameters in the order <var>pattern</var>, <var>replacement</var>, <var>flags</var>.
     * @pre params.length == 3
     */
    @SuppressWarnings({"OverloadedVarargsMethod"}) // Not public anyway.
    private Substitution(@NotNull final String... params) {
        this(params[0], params[1], params[2]);
        assert params.length == 3 : "Expected length: 3, got length: " + params.length;
    }

    /** Creates a Substitution based on a substitution command.
     * @param substSpec Substitution command in the correct <a href="#substitutionSyntax">syntax</a>.
     * @see <a href="#substitutionSyntax">Syntax of the substitution command</a>
     */
    public Substitution(@NotNull final CharSequence substSpec) {
        this(parseRegex(substSpec));
    }

    /** Creates a Substitution based on the specified pattern and replacement.
     * @param patternString Regular expression matcher pattern.
     * @param replacement Replacement.
     */
    public Substitution(@NotNull final String patternString, @NotNull final CharSequence replacement) {
        this(patternString, replacement, null);
    }

    /** Creates a Substitution based on the specified pattern and replacement.
     * @param patternString  Regular expression matcher pattern.
     * @param replacement    Replacement.
     * @param flags          Substitution Flags (currently ignored).
     * @param global         <code>true</code> to replace all, <code>false<code> to replace first only.
     * @param file           <code>true</code> to match file, <code>false<code> to match lines.
     */
    public Substitution(@NotNull final String patternString, @NotNull final CharSequence replacement, final int flags, final boolean global, final boolean file) {
        this.patternString = patternString;
        this.replacement   = unescapeReplacement(replacement);
        this.flags         = flags;
        this.global        = global;
        this.file          = file;
        matcher = Pattern.compile(patternString, flags).matcher("");
    }

    /** Takes a String (usually the replacement part of a substitution) and replaces escape sequences.
     * @param replacement String to unescape.
     * @return Unescaped String.
     */
    private static String unescapeReplacement(final CharSequence replacement) {
        boolean escape = false;
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < replacement.length(); i++) {
            final char c = replacement.charAt(i);
            if (escape) {
                escape = false;
                switch (c) {
                // Supporting \t to \e brings at least a subset of the escape sequences supported in patters into the substitution part.
                case 't': sb.append('\t'); break;
                case 'n': sb.append('\n'); break;
                case 'r': sb.append('\r'); break;
                case 'f': sb.append('\f'); break;
                case 'a': sb.append('\u0007'); break;
                case 'e': sb.append('\u001b'); break;
                case '$': sb.append("\\$"); break;
                case '\\': sb.append("\\\\"); break;
                default:
                    if (c >= '0' && c <= '9') {
                        sb.append('\\').append(c);
                    } else {
                        throw new IllegalArgumentException("Unsupported escape sequence in replacement part.");
                    }
                }
            } else {
                switch (c) {
                case '\\':
                    escape = true;
                    break;
                default:
                    sb.append(c);
                }
            }
        }
        if (escape) {
            throw new IllegalArgumentException("Incomplete escape sequence in replacement part.");
        }
        return sb.toString();
    }

    /** Creates a Substitution based on the specified pattern and replacement.
     * @param patternString  Regular expression matcher pattern.
     * @param replacement    Replacement.
     * @param flags          Substitution Flags (currently ignored).
     */
    public Substitution(@NotNull final String patternString, @NotNull final CharSequence replacement, @Nullable final String flags) {
        this(patternString, replacement, parseFlags(flags), isGlobal(flags), isFile(flags));
    }

    /** Creates an unmodifiable map with the flags.
     * @return Unmodifiable map with the flags.
     */
    private static Map<Character, Integer> createFlagMap() {
        final Map<Character, Integer> flagMap = new HashMap<Character, Integer>();
        flagMap.put('d', Pattern.UNIX_LINES);
        flagMap.put('i', Pattern.CASE_INSENSITIVE);
        flagMap.put('x', Pattern.COMMENTS);
        flagMap.put('m', Pattern.MULTILINE);
        flagMap.put('l', Pattern.LITERAL);
        flagMap.put('s', Pattern.DOTALL);
        flagMap.put('u', Pattern.UNICODE_CASE);
        flagMap.put('c', Pattern.CANON_EQ);
        flagMap.put('g', null); // g is supported by Substitution but not by Pattern / Matcher, we handle it ourselves.
        flagMap.put('f', null); // f is supported by Substitution but not by Pattern / Matcher, we handle it ourselves.
        return Collections.unmodifiableMap(flagMap);
    }

    /** Converts a flag string into a flag value.
     * @param flagString String that specifies substitution flags.
     * @return Integer value with the flags.
     * @throws IllegalArgumentException in case an unsupported flag was specified.
     */
    public static int parseFlags(@Nullable final String flagString) {
        int flags = 0;
        if (flagString != null) {
            StringBuilder bogusFlags = null;
            for (final char c : flagString.toCharArray()) {
                if (FLAG_MAP.containsKey(c)) {
                    final Integer flag = FLAG_MAP.get(c);
                    if (flag != null) {
                        flags |= flag;
                    }
                } else {
                    if (bogusFlags == null) {
                        bogusFlags = new StringBuilder();
                    }
                    bogusFlags.append(c);
                }
            }
            if (bogusFlags != null) {
                throw new IllegalArgumentException("Unsupported flags: " + bogusFlags);
            }
        }
        return flags;
    }

    /** Returns whether the flags have the global flag set.
     * @param flagString String that specifies substitution flags.
     * @return <code>true</code> if the flagString has the g flag set, otherwise <code>false</code>
     */
    public static boolean isGlobal(@Nullable final String flagString) {
        return flagString != null && flagString.contains("g");
    }

    /** Returns whether the flags have the file flag set.
     * @param flagString String that specifies substitution flags.
     * @return <code>true</code> if the flagString has the f flag set, otherwise <code>false</code>
     */
    public static boolean isFile(@Nullable final String flagString) {
        return flagString != null && flagString.contains("f");
    }

    /** Splits a substitution String into its subcomponents.
     * @param regex Substitution String to split.
     * @return The three subcomponents pattern, replacement and flags.
     */
    public static String[] parseRegex(@NotNull final CharSequence regex) {
        final Matcher matcher = RE_PATTERN.matcher(regex);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Malformed substitution command.");
        }
        final String[] parsed = new String[3];
        parsed[0] = matcher.group(1);
        parsed[1] = matcher.group(2);
        parsed[2] = matcher.group(3);
        return parsed;
    }

    /** Returns a String which represents <var>s</var> after performing all substitutions.
     * @param s CharSequence in which to perform the substitution.
     * @return a String which represents <var>s</var> after performing all substitutions.
     */
    public String replace(final CharSequence s) {
        if (file) {
            matcher.reset(s);
            return global ? matcher.replaceAll(replacement) : matcher.replaceFirst(replacement);
        } else {
            final StringBuilder sb = new StringBuilder();
            final Pattern linePattern = Pattern.compile((flags & Pattern.UNIX_LINES) == Pattern.UNIX_LINES ? "(?<=\\n)" : "(?<=\\n|\u0085|\u2028|\u2029|\\r(?!\\n))");
            for (final CharSequence line : linePattern.split(s)) {
            //for (final CharSequence line : new LineIterable(s)) {
                matcher.reset(line);
                sb.append(global ? matcher.replaceAll(replacement) : matcher.replaceFirst(replacement));
            }
            return sb.toString();
        }
    }

    /** Returns the regular expression pattern that is used to perform this Substitution.
     * @return The regular expression pattern that is used to perform this Substitution.
     */
    public String getPatternString() {
        return patternString;
    }

    /** Returns the replacement String that is used to perform this Substitution.
     * @return The replacement String that is used to perform this Substitution.
     */
    public String getReplacement() {
        return replacement;
    }

    /** Returns the flags that are used to perform this Substitution.
     * @note This method returns only those flags known to {@link Pattern}.
     * @return The flags that are used to perform this Substitution.
     * @see #isFile() For the global flag.
     * @see #isGlobal() For the file flag.
     */
    public int getFlags() {
        return flags;
    }

    /** Returns whether or not the file flag is set.
     * @return <code>true</code> if the file flag is set, otherwise <code>false</code>.
     */
    public boolean isFile() {
        return file;
    }

    /** Returns whether or not the global flag is set.
     * @return <code>true</code> if the global flag is set, otherwise <code>false</code>.
     */
    public boolean isGlobal() {
        return global;
    }

    /** Returns the flag map.
     * @return The flag map.
     */
    public static Map<Character, Integer> getFlagMap() {
        //The collection already is unmodifiable.
        //noinspection ReturnOfCollectionOrArrayField
        return FLAG_MAP;
    }
}
