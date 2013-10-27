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

package net.sf.japi.midi.gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.List;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.swing.AbstractListModel;

/** ListModel which shows midi devices.
 * TODO:2009-06-11:christianhujer:Documentation.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class MidiDeviceListModel extends AbstractListModel {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The List with the data. */
    // IntelliJ IDEA does not detect that the invocation of update() sets deviceInfos.
    @SuppressWarnings({"InstanceVariableMayNotBeInitializedByReadObject", "InstanceVariableMayNotBeInitialized"})
    private transient List<MidiDevice.Info> deviceInfos;

    /** Creates a MidiDeviceListModel. */
    public MidiDeviceListModel() {
        update();
    }

    @SuppressWarnings({"JavaDoc"})
    private void readObject(final ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        update();
    }

    /** {@inheritDoc} */
    public int getSize() {
        return deviceInfos.size();
    }

    /** {@inheritDoc} */
    public MidiDevice.Info getElementAt(final int index) {
        return deviceInfos.get(index);
    }

    /** Updates the listmodel.
     * This rescans the MidiSystem for devices.
     */
    public void update() {
        deviceInfos = Arrays.asList(MidiSystem.getMidiDeviceInfo());
    }
}
