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

package net.sf.japi.swing.misc;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

/** JAPI Swing Utilities.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class SwingUtilities2 {

    /** Utility class - do not instanciate. */
    private SwingUtilities2() {
    }

    /** Find the Window for a Component.
     * Searches for a Frame or a Dialog which is the parent component of the supplied argument.
     * Windows are treated like normal Components since in Java Windows can't stand for themselves but must have another Window as a parent.
     * So a real root ancestor can only be a Frame or a Dialog.
     * @param c Component to get Window for
     * @return Window for c, which is a Frame or a Dialog
     */
    public static Window getWindowForComponent(final Component c) {
        if (c == null) {
            return null;
            // TODO:2009-02-15:christianhujer:Implement handling of null.
            // return getRootFrame();
        }
        if (c instanceof Frame || c instanceof Dialog) {
            return (Window) c;
        }
        return getWindowForComponent(c.getParent());
    }

} // class SwingUtilities2
