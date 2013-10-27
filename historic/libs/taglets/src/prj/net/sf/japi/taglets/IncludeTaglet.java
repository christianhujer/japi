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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import de.java2html.Java2Html;

/**
 * Taglet which includes a listing from an external file.
 * <p>
 * Example: {&#x40;include NoteTaglet.java}
 * Result:
 * {@include NoteTaglet.java}
 * The path is relative to the file which contains the {&#x40;include} tag.
 * The taglet will always treat the included file as listing.
 * <p>
 * The included file may be processed by <a href="http://www.java2html.de/">java2html</a> by Markus Gebhard if:
 * <ul>
 *  <li>The file name ends on <code>.java</code></li>
 *  <li><a href="http://www.java2html.de/">java2html</a> by Markus Gebhard is available.
 * </ul>
 * For example, if you want to include this in ant, the taglet declaration would look like this:
 * {@code <taglet name="net.sf.japi.taglets.IncludeTaglet" path="lib/japi-lib-taglets.jar;lib/java2html.jar" />}
 *
 * some inline code {@code public class X {
 *     public static void main(final String... args) {
 *         System.out.println("Hello, world");
 *     }
 *  }} in this line.
 *
 * @note The include tag intentionally is an inline tag.
 *       That prevents javadoc from placing it where it wants - that's what it does with block tags.
 *
 * @warning This is a beta version.
 *          Do not use yet, as the format of the parameters for the include tag might change.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class IncludeTaglet implements Taglet {

    /** Size for an I/O buffer. */
    private static final int BUF_SIZE = 4096;

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
        return "include";
    }

    /** {@inheritDoc} */
    public String toString(final Tag tag) {
        final StringBuilder src = new StringBuilder();
        final File f = new File(tag.position().file().getParentFile(), tag.text());
        try {
            // FIXME:2009-07-25:christianhujer:This always uses the default encoding.
            //                                 Instead use the encoding from the doclet or let the user specify.
            final Reader in = new BufferedReader(new FileReader(f));
            try {
                final char[] buf = new char[BUF_SIZE];
                //noinspection NestedAssignment
                for (int charsRead; (charsRead = in.read(buf)) != -1;) {
                    src.append(buf, 0, charsRead);
                }
            } finally {
                in.close();
            }
        } catch (final FileNotFoundException e) {
            //Standard.htmlDoclet.configuration().getDocletSpecificMsg().warning(tag.position(), "cannot open file", f.toString(), e.toString());
            System.err.println(tag.position() + ": warning: cannot open file " + f + ": " + e);
            return "<p>include " + f + " (error)</p>";
        } catch (final IOException e) {
            System.err.println(tag.position() + ": warning: error while reading file " + f + ": " + e);
            return "<p>include " + f + " (error)</p>";
        }
        String text = null;
        if (f.getName().endsWith(".java")) {
            //noinspection ErrorNotRethrown
            try {
                text = Java2Html.convertToHtml(src.toString());
            } catch (final NoClassDefFoundError ignore) {
                System.err.println(tag.position() + ": warning: Java2html not found, cannot convert Java source code to HTML.");
            }
        }
        if (text == null) {
            text = "<pre>" + src.toString().replaceAll("&", "&amp;").replaceAll("<", "&lt;") + "</pre>";
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
        final Taglet taglet = new IncludeTaglet();
        tagletMap.put(taglet.getName(), taglet);
    }
}
