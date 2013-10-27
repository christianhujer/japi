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

package net.sf.japi.tools.prefsbrowser;

import java.util.prefs.Preferences;
import org.jetbrains.annotations.NotNull;

/** Node describing a leaf in preferences (key / value pair).
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class PrefsLeafNode implements PrefsTreeNode {

    /** The preferences of this leaf node. */
    @NotNull private final Preferences prefs;

    /** The key of this leaf node. */
    @NotNull private final String key;

    /** Create  a PrefsLeafNode.
     * @param prefs Preferences to create leaf node for
     * @param key Key to create leaf node for
     */
    public PrefsLeafNode(@NotNull final Preferences prefs, @NotNull final String key) {
        this.prefs = prefs;
        this.key = key;
    }

    /** {@inheritDoc} */
    @NotNull public Object getValueAt(final int column) {
        switch (column) {
            case 0: return key;
            case 1: return prefs.get(key, "");
            default: assert false; throw new IndexOutOfBoundsException("column must be >= 0 and <= " + 3 + " but was: " + column);
        }
    }

    /** {@inheritDoc} */
    @NotNull public PrefsTreeNode getChild(final int index) {
        throw new IllegalStateException();
    }

    /** {@inheritDoc} */
    public int getChildCount() {
        return 0;
    }

    /** {@inheritDoc} */
    @Override @NotNull public String toString() {
        return key;
    }

} // class PrefsLeafNode
