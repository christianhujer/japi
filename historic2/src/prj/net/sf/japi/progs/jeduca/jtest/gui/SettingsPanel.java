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

package net.sf.japi.progs.jeduca.jtest.gui;

import javax.swing.Action;
import javax.swing.JComponent;

/** A component that is a View and a Controller for a part of Settings storable in Preferences or special objects.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public abstract class SettingsPanel extends JComponent {

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** The Action used for ActionListView.
     * @serial include
     */
    private final Action action;

    /** Create a SettingsPanel.
     * @param action Action for ActionListView
     */
    protected SettingsPanel(final Action action) {
        this.action = action;
    }

    /** Create a SettingsPanel.
     * @param basename Base name for ActionListView (utilizes default ActionBuilder) (must be != <code>null</code>)
     * @param builderName name for ActionBuilder (maybe <code>null</code>, use <code>null</code> if unsure)
     * @throws NullPointerException if <var>basename</var> is <code>null</code>
     */
    protected SettingsPanel(final String basename, final String builderName) {
        // todo
        action = null; // dummy for final
    }

    /** Get the Action associated with this SettingsPanel.
     * @return associated Action
     */
    public Action getAction() {
        return action;
    }

    /** Reread all settings from their stored values. */
    public abstract void load();

    /** Store all settings the user made. */
    public abstract void save();

    /** Reset all settings to their default values. */
    public abstract void reset();

} // class SettingsPanel
