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
import javax.swing.JInternalFrame;
import net.sf.japi.swing.action.ActionBuilderFactory;

/** Class for managing internal Frames.
 * It provides a list of internal frames which can be hidden and shown along with a factory for creating a menu to show and hide these internal frames.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class InternalFrameManager extends AbstractManager<JInternalFrame> {

    /** Create an InternalFrameManager. */
    public InternalFrameManager() {
    }

    /** {@inheritDoc} */
    @Override protected Action getMenuAction() {
        return internalFrames;
    }

    /** {@inheritDoc} */
    @Override protected Action getConfigureAction() {
        return configureInternalFrames;
    }

    /** InternalFrames action. */
    private final Action internalFrames = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing").createAction(true, "internalFrames");

    /** InternalFrames configuration action. */
    private final Action configureInternalFrames = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing").createAction(true, "configureInternalFrames", this);

    /** InternalFrames configuration action. */
    public void configureInternalFrames() { /* TODO */ }

} // class InternalFrameManager
