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

package net.sf.japi.io.args;

import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Locale;
import java.text.Collator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

/** Comparator that compares Charsets by their displayName.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 * @todo move to a different library / package.
 */
public class CharsetDisplaynameComparator implements Comparator<Charset>, Serializable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** Locale to use for the Collator and the displayname.
     * Must be set upon instanciation because the behaviour of a Comparator must stay constant over time.
     * Otherwise this breaks collections using this Comparator.
     * @serial include
     */
    @NotNull
    private final Locale locale;

    /** The Collator to use for comparing the display name.
     * @serial include
     */
    @NotNull private transient Collator collator;

    /** Create a CharsetDisplaynameComparator based on the default locale.
     * The default locale is queried when the CharsetDisplaynameComparator is created.
     * Later invocations to {@link Locale#setDefault(Locale)} will have no effect on this CharsetDisplaynameComparator.
     */
    public CharsetDisplaynameComparator() {
        this(Locale.getDefault());
    }

    /** Create a CharsetDisplaynameComparator based on the specified locale.
     * @param locale Locale to use.
     */
    public CharsetDisplaynameComparator(@NotNull final Locale locale) {
        this.locale = locale;
        collator = Collator.getInstance(locale);
    }

    /** {@inheritDoc} */
    public int compare(@NotNull final Charset o1, @NotNull final Charset o2) {
        return collator.compare(o1.displayName(locale), o2.displayName(locale));
    }

    /** {@inheritDoc} */
    private void readObject(@NotNull final ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        collator = Collator.getInstance(locale);
    }
}
