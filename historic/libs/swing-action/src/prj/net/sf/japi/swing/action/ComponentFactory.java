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

package net.sf.japi.swing.action;

import javax.swing.Action;
import javax.swing.JToolBar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** The ComponentFactory provides some convenience methods for creating Swing Components.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @deprecated This class is experimental.
 *             If you use it, you MUST be aware of the fact that its interface and its package are subject of change.
 * @todo Since this doesn't require an ActionBuilder, this should be in a different library / package.
 */
public final class ComponentFactory {

    /** Utility class - do not instanciate. */
    private ComponentFactory() {
    }

    /** Creates a JToolBar from Actions.
     * @param name     Name of the JToolBar, which is used as title for the undocked JToolBar.
     * @param actions  Actions to create JToolBar for.
     * @return JToolBar with all supplied actions.
     */
    public static JToolBar createJToolBar(@Nullable final String name, @NotNull final Action... actions) {
        final JToolBar toolBar = name != null ? new JToolBar(name) : new JToolBar();
        for (final Action action : actions) {
            toolBar.add(action);
        }
        return toolBar;
    }

    /** Creates a JToolBar from Actions.
     * @param name     Name of the JToolBar, which is used as title for the undocked JToolBar.
     * @param actions  Actions to create JToolBar for.
     * @return JToolBar with all supplied actions.
     */
    public static JToolBar createJToolBar(@Nullable final String name, @NotNull final Iterable<Action> actions) {
        final JToolBar toolBar = name != null ? new JToolBar(name) : new JToolBar();
        for (final Action action : actions) {
            toolBar.add(action);
        }
        return toolBar;
    }

} // class ComponentFactory
