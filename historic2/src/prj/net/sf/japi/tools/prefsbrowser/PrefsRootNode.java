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

/** Node describing the root of preferences.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class PrefsRootNode implements PrefsTreeNode {

    /** Branch node for system root of preferences. */
    @NotNull private final PrefsBranchNode systemRoot;

    /** Branch node for user root of preferences. */
    @NotNull private final PrefsBranchNode userRoot;

    /** Create a root node for preferences. */
    public PrefsRootNode() {
        systemRoot = new PrefsBranchNode(Preferences.systemRoot(), "system");
        userRoot = new PrefsBranchNode(Preferences.userRoot(), "user");
    }

    /** {@inheritDoc} */
    @NotNull public Object getValueAt(final int column) {
        return "";
    }

    /** {@inheritDoc} */
    @NotNull public PrefsTreeNode getChild(final int index) {
        switch (index) {
            case 0: return systemRoot;
            case 1: return userRoot;
            default: assert false; throw new IllegalArgumentException();
        }
    }

    /** {@inheritDoc} */
    public int getChildCount() {
        return 2;
    }

    /** {@inheritDoc} */
    @Override @NotNull public String toString() {
        return "";
    }

} // class PrefsRootNode
