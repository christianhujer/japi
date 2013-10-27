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

package net.sf.japi.swing;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/** Implementation of {@link Comparator} that is able to compare {@link Locale} instances by their names (allowing <code>null</code>).
 * The Locale for sorting the Locales is determined at creation time.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public final class LocaleComparator implements Comparator<Locale>, Serializable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** Collator for comparing the names. */
    private final Collator collator = Collator.getInstance();

    /** Create a LocaleComparator. */
    public LocaleComparator() {
    }

    /** {@inheritDoc} */
    public int compare(final Locale o1, final Locale o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return -1;
        } else if (o2 == null) {
            return 1;
        } else {
            return collator.compare(o1.getDisplayName(), o2.getDisplayName());
        }
    }

} // class LocaleComparator
