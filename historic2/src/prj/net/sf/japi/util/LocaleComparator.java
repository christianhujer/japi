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

package net.sf.japi.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Implementation of {@link Comparator} that is able to compare {@link Locale} instances by their names (allowing <code>null</code>).
 * The Locale for sorting the Locales is determined at creation time.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class LocaleComparator implements Comparator<Locale>, Serializable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** Collator for comparing the names.
     * @serial exclude
     */
    @NotNull private transient Collator collator = Collator.getInstance();

    /** Create a LocaleComparator. */
    public LocaleComparator() {
    }

    /** {@inheritDoc} */
    private void readObject(@NotNull final ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        collator = Collator.getInstance();
    }

    /** {@inheritDoc} */
    public int compare(@Nullable final Locale o1, @Nullable final Locale o2) {
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
