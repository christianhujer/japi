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

import javax.swing.Action;
import static javax.swing.Action.NAME;
import javax.swing.JMenu;
import net.sf.japi.swing.action.ActionBuilderFactory;

/** Class for managing menus.
 * It provides a list of menus which can be hidden and shown along with a factory for creating a menu to show and hide these menus.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class MenuManager extends AbstractManager<JMenu> {

    /** Create a MenuManager. */
    public MenuManager() {
    }

    /** {@inheritDoc} */
    @Override protected Action getMenuAction() {
        return menus;
    }

    ///** {@inheritDoc} */
    //protected Action getConfigureAction() {
    //    return configureMenus;
    //}

    /** Menus action. */
    private final Action menus = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing").createAction(true, "menus");

    /** Menus configuration action. */
    private final Action configureMenus = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing").createAction(true, "configureMenus", this);

    /** Menus configuration action. */
    public void configureMenus() { /* TODO */ }

    /** {@inheritDoc}
     * If the default implementation from the superclass did not provide an appropriate name for the SubAction, the JMenu's title is used.
     */
    @Override protected SubAction<JMenu> createSubAction(final JMenu comp) {
        final SubAction<JMenu> subAction = new SubAction<JMenu>(comp);
        if (subAction.getValue(NAME) == null) {
            subAction.putValue(NAME, comp.getText());
        }
        return subAction;
    }

} // class MenuManager
