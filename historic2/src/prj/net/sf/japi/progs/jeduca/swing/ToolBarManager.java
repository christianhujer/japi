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

package net.sf.japi.progs.jeduca.swing;

import java.awt.BorderLayout;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.ToolBarLayout;

/** Class for managing toolbars.
 * It provides a list of toolbars which can be hidden and shown along with a factory for creating a menu to show and hide these toolbars.
 * <p />
 * When working with toolbars, you might also be interested in {@link ToolBarLayout}, which has nothing to do with ToolBarManager, but nicely replaces
 * {@link BorderLayout} in being a layout manager for containers with toolbars.
 * <p />
 * Note: all added toolbars automatically get a popup menu for managing the toolbars.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @see JToolBar
 * @see ToolBarLayout
 * @todo implement {@link #configureToolbars()}
 * @todo factory method for toolbar creation.
 * @todo popup context menu to change toolbar settings
 * @todo allow changing of icon size
 * @todo allow changing of toolbar alignment (top left right bottom floating)
 * @todo allow changing of text position (icons only, text only, text next to icons, text below icons)
 * ideas: perhaps add a class named ToolBarConfiguration which is savable to prefs, has a default state and consists of Action groups that make up the
 * toolbars?
 */
public class ToolBarManager extends AbstractManager<JToolBar> {

    /** Create a ToolBarManager. */
    public ToolBarManager() {
    }

    /** {@inheritDoc} */
    @Override protected Action getMenuAction() {
        return toolbars;
    }

    /** {@inheritDoc}
     * Adds a Popup menu to the toolbar.
     */
    @Override protected void addHook(final JToolBar comp) {
        final ActionBuilder af = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing");
        final JPopupMenu pop = new JPopupMenu(af.getString("toolbarPopup.text"));
        final JMenu m1 = new JMenu(af.createAction(true, "toolbarPopupPos"));
        final JMenu m2 = new JMenu(af.createAction(true, "toolbarPopupTextpos"));
        final JMenu m3 = new JMenu(af.createAction(true, "toolbarPopupSymsize"));
        final JMenu m4 = createManagedMenu();
        final JMenuItem m5 = new JMenuItem(configureToolbars);
        pop.add(m1);
        pop.add(m2);
        pop.add(m3);
        pop.add(m4);
        pop.add(m5);
        comp.setComponentPopupMenu(pop);
    }

    /** {@inheritDoc} */
    @Override protected Action getConfigureAction() {
        return configureToolbars;
    }

    /** Toolbars action. */
    private final Action toolbars = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing").createAction(true, "toolbars");

    /** Toolbars configuration action. */
    private final Action configureToolbars = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing").createAction(true, "configureToolbars", this);

    /** Toolbars configuration action. */
    public void configureToolbars() {
        /* TODO */
    }

} // class ToolBarManager
