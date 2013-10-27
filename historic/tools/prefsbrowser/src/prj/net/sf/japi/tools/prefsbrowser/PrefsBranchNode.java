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

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.jetbrains.annotations.NotNull;

/** Node describing a branch in preferences.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class PrefsBranchNode implements PrefsTreeNode {

    /** The preferences of this branch node. */
    @NotNull private final Preferences prefs;

    /** The Children. */
    @NotNull private final List<PrefsTreeNode> children = new ArrayList<PrefsTreeNode>();

    /** Name. */
    @NotNull private String name;

    /** Create  a PrefsLeafNode.
     * @param prefs Preferences to create leaf branch for
     * @param name Name
     */
    public PrefsBranchNode(@NotNull final Preferences prefs, @NotNull final String name) {
        this.prefs = prefs;
        this.name = name;
        initChildren();
    }

    /** Create  a PrefsLeafNode.
     * @param prefs Preferences to create leaf branch for
     */
    public PrefsBranchNode(@NotNull final Preferences prefs) {
        this.prefs = prefs;
        initChildren();
    }

    /** Initializes the children of this PrefsBranchNode. */
    private void initChildren() {
        children.clear();
        try {
            for (final String childName : prefs.childrenNames()) {
                children.add(new PrefsBranchNode(prefs.node(childName)));
            }
        } catch (final BackingStoreException e) {
            e.printStackTrace();
        }
        try {
            for (final String key : prefs.keys()) {
                children.add(new PrefsLeafNode(prefs, key));
            }
        } catch (final BackingStoreException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @NotNull public Object getValueAt(final int column) {
        switch (column) {
            case 0: return prefs.name();
            case 1: return "";
            default: assert false; throw new IndexOutOfBoundsException("column must be >= 0 and <= " + 2 + " but was: " + column);
        }
    }

    /** {@inheritDoc} */
    @NotNull public PrefsTreeNode getChild(final int index) {
        return children.get(index);
    }

    /** {@inheritDoc} */
    public int getChildCount() {
        return children.size();
    }

    /** {@inheritDoc} */
    @Override @NotNull public String toString() {
        return name != null ? name : prefs.name();
    }

} // class PrefsBranchNode
