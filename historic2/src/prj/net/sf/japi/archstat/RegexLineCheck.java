/*
 * Copyright (C) 2009 - 2010  Christian Hujer
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

package net.sf.japi.archstat;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import org.w3c.dom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.sf.japi.archstat.log.LogEntry;
import net.sf.japi.archstat.log.LogSystem;

/** A Line-based Check.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class RegexLineCheck {

    /** The message type to emit if this check found something. */
    @NotNull private final MessageType messageType;

    /** The name of this check. */
    @NotNull private final String name;

    /** The regular expression for this check.
     * If it matches, the check found something to report.
     */
    @NotNull private final Pattern regex;

    /** The message to emit if this check found something. */
    @NotNull private final String message;

    /** Create a line check.
     * @param elem XML Element with the check information.
     */
    public RegexLineCheck(@NotNull final Element elem) {
        this(Enum.valueOf(MessageType.class, elem.getAttribute("messageType")), elem.getAttribute("name"), Pattern.compile(elem.getAttribute("regex")), elem.getAttribute("message"));
    }

    /** Create a line check.
     * @param messageType The message type to emit if this check found something.
     * @param name The name of this check.
     * @param regex The regular expression for this check.
     * @param message The message to emit if this check found something.
     */
    public RegexLineCheck(@NotNull final MessageType messageType, @NotNull final String name, @NotNull final Pattern regex, @NotNull final String message) {
        this.messageType = messageType;
        this.name = name;
        this.regex = regex;
        this.message = message;
    }

    /** Returns whether or not this line check finds something.
     * @param file File to check (informational purpose only, e.g. for error message).
     * @param line Line to check.
     * @param lineNumber Line number of the line that's checked.
     * @return true if this check found a problem, otherwise false.
     */
    boolean hasProblem(@NotNull final File file, @NotNull final String line, final int lineNumber) {
        boolean ret = false;
        final Matcher matcher = regex.matcher(line);
        if (matcher.find()) {
            final int column = matcher.start();
            ret = true;
            LogSystem.log(new LogEntry(file, line, lineNumber, column + 1, messageType, name, message));
        }
        return ret;
    }

    /** Returns the name of this check.
     * @return The name of this check.
     */
    @NotNull public String getName() {
        return name;
    }

    /** Returns the message type of this check.
     * @return The message type of this check.
     */
    @NotNull public MessageType getMessageType() {
        return messageType;
    }

    /** Returns the plural name of this check.
     * @return The plural name of this check.
     */
    @NotNull public String getPlural() {
        return name; // TODO:2009-02-18:christianhujer:Implement proper plural support.
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(@Nullable final Object obj) {
        return obj != null && obj instanceof RegexLineCheck && ((RegexLineCheck) obj).name.equals(name);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
