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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;

/** Dummy Action implementation, an Action implementation which does nothing and only serves as an Action Property container.
 * This is useful e.g. for JMenu, instances of which you can create using Action instances but where implementing the basic abstract method {@link
 * ActionListener#actionPerformed(ActionEvent)} does not make any sense.
 * This class is also an appropriate superclass for Action implementations that aren't interested in ActionEvents but other events.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class DummyAction extends AbstractAction {

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** Create a Dummy Action. */
    public DummyAction() {
    }

    /** {@inheritDoc}
     * The implementation of this method in DummyAction simply does nothing.
     */
    public void actionPerformed(final ActionEvent e) {
        /* Do nothing, this is a Dummy Action. */
    }

    /** {@inheritDoc} */
    @Override protected Object clone() throws CloneNotSupportedException { // NOPMD
        return super.clone();
    }

} // class DummyAction
