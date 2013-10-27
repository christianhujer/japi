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

package net.sf.japi.midi;

import java.io.Serializable;
import java.util.Comparator;
import javax.sound.midi.MidiDevice;

/** Comparator for MidiDevices which compares them by name.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class DeviceComparator implements Comparator<MidiDevice>, Serializable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The convenient default instance. */
    private static final Comparator<? super MidiDevice> INSTANCE = new DeviceComparator();

    /** {@inheritDoc} */
    public int compare(final MidiDevice o1, final MidiDevice o2) {
        return o1.getDeviceInfo().getName().compareTo(o2.getDeviceInfo().getName());
    }

    /** Returns a default instance.
     * @return Default instance.
     */
    public static Comparator<? super MidiDevice> getInstance() {
        return INSTANCE;
    }
}
