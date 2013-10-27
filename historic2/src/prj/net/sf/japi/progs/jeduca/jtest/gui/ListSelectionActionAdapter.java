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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/** Adapter which creates and fires ActionEvents on ListSelections.
 * Requires that the event source is a JList and its model consists of ActionListeners, e.g. getSelectedValue() returns an ActionListener.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ListSelectionActionAdapter implements ListSelectionListener {

    /** {@inheritDoc}
     * Warning: passes empty String as actionCommand and 0 as ID.
     */
    public void valueChanged(final ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            ((ActionListener) ((JList) e.getSource()).getSelectedValue()).actionPerformed(new ActionEvent(e.getSource(), 0, ""));
        }
    }

} // class ListSelectionActionAdapter
