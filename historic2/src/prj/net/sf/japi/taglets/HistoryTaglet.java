/*
 * Copyright (C) 2009  Christian Hujer.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.sf.japi.taglets;

import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

/**
 * Taglet for history.
 * Format: history date developer  Comment.
 * Separate developers and Comment with two whitespace.
 * Date must be ISO.
 * You shouldn't use it for something your version control system can handle anyway but in a much more reliable way.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class HistoryTaglet implements Taglet {

    /** The pattern to use for checking. */
    private static final Pattern PATTERN = Pattern.compile("^(\\d\\d\\d\\d-\\d\\d-\\d\\d) (.*?)  (.*?)$");

    /** The DateFormat for ISO dates. */
    private static final DateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /** {@inheritDoc} */
    public boolean inField() {
        return true;
    }

    /** {@inheritDoc} */
    public boolean inConstructor() {
        return true;
    }

    /** {@inheritDoc} */
    public boolean inMethod() {
        return true;
    }

    /** {@inheritDoc} */
    public boolean inOverview() {
        return true;
    }

    /** {@inheritDoc} */
    public boolean inPackage() {
        return true;
    }

    /** {@inheritDoc} */
    public boolean inType() {
        return true;
    }

    /** {@inheritDoc} */
    public boolean isInlineTag() {
        return false;
    }

    /** {@inheritDoc} */
    public String getName() {
        return "history";
    }

    /** {@inheritDoc} */
    public String toString(final Tag tag) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<DT><B>");
        sb.append("History:");
        sb.append("</B></DT><DD><TABLE><TR><TH>Date</TH><TH>Developer(s)</TH><TH>Changes</TH></TR>");
        append(sb, tag);
        sb.append("</TABLE></DD>");
        return sb.toString();
    }

    /** {@inheritDoc} */
    @Nullable
    public String toString(final Tag[] tags) {
        if (tags == null || tags.length == 0) {
            return null;
        }
        if (tags.length == 1) {
            return toString(tags[0]);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("<DT><B>");
        sb.append("History:");
        sb.append("</B></DT><DD><TABLE><TR><TH>Date</TH><TH>Developer(s)</TH><TH>Changes</TH></TR>");
        final Tag[] myTags = tags.clone();
        Arrays.sort(myTags, TagByTextComparator.INSTANCE);
        for (final Tag tag : tags) {
            append(sb, tag);
        }
        sb.append("</TABLE></DD>");
        return sb.toString();
    }

    /** Appends a tag to the text as a table.
     * @param sb StringBuilder to append to.
     * @param tag Tag to append
     */
    private void append(final StringBuilder sb, final Tag tag) {
        final String text = tag.text();
        final Matcher matcher = PATTERN.matcher(text);
        if (!matcher.matches()) {
            System.err.println(tag.position() + ": warning: @" + getName() + " not formatted correctly.");
            return;
        }
        final String dateText = matcher.group(1);
        final String developers = matcher.group(2);
        final String comment = matcher.group(3);
        try {
            ISO_DATE_FORMAT.parse(dateText);
        } catch (final ParseException ignore) {
            System.err.println(tag.position() + ": warning: @" + getName() + " has a date in wrong format (not-iso).");
        }
        sb.append("<TR><TD>");
        sb.append(dateText);
        sb.append("</TD><TD>");
        sb.append(developers);
        sb.append("</TD><TD>");
        sb.append(comment);
        sb.append("</TD></TR>");
    }

    /**
     * Register this Taglet.
     * @param tagletMap the map to register this tag to.
     */
    public static void register(final Map<String, Taglet> tagletMap) {
        final Taglet taglet = new HistoryTaglet();
        tagletMap.put(taglet.getName(), taglet);
    }

} // class HistoryTaglet
