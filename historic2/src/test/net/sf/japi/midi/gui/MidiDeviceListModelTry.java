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

package test.net.sf.japi.midi.gui;

import net.sf.japi.midi.gui.MidiDeviceListModel;

import javax.swing.*;

/**
 * TODO:2009-06-11:christianhujer:Documentation.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class MidiDeviceListModelTry {

    /** Tries the MidiDeviceListModel.
     * @param args Command line arguments (ignored).
     */
    public static void main(final String... args) {
        final JFrame f = new JFrame("foo");
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        final JList list = new JList(new MidiDeviceListModel());
        f.add(new JScrollPane(list));
        f.pack();
        f.setVisible(true);
    }
}
