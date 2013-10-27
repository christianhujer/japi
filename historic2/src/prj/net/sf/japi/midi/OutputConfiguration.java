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

import javax.sound.midi.MidiDevice;
import org.jetbrains.annotations.NotNull;

/** OutputConfiguration describes a MIDI target, which is the combination of device and channel.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class OutputConfiguration {

    /** The device. */
    @NotNull private final MidiDevice device;

    /** The channel. */
    private final int channel;

    /** Creates an OutputConfiguration.
     * @param device MidiDevice.
     * @param channel Midi Channel.
     */
    public OutputConfiguration(@NotNull final MidiDevice device, final int channel) {
        this.device = device;
        this.channel = channel;
    }

    /** Returns the MidiDevice.
     * @return The MidiDevice.
     */
    @NotNull public MidiDevice getDevice() {
        return device;
    }

    /** Returns the Midi Channel.
     * @return The Midi Channel.
     */
    public int getChannel() {
        return channel;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return device.getDeviceInfo().getName() + ": " + channel;
    }
}
