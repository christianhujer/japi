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
import java.io.Serializable;
import java.util.Comparator;

/**
 * Compares Javadoc Tags by their text.
 * Note: this comparator imposes orderings that are inconsistent with equals.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class TagByTextComparator implements Comparator<Tag>, Serializable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** Static instance. */
    public static final Comparator<Tag> INSTANCE = new TagByTextComparator();

    /** {@inheritDoc} */
    public int compare(final Tag o1, final Tag o2) {
        return o1.text().compareTo(o2.text());
    }

} // class TagByTextComparator
