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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for Taglets that are simple blocks transforming into lists.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo add user definable background colour
 */
@SuppressWarnings({"AbstractClassWithoutAbstractMethods"})
public abstract class BlockListTaglet implements Taglet {

    /** The name of this Taglet. */
    private final String name;

    /** The singular title of this Taglet. */
    private final String singularTitle;

    /** The plural title of this Taglet. */
    private final String pluralTitle;

    /**
     * Create a BLockListTaglet.
     * @param name Name of this Taglet (e.g. "todo").
     * @param title Title of this Taglet (e.g. "Todo") for both, singular and plural.
     */
    protected BlockListTaglet(@NotNull final String name, @NotNull final String title) {
        this.name = name;
        singularTitle = title;
        pluralTitle = title;
    }

    /**
     * Create a BLockListTaglet.
     * @param name Name of this Taglet (e.g. "note").
     * @param singularTitle Singular title of this taglet (e.g. "note").
     * @param pluralTitle Plural title of this taglet (e.g. "notes").
     */
    protected BlockListTaglet(@NotNull final String name, @NotNull final String singularTitle, @NotNull final String pluralTitle) {
        this.name = name;
        this.singularTitle = singularTitle;
        this.pluralTitle = pluralTitle;
    }

    /** {@inheritDoc} */
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    public boolean inConstructor() {
        return true;
    }

    /** {@inheritDoc} */
    public boolean inField() {
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
    public String toString(final Tag tag) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<DT><B>");
        sb.append(singularTitle);
        sb.append("</B></DT><DD><UL><LI>");
        sb.append(tag.text());
        sb.append("</LI></UL></DD>");
        return sb.toString();
    }

    /** {@inheritDoc} */
    @Nullable public String toString(final Tag[] tags) {
        if (tags == null || tags.length == 0) {
            return null;
        }
        if (tags.length == 1) {
            return toString(tags[0]);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("<DT><B>");
        sb.append(pluralTitle);
        sb.append("</B></DT><DD><UL>");
        for (final Tag tag : tags) {
            sb.append("<LI>");
            sb.append(tag.text());
            sb.append("</LI>");
        }
        sb.append("</UL></DD>");
        return sb.toString();
    }

} // class BlockListTaglet
