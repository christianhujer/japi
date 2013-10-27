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

package net.sf.japi.progs.jeduca.util;

import java.io.Serializable;
import java.util.Comparator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** An adapter to be able to use String comparators for Text objects.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class StringToTextComparatorAdapter implements Comparator<Text>, Serializable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** Mime Comparator. */
    @Nullable private final Comparator<String> mime;

    /** String Comparator. */
    @NotNull private final Comparator<String> comp;

    /** Create a <code>Comparator&lt;Text&gt;</code> from a <code>Comparator&lt;String&gt;</code>.
     * @param mime <code>Comparator&lt;String&gt;</code> to create from, used for the Mime Type (may be <code>null</code>)
     * @param comp <code>Comparator&lt;String&gt;</code> to create from, used for the text
     */
    public StringToTextComparatorAdapter(@Nullable final Comparator<String> mime, @NotNull final Comparator<String> comp) {
        this.mime = mime;
        this.comp = comp;
    }

    /** Create a <code>Comparator&lt;Text&gt;</code> from a <code>Comparator&lt;String&gt;</code>.
     * @param comp <code>Comparator&lt;String&gt;</code> to create from, used for the text
     */
    public StringToTextComparatorAdapter(final Comparator<String> comp) {
        this(null, comp);
    }

    /** {@inheritDoc} */
    public int compare(final Text o1, final Text o2) {
        int ret = 0;
        if (mime != null) {
            ret = mime.compare(o1.getType(), o2.getType());
        }
        if (ret == 0) {
            ret = comp.compare(o1.getText(), o2.getText());
        }
        return ret;
    }

    /** {@inheritDoc} */
    @Override public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof StringToTextComparatorAdapter)) {
            return false;
        }
        final StringToTextComparatorAdapter a = (StringToTextComparatorAdapter) obj;
        return (mime == null ? a.mime == null : mime.equals(a.mime)) && comp.equals(a.comp);
    }

    /** {@inheritDoc} */
    @Override public int hashCode() {
        int hc = 0;
        if (mime != null) {
            hc ^= mime.hashCode();
        }
        hc ^= comp.hashCode();
        return hc;
    }

} // class StringToTextComparatorAdapter
