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

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Taglet which includes a listing.
 * It works like the original <code>{&#x40;code}</code> taglet.
 * As an additional feature, it will treat the first word as a language indicator and try to apply syntax highlighting.
 * The purpose is to include source code within Javadoc in a more convenient form.
 * <p>
 * Currenty, the following languages are supported:
 * <table>
 *  <tr><th>keyword</th><th>language</th><th>Notes</th></tr>
 *  <tr><td>java</td><td>Java</td><td>Requires <a href="http://www.java2html.de/">java2html</a> by Markus Gebhard.</td></tr>
 * </table>
 *
 * <h4>Example</h4>
 * {@listing java /** ...
 *  * {@listing java for (final String arg : args) {
 *  *     out.println(arg);
 *  * }}
 *  * ...}
 * will procude the following output:
 * <p>
 * ...{@listing java for (final String arg : args) {
 *     out.println(arg);
 * }}...
 *
 * @note The listing tag intentionally is an inline tag.
 *       That prevents javadoc from placing it where it wants - that's what it does with block tags.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 1.2
 */
public class ListingTaglet implements Taglet {

    /** Pattern which matches a Listing.
     * The first group is the keyword for the language.
     * The second group is the remaining text, the listing itself.
     */
    private static final Pattern PATTERN = Pattern.compile("\\s*(\\S+)\\s+(.*)", Pattern.MULTILINE | Pattern.DOTALL);

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
        return true;
    }

    /** {@inheritDoc} */
    public String getName() {
        return "listing";
    }

    /** {@inheritDoc} */
    public String toString(final Tag tag) {
        final String origText = tag.text();
        final Matcher matcher = PATTERN.matcher(origText);
        String text = null;
        final String listing;
        if (matcher.matches()) {
            final String language = matcher.group(1);
            listing = matcher.group(2).replaceAll("\n ", "\n");
            if ("java".equalsIgnoreCase(language)) {
                //noinspection ErrorNotRethrown
                try {
                    //text = Java2Html.convertToHtml(listing); // XXX Fix
                } catch (final NoClassDefFoundError ignore) {
                    System.err.println(tag.position() + ": warning: Java2html not found, cannot convert Java source code to HTML.");
                }
            } else {
                System.err.println(tag.position() + ": warning: Unsupported language" + language);
            }
        } else {
            listing = origText;
            System.err.println(tag.position() + ": warning: bad formatted {@listing} - disabling features, trying fallback.");
        }
        if (text == null) {
            text = "<pre>" + listing.replaceAll("&", "&amp;").replaceAll("<", "&lt;") + "</pre>";
        }
        return text;
    }

    /** {@inheritDoc} */
    public String toString(final Tag[] tags) {
        final StringBuilder sb = new StringBuilder();
        for (final Tag tag : tags) {
            sb.append(toString(tag));
        }
        return sb.toString();
    }

    /**
     * Register this Taglet.
     * @param tagletMap the map to register this tag to.
     */
    public static void register(final Map<String, Taglet> tagletMap) {
        final Taglet taglet = new ListingTaglet();
        tagletMap.put(taglet.getName(), taglet);
    }
}
